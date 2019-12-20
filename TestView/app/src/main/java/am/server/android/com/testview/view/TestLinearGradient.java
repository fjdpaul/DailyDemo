package am.server.android.com.testview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建时间: 2019-12-20 14:45
 * 类描述: 线性 渲染
 *
 * @author paul
 */
public class TestLinearGradient extends View {

    LinearGradient mShader;

    Paint mPaint;

    public TestLinearGradient(Context context) {
        this(context, null);
    }

    public TestLinearGradient(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestLinearGradient(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Create a shader that draws a linear gradient along a line.
         *
         * @param x0           起点x
         * @param y0           起点y
         * @param x1           终点x
         * @param y1           终点y
         *                     起点 - 终点 控制渐变的方向
         * @param colors       渐变 颜色数组
         * @param positions    位置数组 取值范围0-1
         * @param tile         平铺模式
         *
         * 复制边缘颜色
         *CLAMP(0),
         *
         *  重复复制
         *REPEAT(1),
         *
         *  重复镜像
         *MIRROR(2);
         *
         *}
         */
        mShader = new LinearGradient(0,0, 250, 0, new int[]{Color.RED, Color.BLUE, Color.GREEN},
                new float[]{0.f, 0.7f, 1}, Shader.TileMode.MIRROR);

        mPaint.setShader(mShader);

        canvas.drawRect(0, 0, 1000, 1000, mPaint);

    }
}
