package comm.example.strugglefu.uitest.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

import comm.example.strugglefu.uitest.R;
/**
 * 创建时间: 2019/6/4 上午11:21
 * 类描述: 模仿某app刷新 机理一致

 *
 * @author 香瓜
 */
public class PathMeasureView extends View {

    private Paint mPaint = new Paint();
    private Paint mLinePaint = new Paint(); //坐标系
    private Bitmap mBitmap;

    public PathMeasureView(Context context) {
        super(context);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);
        mPaint.setStrokeWidth(4);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(6);

        //缩小图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4;
        mBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.arrow,options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint);
        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mLinePaint);

        canvas.translate(getWidth() / 2, getHeight() / 2);


        mPath.reset();
        mPath.addRect(-200,-200,200, 200,Path.Direction.CW);
        canvas.drawPath(mPath, mPaint);

        mFloat += 0.01;
        if (mFloat >= 1){
            mFloat = 0;
        }

        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        //将pos信息和tan信息保存在mMatrix中
        pathMeasure.getMatrix(pathMeasure.getLength() * mFloat, mMatrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        //将图片的旋转坐标调整到图片中心位置
        mMatrix.preTranslate(-mBitmap.getWidth() / 2, -mBitmap.getHeight() / 2);

        canvas.drawBitmap(mBitmap,mMatrix, mPaint);

        invalidate();
    }

    private Matrix mMatrix = new Matrix();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private Path mPath = new Path();
    private float mFloat;


}
