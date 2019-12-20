package am.server.android.com.testview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 创建时间: 2019-12-20 14:45
 * 类描述: 环形 渲染
 *
 * @author paul
 */
public class RadialGradientView extends View {

    RadialGradient mShader;

    Paint mPaint;

    public RadialGradientView(Context context) {
        this(context, null);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RadialGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Create a shader that draws a linear gradient along a line.
         *
         * @param centerX  中心点x
         * @param centerY  中心点y
         * @param radius   半径
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
        mShader = new RadialGradient(500,500, 250, new int[]{Color.RED, Color.BLUE, Color.GREEN},
                new float[]{0.f, 0.7f, 1}, Shader.TileMode.MIRROR);

        mPaint.setShader(mShader);

        canvas.drawRect(0, 0, 1000, 1000, mPaint);

    }
}
