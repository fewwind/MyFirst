package com.example.fewwind.myfirst.view;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

/**
 * Created by fewwind on 2015/11/27.
 */
public class DragView extends View {

    private Scroller mScroller;
    private static final String TAG = "tag";

    public DragView(Context context) {
        this(context, null);
    }

    public DragView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initScroll(context);


        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: ");
            }
        });


        setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Log.e(TAG, "onLongClick: ");
                return false;
            }
        });
    }

    private void initScroll(Context context) {
        this.setBackgroundColor(Color.RED);
        mScroller = new Scroller(context);
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            Log.e(TAG, "computeScroll: ");
            ((View) getParent()).scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
        invalidate();

    }

    int lastX, lastY = 0;
    int x, y = 0;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        int rawX = (int) event.getRawX();
//        int rawY = (int) event.getRawY();
        int x = (int) event.getX();
        int y = (int) event.getY();
        Log.e(TAG, "onTouchEvent: " + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                lastX = rawX;
//                lastY = rawY;

                lastX = (int) event.getX();
                lastY = (int) event.getY();

                break;

            case MotionEvent.ACTION_MOVE:
                int offsetX = x - lastX;
                int offsetY = y - lastY;
                Log.e(TAG, "ACTION_MOVE: "+  ((View) getParent()).getScrollX() );
                ((View) getParent()).scrollBy(-offsetX, -offsetY);

//                layout(getLeft() + offsetX,
//                getTop() + offsetY,
//                getRight() + offsetX,
//                getBottom() + offsetY);
//
//                lastX = rawX;
//                lastY = rawY;
                break;

            case MotionEvent.ACTION_UP:
                View parent = ((View) getParent());
                Log.e(TAG, "ACTION_UP: ");
                mScroller.startScroll(parent.getScrollX(), parent.getScrollY(), -parent.getScrollX(), -parent.getScrollY());
                invalidate();
                break;
        }
        return true;
    }
}
