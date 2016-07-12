package com.fewwind.mydesign.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by fewwind on 2016/4/1.
 */
public class LongTimeService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

//        AlarmManager manager = (AlarmManager) getSystemService(Activity.ALARM_SERVICE);

    }
}
