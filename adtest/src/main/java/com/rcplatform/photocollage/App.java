package com.rcplatform.photocollage;

import android.app.Application;

import com.orhanobut.logger.Logger;

/**
 * Created by admin on 2016/5/23.
 */
public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init("test");
    }
}
