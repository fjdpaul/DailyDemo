package com.tencent.liteav.taobaodemo;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * @Description: 类作用描述
 * @Author: shuidi
 * @CreateDate: 2020/9/9 下午4:51
 * @Version: 1.0
 */
public class AddGroup extends FrameLayout {
    private Context context;
    private Handler handler = new Handler();
    private ImageView imageView;
    private ImageView imageView1;
    private boolean isChange = false;
    private ObjectAnimator animation = new ObjectAnimator();
    private float oldValue = 20;
    private float oldValue1 = 20;
    private float oldValue2 = 1f;
    private float oldValue3 = 1f;

    private Runnable runnable = new Runnable() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
             oldValue = 20;
            oldValue1 = 20;
            oldValue2 = 1f;
            oldValue3 = 1;
            changeImage();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void changeImage() {
        isChange = !isChange;
        animation.setInterpolator(new LinearInterpolator());
        animation.setFloatValues(0,100);
        animation.start();
        animation.setDuration(500);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                Log.i("onAnimationUpdate", value + "");
                upAnimation(value);
            }
        });
        //handler.postDelayed(runnable, 2000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void upAnimation(float value) {
        oldValue2 = oldValue2 -0.01f;
        Log.i("oldValue2", oldValue2 + "");
        if (isChange){
            imageView.setTranslationZ( oldValue);
            imageView1.setTranslationZ(-oldValue);
            imageView.setTranslationY(-oldValue1);
            imageView1.setTranslationY(oldValue1);
            imageView.setScaleX(oldValue2);
            imageView.setScaleY(oldValue2);
            imageView1.setScaleX(oldValue3);
            imageView1.setScaleY(oldValue3);
        } else {
            imageView1.setTranslationZ(oldValue);
            imageView.setTranslationZ(-oldValue);
            imageView1.setTranslationY(-oldValue1);
            imageView.setTranslationY(oldValue1);
            imageView1.setScaleX(oldValue2);
            imageView1.setScaleY(oldValue2);
            imageView.setScaleX(oldValue3);
            imageView.setScaleY(oldValue3);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddGroup(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddGroup(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public AddGroup(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        initView();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initView() {
        removeAllViews();
        LayoutParams layoutParams = new LayoutParams(500,500);
        imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.ic_test_one);
        imageView.setLayoutParams(layoutParams);
        imageView.setScaleX(0.8f);
        imageView.setScaleY(0.8f);
        imageView.setTranslationY(100);
        addView(imageView);

        LayoutParams layoutParams1 = new LayoutParams(500,500);
        imageView1 = new ImageView(context);
        imageView1.setImageResource(R.drawable.ic_test_two);
        imageView1.setLayoutParams(layoutParams1);
//        imageView1.setTranslationX(30);
//        imageView1.setTranslationY(-60);
//        imageView1.setTranslationZ(60);
        addView(imageView1);
        handler.post(runnable);
    }

}
