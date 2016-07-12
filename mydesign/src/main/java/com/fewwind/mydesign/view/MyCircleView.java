package com.fewwind.mydesign.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.fewwind.mydesign.R;

/**
 * Created by fewwind on 2016/2/23.
 */
public class MyCircleView extends View {
    private int mBorderWidth = 2;
    private int mBorderColor = 0xe6004a;
    private int mWidth;
    private int mHeight;
    private Context mContext;
    private Path mSegmentpath;
    private RectF oval;

    float left;
    float right;
    float top;
    float button;
    private int mRadius;
    private Paint mPaint;

    public MyCircleView(Context context) {
        this(context, null);
    }

    public MyCircleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCircleView);
        mBorderColor = typedArray.getColor(R.styleable.MyCircleView_border_my_color, Color.RED);
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.MyCircleView_border_my_width, 2);


        typedArray.recycle();

        mSegmentpath = new Path();
        oval = new RectF();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mBorderColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = Math.min(mWidth / 2, mHeight / 2) - 6;

        oval = new RectF();
//        oval.set(-mRadius + 9, -mRadius + 9, mRadius - 9, mRadius - 9);
        oval.set(0 + 10, 0 + 10, mWidth - 10, mWidth - 10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);
        int width = canvas.getWidth();
    }

    private void drawCircle(Canvas canvas) {
        mSegmentpath.reset();
        for (int i = 0; i < 360; i += 8) {
            mSegmentpath.addArc(oval, i, 5f);
        }
        canvas.drawPath(mSegmentpath, mPaint);

    }
}
