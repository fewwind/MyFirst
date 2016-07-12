package com.fewwind.mydesign.view.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by admin on 2016/6/17.
 */
public class LineLoading2 extends View {

    private Paint mPaint;
    private Paint mPaintTxt;
    private String mContent = "下载";

    Rect rectTxt;
    private float mPrecent = 0.0f;
    int mColorBg = Color.GREEN;
    private Xfermode mXfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);

    public LineLoading2(Context context) {
        this(context, null);
    }

    public LineLoading2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LineLoading2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 40f, getResources().getDisplayMetrics()));


        mPaintTxt = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectTxt = new Rect();
        mPaintTxt.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 18f, getResources().getDisplayMetrics()));
        mPaintTxt.setColor(Color.WHITE);
        mPaintTxt.setFakeBoldText(true);
        mPaintTxt.getTextBounds(mContent, 0, mContent.length(), rectTxt);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                post(loadTask);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mColorBg);
        canvas.drawLine(getPaddingLeft(), getMeasuredHeight() / 2, getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() / 2, mPaint);

        int count = canvas.saveLayer(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawLine(getPaddingLeft(), getMeasuredHeight() / 2, getMeasuredWidth() - getPaddingRight(), getMeasuredHeight() / 2, mPaint);
        mPaint.setXfermode(mXfermode);


        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(getPaddingLeft(), getMeasuredHeight() / 2, (getMeasuredWidth() - getPaddingRight()-getPaddingLeft()) * mPrecent + getPaddingLeft(), getMeasuredHeight() / 2, mPaint);
        canvas.restoreToCount(count);
        mPaint.setXfermode(null);
        canvas.drawText(mContent, getWidth() / 2 - rectTxt.width() / 2, getHeight() / 2 + rectTxt.height() / 2, mPaintTxt);

        Log.w("tag", "measue" + getMeasuredWidth() + "==paddingleft==" + getPaddingLeft());
    }

    public void setPrecent(float precent) {
        this.mPrecent = precent;
        mColorBg = Color.GRAY;
        if (precent < 1.0f) {

            mContent = (int) (precent * 100) + "%";
        } else {
            mContent = "下载完成";
        }
        mPaintTxt.getTextBounds(mContent, 0, mContent.length(), rectTxt);
        invalidate();
    }

    private float i = 0.0f;
    Runnable loadTask = new Runnable() {
        @Override
        public void run() {

            if (i <= 1.0) {
                postDelayed(loadTask, 200);
            }

            i += 0.02f;
            setPrecent(i);

        }
    };


}
