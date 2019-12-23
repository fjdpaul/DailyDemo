package comm.example.strugglefu.uitest.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;

import comm.example.strugglefu.uitest.R;

/**
 * 创建时间: 2019/6/2 上午11:33
 * 类描述: 爆炸粒子
 *
 * @author 香瓜
 */
public class SpliteView extends View {
    private Paint mPaint;
    private Bitmap mBitmap;
    private List<Ball> mBalls = new ArrayList<>();
    private float d = 3;//粒子的直径

    private ValueAnimator mValueAnimator;
    public SpliteView(Context context) {
        this(context, null);
    }

    public SpliteView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpliteView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /****
     * 初始化
     */
    private void init(){
        mPaint = new Paint();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 40;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_beatful_girl, options);
        for (int i = 0; i < mBitmap.getWidth(); i++){
            for (int j = 0; j < mBitmap.getHeight(); j++){
                Ball ball = new Ball();
                ball.color = mBitmap.getPixel(i, j);
                ball.x = i * d + d / 2;
                ball.y = j * d + d / 2;
                ball.r = d/2;
                // 速度(-20, 20) 的随机值
                ball.vX = (float) (Math.pow(-1, Math.ceil(Math.random() * 1000)) * 20 * Math.random());
                ball.vY = rangInt(-15, 35);
                //加速度
                ball.aX = 0;
                ball.aY = 0.98f;
                mBalls.add(ball);
            }
        }
        mValueAnimator = ValueAnimator.ofFloat(0,1);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });
    }


    private void updateBall() {
        for (Ball ball : mBalls){
            ball.x += ball.vX;
            ball.y += ball.vY;

            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }
    }
    private int rangInt(int i, int j){
        int max = Math.max(i, j);
        int min = Math.min(i, j) - 1;
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getWidth()/2,getHeight()/2);
        for (Ball ball : mBalls){
            mPaint.setColor(ball.color);
            canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            mValueAnimator.start();
        }
        return true;
    }
}
