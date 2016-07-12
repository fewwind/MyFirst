package com.fewwind.mydesign.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * Created by fewwind on 2016/3/16.
 */
public class MyScrollView  extends ScrollView{

    private OnScrollListener mListener;


    public interface OnScrollListener{
        void onScroll(int scrollY);

        void onUpScroll();
        void onDownScroll();
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOnScrollerListener(OnScrollListener listener){
        this.mListener = listener;
    }

    @Override
    protected int computeVerticalScrollRange() {
        return super.computeVerticalScrollRange();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);

        if (mListener!=null){
            mListener.onScroll(t);
        }
    }


    float downY;
    float offsetY;
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mListener!=null){
            switch (ev.getAction()){
                case MotionEvent.ACTION_DOWN:
                    downY = ev.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float currY = ev.getY();
                    offsetY = currY-downY;
                    downY = currY;

                    break;
                case MotionEvent.ACTION_UP:
                    if (offsetY<0){
                        mListener.onUpScroll();
                    } else{
                        mListener.onDownScroll();
                    }
                    break;
            }


        }

        return super.onTouchEvent(ev);
    }
}
