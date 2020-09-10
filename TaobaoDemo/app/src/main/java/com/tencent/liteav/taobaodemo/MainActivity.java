package com.tencent.liteav.taobaodemo;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    private View view;
    private TabView tabView;
    private ScaleView scaleView;

    private int[] resIds = {
            R.drawable.ic_test_one,
            R.drawable.ic_test_two,
            R.drawable.ic_test_three,
            R.drawable.ic_test_four,
            R.drawable.ic_test_five
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scaleView = this.findViewById(R.id.scale_container);
        scaleView.setImgs(resIds);
        scaleView.start();
    }
}