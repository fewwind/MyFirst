package com.example.fewwind.myfirst.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.FrameLayout;

/**
 * Created by fewwind on 2016/1/19.
 */
public class MyViewGroup extends FrameLayout {


    public MyViewGroup(Context context) {
        super(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("tag", "MyViewGroup---onTouchEvent: "+super.onTouchEvent(event));
//        return super.onTouchEvent(event);
        return true;
    }
}
