package com.fewwind.mydesign.utils;

import android.app.Activity;

import com.fewwind.mydesign.test.ContentView;

import java.lang.reflect.Method;

/**
 * Created by fewwind on 2016/3/18.
 */
public class ViewInjectUtil {

    public void injectView(Activity activity){

            Class<? extends Activity>  clazz = activity.getClass();
        ContentView annotation = clazz.getAnnotation(ContentView.class);
        if (annotation==null) return;

        int contentId = annotation.value();
        try {
            Method setContentView = clazz.getMethod("setContentView",int.class);
            setContentView.setAccessible(true);
            setContentView.invoke(activity,contentId);




        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
