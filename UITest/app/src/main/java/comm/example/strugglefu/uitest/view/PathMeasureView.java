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
import android.util.Log;
import android.view.View;

import comm.example.strugglefu.uitest.R;
/**
 * 创建时间: 2019/6/4 上午11:21
 * 类描述: 模仿某app刷新 机理一致

 *
 * @author 香瓜
 */
public class PathMeasureView extends View {

    private Paint mNormalPaint = new Paint();
    private Paint mPaint = new Paint();
    private Paint mLinePaint = new Paint(); //坐标系
    private Bitmap mBitmap;

    public PathMeasureView(Context context) {
        super(context);
        mNormalPaint.setStyle(Paint.Style.STROKE);
        mNormalPaint.setColor(getResources().getColor(R.color.normal_red));
        mNormalPaint.setStrokeWidth(12);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(getResources().getColor(R.color.red));
        mPaint.setStrokeWidth(12);

        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(6);

        //缩小图片
        BitmapFactory.Options options = new BitmapFactory.Options();
        mBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_free,options);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, mLinePaint);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), mLinePaint);

        canvas.translate(getWidth() / 2, getHeight() / 2);


        mPath.reset();
        mPath.addCircle(0,0, 200,Path.Direction.CW);
        canvas.drawPath(mPath, mNormalPaint);

        mFloat += 0.01;
        if (mFloat >= 1){
            mFloat = 0;
        }

        PathMeasure pathMeasure = new PathMeasure(mPath, false);
        //将pos信息和tan信息保存在mMatrix中
        pathMeasure.getMatrix(pathMeasure.getLength() * mFloat, mMatrix, PathMeasure.POSITION_MATRIX_FLAG | PathMeasure.TANGENT_MATRIX_FLAG);
        //将图片的旋转坐标调整到图片中心位置
        mMatrix.preRotate(-90);
        mMatrix.preTranslate(-mBitmap.getWidth()/2, -mBitmap.getHeight()/2);

        pathMeasure.getPosTan(pathMeasure.getLength() * mFloat,pos,tan);

        //计算出当前的切线与x轴夹角的度数
        double degrees = 360 * mFloat;
        Log.e("TAG", "onDraw: degrees="+degrees);
        mPath.reset();
        mPath.addArc(-200,-200, 200, 200,0, (float) degrees);
        canvas.drawPath(mPath, mPaint);

        canvas.drawBitmap(mBitmap,mMatrix, mPaint);

        invalidate();
    }

    private Matrix mMatrix = new Matrix();
    private float[] pos = new float[2];
    private float[] tan = new float[2];
    private Path mPath = new Path();
    private float mFloat;


}
