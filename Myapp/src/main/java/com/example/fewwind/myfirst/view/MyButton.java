package com.example.fewwind.myfirst.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.Button;

/**
 * Created by fewwind on 2016/1/13.
 */
public class MyButton extends Button {
    public MyButton(Context context) {
        super(context);
    }

    public MyButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        Log.e("tag", "onTouchEvent: "+super.onTouchEvent(event) );

        return super.onTouchEvent(event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("tag", "dispatchTouchEvent: "+super.onTouchEvent(event) );
        return super.dispatchTouchEvent(event);
    }

}
