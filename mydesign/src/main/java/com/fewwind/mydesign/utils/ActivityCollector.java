package com.fewwind.mydesign.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fewwind on 2015/10/13.
 */
public class ActivityCollector {

    public static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public  static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll(){
        for (Activity activity:activities){
            activity.finish();
        }

        activities.clear();
//        System.exit(0);
    }
}
