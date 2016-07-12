package com.fewwind.mydesign.ontherutil;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by admin on 2016/4/25.
 */
public class VerticalTextView extends TextView {

    Paint mPaint;
    public VerticalTextView(Context context) {
        this(context,null);
    }

    public VerticalTextView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public VerticalTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


    }
}
