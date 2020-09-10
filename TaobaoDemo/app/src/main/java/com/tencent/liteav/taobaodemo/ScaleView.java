package com.tencent.liteav.taobaodemo;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @Description: 类作用描述
 * @Author: shuidi
 * @CreateDate: 2020/9/9 下午7:48
 * @Version: 1.0
 */
public class ScaleView extends FrameLayout {
    private float value = 100;
    private float v = 100;
    private boolean isChange;
    private Context context;
    private ImageView imageView, imageView1;
    private ObjectAnimator objectAnimator;
    private int[] imgs;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            changeView();
        }
    };

    public ScaleView(Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    public ScaleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    private void initView() {
        objectAnimator = new ObjectAnimator();
        objectAnimator.setIntValues(1, 10);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.setDuration(200);
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int s = (int) animation.getAnimatedValue();
                if (isChange) {
                    value = 90 + s;
                    imageView1.setScaleX(value/100);
                    imageView1.setScaleY(value/100);
                    v = 100 - s;
                    imageView.setScaleX(v/100);
                    imageView.setScaleY(v/100);
                    imageView.setTranslationY(3 * s);
                    imageView1.setTranslationY(-3 * s);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imageView.setTranslationZ(-10 * s);
                        imageView1.setTranslationZ(10 * s);
                    }
                } else {
                    value = 100 - s;
                    imageView1.setScaleX(value/100);
                    imageView1.setScaleY(value/100);
                    v = 90 + s;
                    imageView.setScaleX(v/100);
                    imageView.setScaleY(v/100);
                    imageView.setTranslationY(-3 * s);
                    imageView1.setTranslationY(3 * s);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        imageView.setTranslationZ(10 * s);
                        imageView1.setTranslationZ(-10 * s);
                    }
                }
            }
        });
        this.post(new Runnable() {
            @Override
            public void run() {
                //保证 获取宽、高，容器已经绘制完成
                LayoutParams layoutParams = new LayoutParams(getWidth(), getHeight());
                imageView = new ImageView(context);
                imageView.setImageResource(R.drawable.ic_test_one);
                imageView.setLayoutParams(layoutParams);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                addView(imageView);
                imageView1 = new ImageView(context);
                imageView1.setLayoutParams(layoutParams);
                imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
                imageView1.setImageResource(R.drawable.ic_test_two);
                addView(imageView1);
            }
        });

    }
    public void start(){
        if (imgs == null || imgs.length < 2){
            return;
        }
        handler.post(runnable);
    }

    private void changeView() {
        handler.postDelayed(runnable, 5000);
        isChange = !isChange;
        objectAnimator.start();
    }

    public void setImgs(int[] imgs) {
        this.imgs = imgs;
    }
}
