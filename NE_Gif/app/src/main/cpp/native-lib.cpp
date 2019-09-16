#include <jni.h>
#include <string>
#include "gif_lib.h"
#include <android/bitmap.h>
#define  argb(a,r,g,b) ( ((a) & 0xff) << 24 ) | ( ((b) & 0xff) << 16 ) | ( ((g) & 0xff) << 8 ) | ((r) & 0xff)

typedef struct GifBean{
    int current_frame;
    int total_frames;
    int *delays;
};


void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels);

extern "C"
JNIEXPORT jlong JNICALL
Java_am_server_android_com_ne_1gif_GifNDKDecoder_loadGIFNative(JNIEnv *env, jclass type, jstring path_) {
    const char *path = env->GetStringUTFChars(path_, 0);

    int error;
    //GifFileType 保存gif信息的结构体
    GifFileType * gifFileType =DGifOpenFileName(path, &error);

    //gif 初始化
    DGifSlurp(gifFileType);

    //给GifBean分配内存
    GifBean *gifBean = (GifBean *)(malloc(sizeof(GifBean)));
    //清空原有内存，保证是一个干净的结构体
    memset(gifBean, 0, sizeof(GifBean));

    //给延迟总时间数组分配内存
    gifBean->delays = (int *)(malloc(sizeof(int) * gifFileType->ImageCount));
    memset(gifBean->delays, 0, sizeof(int) * gifFileType->ImageCount );

    gifBean->current_frame=0;
    ExtensionBlock *ext;
    //赋值给GifBean
    for (int i = 0; i < gifFileType->ImageCount; ++i) {
        //取出每一帧图像
        SavedImage frame = gifFileType->SavedImages[i];
        for (int j = 0; j < frame.ExtensionBlockCount; ++j) {
            if (frame.ExtensionBlocks[j].Function == GRAPHICS_EXT_FUNC_CODE){
                ext = &frame.ExtensionBlocks[j];
            }
        }
        if(ext){
            //拿到图形控制拓展块（拓展时间）
            //小端模式
            //Bytes[1] 低8位
            //Bytes[2] 高8位
            //Bytes[0] 保留字段
            int frame_delay = ext->Bytes[2]<<8 | ext->Bytes[1] * 10;
            gifBean->delays[i] = frame_delay;
        }
    }
    gifBean->total_frames = gifFileType->ImageCount;
    //方便后面获取宽高等信息
    gifFileType->UserData = gifBean;

    env->ReleaseStringUTFChars(path_, path);
    return (jlong)(gifFileType);
}

void drawFrame(GifFileType *gifFileType, GifBean *gifBean, AndroidBitmapInfo info, void *pixels) {
    SavedImage savedImage = gifFileType->SavedImages[gifBean->current_frame];
    //当前帧图像信息
    GifImageDesc imageInfo = savedImage.ImageDesc;
    int *px = (int *)(pixels);//图像的首地址
    ColorMapObject *colorMapObject = imageInfo.ColorMap;
    if (colorMapObject == NULL){
        colorMapObject = gifFileType->SColorMap;
    }

    //y方向的偏移量
    px = (int *)((char *)px + info.stride * imageInfo.Top);

    int pointPixels;//记录像素位置
    GifByteType gifByteType;
    GifColorType gifColorType;
    int *line;
    for (int y = imageInfo.Top; y < imageInfo.Top + imageInfo.Height; ++y) {
        line = px;
        for (int x = imageInfo.Left; x < imageInfo.Left + imageInfo.Width; ++x) {
            pointPixels = (y - imageInfo.Top)* imageInfo.Width + (x - imageInfo.Left);
            //拿到像素数据(颜色索引值)
            gifByteType = savedImage.RasterBits[pointPixels];
            //给像素赋予颜色
            if (colorMapObject != NULL){
                gifColorType = colorMapObject->Colors[gifByteType];
                line[x] = argb(255,gifColorType.Red, gifColorType.Green, gifColorType.Blue);
            }

        }
        px = (int *)((char *)px + info.stride);
    }
}

extern "C"
JNIEXPORT jint JNICALL
Java_am_server_android_com_ne_1gif_GifNDKDecoder_getWidth(JNIEnv *env, jclass type,
                                                          jlong gifPoint) {

    GifFileType *gifFileType = (GifFileType *)(gifPoint);
    return gifFileType->SWidth;

}
extern "C"
JNIEXPORT jint JNICALL
Java_am_server_android_com_ne_1gif_GifNDKDecoder_getHeight(JNIEnv *env, jclass type,
                                                           jlong gifPoint) {

    GifFileType *gifFileType = (GifFileType *)(gifPoint);
    return gifFileType->SHeight;

}
extern "C"
JNIEXPORT jint JNICALL
Java_am_server_android_com_ne_1gif_GifNDKDecoder_updateFrame(JNIEnv *env, jclass type,
                                                             jobject bitmap, jlong gifPoint) {
    GifFileType *gifFileType = (GifFileType *)(gifPoint);
    GifBean *gifBean = (GifBean *)gifFileType->UserData;

    AndroidBitmapInfo info;

    //通过bitmap 获取 AndroidBitmapInfo
    AndroidBitmap_getInfo(env, bitmap, &info);

    void *pixels;//像素数组

    //锁定bitmap
    AndroidBitmap_lockPixels(env,bitmap, &pixels);

    //绘制一帧图像
    drawFrame(gifFileType, gifBean, info, pixels);
    gifBean->current_frame += 1;//绘制当前帧后加1

    if(gifBean->current_frame >= gifBean->total_frames){
        gifBean->current_frame = 0;
    }


    AndroidBitmap_unlockPixels(env, bitmap);

    return gifBean->delays[gifBean->current_frame];
}