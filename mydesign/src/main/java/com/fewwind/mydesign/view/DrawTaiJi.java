package com.fewwind.mydesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fewwind on 2016/2/26.
 */
public class DrawTaiJi extends View {


   private Paint mPaintCircleOuter;
    private android.graphics.Paint mPaintCircleInner;
    private Handler mHandler;

    public DrawTaiJi(Context context) {
        this(context, null);
    }

    public DrawTaiJi(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawTaiJi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        mPaintCircleOuter = new Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
        mPaintCircleOuter.setStyle(android.graphics.Paint.Style.STROKE);
        mPaintCircleOuter.setStrokeWidth(3F);
        mPaintCircleOuter.setColor(Color.parseColor("#E6004a"));

        mPaintCircleInner = new Paint(android.graphics.Paint.ANTI_ALIAS_FLAG);
        mPaintCircleInner.setColor(Color.BLACK);
        mHandler =new Handler();
//        mHandler.post(rotateTask);
        this.post(rotateTask);
    }

    private float mWidth;
    private float mHeight;
    private float mRadius;
    private RectF mOval;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = Math.min(mWidth / 2, mHeight / 2) - 6;

        mOval = new RectF(-mRadius, -mRadius, mRadius, mRadius);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);



        canvas.translate(mWidth / 2, mHeight / 2);
        /**
         * 调用旋转的方法必须在  绘制之前调用，并且如果 旋转画布之后，必须在之后调用
         */
        canvas.rotate(degree);

        canvas.drawCircle(0, 0, mRadius, mPaintCircleOuter);
        mPaintCircleInner.setColor(Color.BLACK);
        canvas.drawArc(mOval, 90F, 180, true, mPaintCircleInner);
        mPaintCircleInner.setColor(Color.GREEN);
        canvas.drawArc(mOval, -90F, 180, true, mPaintCircleInner);
        canvas.drawCircle(0, (mHeight / 2 - 6) / 2, (mHeight / 2 - 6) / 2, mPaintCircleInner);
        mPaintCircleInner.setColor(Color.BLACK);
        canvas.drawCircle(0, -(mHeight / 2 - 6) / 2, (mHeight / 2 - 6) / 2, mPaintCircleInner);

        canvas.drawCircle(0, (mHeight / 2 - 6) / 2, mHeight / 9, mPaintCircleInner);
        mPaintCircleInner.setColor(Color.GREEN);
        canvas.drawCircle(0, -(mHeight / 2 - 6) / 2, mHeight / 9, mPaintCircleInner);





    }


    Runnable rotateTask = new Runnable() {
        @Override
        public void run() {
            invalidate();
            degree +=2;
           DrawTaiJi.this.postDelayed(this, 20);
        }
    };
    int degree;

    /**
     * 圆圈扫过的角度
     */
    private float mSweepAngel;

    /**
     * 设置加载进度
     */
    public void setProgress(int progress) {
        mSweepAngel = (float) (progress / 100.0 * 360);
        invalidate();
    }
}
