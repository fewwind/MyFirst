package com.example.fewwind.myfirst.view;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by fewwind on 2016/1/21.
 */
public class MyViewPager extends ViewGroup {

    private Scroller mScroller;

    private int mTouchSlop;
    private float mXDown;
    private float mYDown;

    private float mXMove;
    private float mXLastMove;

    private int leftBroader;
    private int rightBroader;


    public MyViewPager(Context context) {
        this(context, null);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mScroller = new Scroller(context);
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childs = getChildCount();

        for (int i = 0; i < childs; i++) {
            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (changed) {
            int childs = getChildCount();

            for (int i = 0; i < childs; i++) {
                View child = getChildAt(i);
                child.layout(i * child.getMeasuredWidth(), 0, (i + 1) * getMeasuredWidth(), getMeasuredHeight());
            }

            leftBroader = getChildAt(0).getLeft();
            rightBroader = getChildAt(childs - 1).getRight();

        }
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;

                break;

            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();
                float diff = Math.abs(mXMove - mXDown);
                mXLastMove = mXMove;

                if (diff > mTouchSlop) {
                    return true;
                }
                break;
        }

        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {

            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                Log.e("tag", "ACTION_MOVE--getScrollX()--: " + getScrollX());

                int scrollX = (int) (mXLastMove - mXMove);
                if (getScrollX() + scrollX < leftBroader) {
                    scrollTo(leftBroader, 0);
                    return true;
                } else if (getScrollX() + getWidth() + scrollX > rightBroader) {
                    scrollTo(rightBroader - getWidth(), 0);

                    return true;
                }

                scrollBy(scrollX, 0);
                mXLastMove = mXMove;

                break;

            case MotionEvent.ACTION_UP:
                Log.v("tag", "ACTION_UP: getScrollX()---" + getScrollX());
                int targetIndex = (getScrollX() + getWidth() / 2) / getWidth();
                int dx = targetIndex * getWidth() - getScrollX();

                mScroller.startScroll(getScrollX(), 0, dx, 0);
                invalidate();


                break;
        }


        return super.onTouchEvent(event);
    }


    @Override
    public void computeScroll() {
        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        if (mScroller.computeScrollOffset()) {
            Log.d("tag", "ACTION_UP:getCurrX--- " + mScroller.getCurrX());
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            invalidate();
        }
    }
}
