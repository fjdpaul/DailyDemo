package am.server.android.com.ne_gif;

import android.graphics.Bitmap;

/**
 * 创建时间: 2019-09-15 12:08
 * 类描述:
 *
 * @author paul
 */
public class GifNDKDecoder {

    static {
        System.loadLibrary("native-lib");
    }

    public GifNDKDecoder(long gifPointer) {
        this.gifPoint = gifPointer;
    }

    public static GifNDKDecoder load(String path){
        long gitHander = loadGIFNative(path);
        GifNDKDecoder gifNDKDecoder = new GifNDKDecoder(gitHander);
        return gifNDKDecoder;
    }

    //加载gif文件

    private static native long loadGIFNative(String path);
    public static native int getWidth(long gifPoint);
    public static native int getHeight(long gifPoint);

    public static native int updateFrame(Bitmap bitmap, long gifPoint);

    private long gifPoint;

    public long getGifPoint() {
        return gifPoint;
    }
}
