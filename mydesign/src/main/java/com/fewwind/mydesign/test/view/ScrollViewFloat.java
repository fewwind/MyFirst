package com.fewwind.mydesign.test.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ScrollView;

/**
 * Created by fewwind on 2016/3/18.
 */
public class ScrollViewFloat extends ScrollView {

    private View mTopView;
    private View mFloatView;


    public ScrollViewFloat(Context context) {
        super(context);
    }

    public ScrollViewFloat(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ScrollViewFloat(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (mTopView!=null&&mFloatView!=null){
            if (t>=mTopView.getHeight()){
                mFloatView.setVisibility(View.VISIBLE);
            } else{
                mFloatView.setVisibility(View.GONE);
            }
        }

    }

    public void setFloatTopView(View topView,View floatView){
        this.mTopView = topView;
        this.mFloatView = floatView;
    }
}
