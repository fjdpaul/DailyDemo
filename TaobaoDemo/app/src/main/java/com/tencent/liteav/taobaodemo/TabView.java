package com.tencent.liteav.taobaodemo;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;

/**
 * @Description: 类作用描述
 * @Author: shuidi
 * @CreateDate: 2020/9/1 下午2:44
 * @Version: 1.0
 */
public class TabView extends FrameLayout {
    private boolean isFirst;
    private Context context;
    private ScrollView scrollView;
    private int lastX,lastY;

    public TabView(@NonNull Context context) {
        super(context);
        this.context = context;
        init(context);
    }

    public TabView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(context);
    }

    public TabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TabView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;

        init(context);
    }

    @SuppressLint("ClickableViewAccessibility")
    private void init(final Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout, this, false);
        scrollView = view.findViewById(R.id.scroll);
        scrollView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFirst) {
                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    isFirst = false;
                } else {
                    scrollView.fullScroll(ScrollView.FOCUS_UP);
                    isFirst = true;
                }
            }
        });
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int y = (int) event.getY();
                int x = (int) event.getX();
                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {// 记录触摸点坐标
                    lastY = y;
                    lastX = x;
                } else if (action == MotionEvent.ACTION_MOVE) {
                } else if (action == MotionEvent.ACTION_UP) {
                    if (Math.abs(y - lastY) < 5 && Math.abs(x - lastX) < 5) {
                        //如果横纵坐标的偏移量都小于五个像素，那么就把它当做点击事件触发
                        doClickthing();
                    } else {
                        //则继续处理为触摸事件
                        //doTouchthing();
                    }
                }
                return true;
            }
        });
//        //initAnimation();
//        ImageView imageView = new ImageView(context);
//        if (isFirst){
//            isFirst = false;
//            imageView.setImageResource(R.drawable.live_star2);
//        } else {
//            imageView.setImageResource(R.drawable.live_star2);
//        }
        addView(view);
    }

    private void doClickthing() {
        if (isFirst) {
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            isFirst = false;
        } else {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            isFirst = true;
        }
    }

    private void initAnimation() {
        LayoutTransition mLayoutTransition = new LayoutTransition();
        Animator animation;
        Animator delete;
        if (isFirst) {
            animation = getAppearingAnimation();
            delete = getfffAnimation();
        } else {
            animation = getfffAnimation();
            delete = getAppearingAnimation();
        }
        mLayoutTransition.setAnimator(LayoutTransition.APPEARING, animation);
        mLayoutTransition.setDuration(LayoutTransition.APPEARING, 400);
        mLayoutTransition.setStartDelay(LayoutTransition.APPEARING, 0);//源码中带有默认300毫秒的延时，需要移除，不然view添加效果不好！！

        mLayoutTransition.setAnimator(LayoutTransition.DISAPPEARING, delete);
        mLayoutTransition.setDuration(LayoutTransition.DISAPPEARING, 400);

        setLayoutTransition(mLayoutTransition);
    }


    private Animator getAppearingAnimation() {
        AnimatorSet mSet = new AnimatorSet();
        mSet.playTogether(
                ObjectAnimator.ofFloat(null, View.TRANSLATION_Y, -400, 0));
        return mSet;
    }

    private Animator getfffAnimation() {
        AnimatorSet mSet = new AnimatorSet();
        mSet.playTogether(
                ObjectAnimator.ofFloat(null, View.TRANSLATION_Y, 400, 0));
        return mSet;
    }

    public void switchTab() {
        if (isFirst) {
            scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            isFirst = false;
        } else {
            scrollView.fullScroll(ScrollView.FOCUS_UP);
            isFirst = true;
        }
//        removeAllViews();
//        ImageView imageView = new ImageView(context);
//        if (isFirst){
//            isFirst = false;
//            imageView.setImageResource(R.drawable.live_star1);
//        } else {
//            isFirst = true;
//            imageView.setImageResource(R.drawable.live_star2);
//        }
//        initAnimation();
//
//        addView(imageView);
    }

}
