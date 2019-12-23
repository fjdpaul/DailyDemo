package comm.example.strugglefu.uitest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import comm.example.strugglefu.uitest.R;

/**
 * 创建时间: 2019/6/2 下午10:18
 * 类描述: 刮卡效果
 *  利用离线缓冲 重叠部分透明 达到刮刮卡效果
 *  1.首先绘制结果图片 放在最下面
 *  2.绘制用户滑动绘制的图片
 *  3.利用离线缓冲
 *
 * @author 香瓜
 */
public class EraserCarView extends View {
    private Paint mPaint;
    private Path path;
    private Bitmap bitB;//最下面的的图片
    private Bitmap bitM;// 用户滑动区域图片
    private Bitmap bitC;//封面

    private float mX, mY;//记录上一次的点位置
    public EraserCarView(Context context) {
        this(context, null);
    }

    public EraserCarView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EraserCarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /***
    ** 初始化操作
     */
    private void init() {
        //禁用硬件加速
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);

        mPaint = new Paint();
        //设置描边效果 宽度80
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(80);

        //贝塞尔曲线 加上 描边效果 实现用户滑动区域图片
        path = new Path();

        //初始化图片
        bitB = BitmapFactory.decodeResource(getResources(), R.drawable.result);
        bitC = BitmapFactory.decodeResource(getResources(), R.drawable.eraser);
        bitM = Bitmap.createBitmap(bitC.getWidth(), bitC.getHeight(),Bitmap.Config.ARGB_8888);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.i("onSizeChanged", "w " + w);
        Log.i("onSizeChanged", "h " + h);
        Log.i("onSizeChanged", "oldw " + oldw);
        Log.i("onSizeChanged", "oldh " + oldh);
    }

    /**
     * 消费用户该事件
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                //取中心点位置坐标
                float endX = (event.getX() - mX) / 2 + mX;
                float endY = (event.getY() - mY) /2 + mY;
                //边滑边绘
                path.quadTo(mX, mY, endX, endY);
                //绘制 修改控制点值
                mX = event.getX();
                mY = event.getY();
                break;
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                path.moveTo(mX, mY);//作为起始点 绘制贝塞尔
                break;
        }
        //调用draw 进行绘制
        invalidate();
        return true;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //绘制背景
        canvas.drawBitmap(bitB, 0 , 0 ,mPaint);

        //先画出用户滑动的图片
        Canvas canvas1 = new Canvas(bitM);
        canvas1.drawPath(path, mPaint);

        //绘制离线缓冲区
        int saveLayer = canvas.saveLayer(0,0,getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);

        canvas.drawBitmap(bitM, 0 , 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(bitC, 0 , 0, mPaint);

        mPaint.setXfermode(null);
        canvas.restoreToCount(saveLayer);
    }
}
