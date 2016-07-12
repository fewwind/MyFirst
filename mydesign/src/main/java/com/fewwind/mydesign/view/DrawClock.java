package com.fewwind.mydesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fewwind on 2016/3/3.
 */
public class DrawClock extends View {

    Paint mPaint;

    public DrawClock(Context context) {
        this(context, null);
    }

    public DrawClock(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DrawClock(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(5);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.translate(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);

        canvas.drawCircle(0, 0, getMeasuredWidth() / 2 - 10, mPaint);

        android.graphics.Path path = new android.graphics.Path();

        mPaint.setStrokeWidth(2);
        mPaint.setColor(Color.RED);
        for (int i = 0; i <=360 ; i+=6) {
            canvas.drawLine(0,getMeasuredWidth()/2-10-10,0,getMeasuredWidth()/2-10,mPaint);
            canvas.rotate(6);
        }

    }
}
