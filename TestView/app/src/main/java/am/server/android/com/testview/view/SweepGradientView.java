package am.server.android.com.testview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建时间: 2019-12-20 14:45
 * 类描述: 扫描 渲染
 *
 * @author paul
 */
public class SweepGradientView extends View {

    SweepGradient mShader;

    Paint mPaint;

    public SweepGradientView(Context context) {
        this(context, null);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SweepGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Create a shader that draws a linear gradient along a line.
         *
         * @param cx           渐变中心x
         * @param cx           渐变中心y

         * @param colors       渐变 颜色数组
         * @param positions    位置数组 取值范围0-1
         */
        mShader = new SweepGradient(500,500,new int[]{Color.RED, Color.BLUE, Color.GREEN},
                new float[]{0.f, 0.7f, 1});

        mPaint.setShader(mShader);

        canvas.drawRect(0, 0, 1000, 1000, mPaint);

    }
}
