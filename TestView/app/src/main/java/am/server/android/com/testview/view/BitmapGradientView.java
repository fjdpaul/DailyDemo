package am.server.android.com.testview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import am.server.android.com.testview.R;

/**
 * 创建时间: 2019-12-20 14:45
 * 类描述: 扫描 渲染
 *
 * @author paul
 */
public class BitmapGradientView extends View {

    BitmapShader mShader;

    Paint mPaint;

    Bitmap mBitmap;

    public BitmapGradientView(Context context) {
        this(context, null);
    }

    public BitmapGradientView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BitmapGradientView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * Create a shader that draws a linear gradient along a line.
         *
         * @param bitmap 位图
         * @param tileX x轴的平铺模式
         * @param tileY y轴的平铺模式
         */
        mShader = new BitmapShader(mBitmap, Shader.TileMode.MIRROR,
                Shader.TileMode.MIRROR);

        mPaint.setShader(mShader);

        canvas.drawRect(0, 0, 500, 500, mPaint);

    }
}
