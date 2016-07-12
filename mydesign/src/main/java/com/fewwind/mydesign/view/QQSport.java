package com.fewwind.mydesign.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by fewwind on 2016/3/2.
 */
public class QQSport extends View {

    private Paint mPaint;


    private Paint mPaintTxt;
    Rect rectTxt;
    String progress = "66";

    public QQSport(Context context) {
        this(context, null);
    }

    public QQSport(Context context, AttributeSet attrs) {
        super(context, attrs);

        mPaint =new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaintTxt =new Paint(Paint.ANTI_ALIAS_FLAG);
        rectTxt =new Rect();

        mPaintTxt.setTextSize(55);
        mPaintTxt.setFakeBoldText(true);
        mPaintTxt.getTextBounds(progress,0,progress.length(),rectTxt);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.GRAY);

        canvas.drawArc(new RectF(0 + 20, 0 + 20, getMeasuredWidth() - 20, getMeasuredHeight() - 20), -225, 270, false, mPaint);
        mPaint.setColor(Color.YELLOW);
        canvas.drawArc(new RectF(0 + 20, 0 + 20, getMeasuredWidth() - 20, getMeasuredHeight() - 20), -225, 160, false, mPaint);

        mPaintTxt.setColor(Color.RED);
        canvas.drawText(progress,getWidth()/2-rectTxt.width()/2,getHeight()/2+rectTxt.height()/2,mPaintTxt);
    }
}
