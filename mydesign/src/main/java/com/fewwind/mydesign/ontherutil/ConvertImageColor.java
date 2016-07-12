package com.fewwind.mydesign.ontherutil;

import android.app.Activity;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;

/**
 * Created by admin on 2016/4/25.
 */
public class ConvertImageColor {
    Activity activity;

    public ConvertImageColor(Activity activity) {
        this.activity = activity;
    }

    // 改变图标的颜色
    public Drawable changeImageColor(int drawable, int color){
        Drawable myIcon = activity.getResources().getDrawable(drawable);
        myIcon.setColorFilter(activity.getResources().getColor(color), PorterDuff.Mode.SRC_ATOP);
        return  myIcon;
    }
}