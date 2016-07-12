package com.example.fewwind.myfirst.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by fewwind on 2015/12/31.
 */
public class SimpleView extends View {



    public SimpleView(Context context) {
        super(context);
    }

    public SimpleView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));

    }

    private int measureHeight(int heightMeasureSpec) {

        int mHeiSpec =MeasureSpec.getMode(heightMeasureSpec);
        int sizeHei = MeasureSpec.getSize(heightMeasureSpec);
        Log.e("tag", "SimpleView(测量高): "+sizeHei);
        int result = mHeiSpec==MeasureSpec.EXACTLY?sizeHei:200;
        return result;


    }

    private int measureWidth(int widthMeasureSpec) {
        int mHeiSpec =MeasureSpec.getMode(widthMeasureSpec);
        int sizeHei = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("tag", "SimpleView(测量款): "+sizeHei);
        int result = mHeiSpec==MeasureSpec.EXACTLY?sizeHei:200;
        return result;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tag", "View----onTouchEvent: " + super.onTouchEvent(event));
        return true;
//        return super.onTouchEvent(event);
    }
}
