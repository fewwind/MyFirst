package com.example.fewwind.myfirst;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by fewwind on 2016/2/14.
 */
public class AppService extends Service {

    public static int inCarTimeOut;
    private Handler mHandler;
    //定义开启行车模式的接口，如果超过固定时间没有触摸屏幕就会回调 该方法
    private ItimeDrawer mItimeDrawer;

    public interface  ItimeDrawer{
        void openDrawer();
    }

    public void setInCarTimeListener(ItimeDrawer timeListener){
        this.mItimeDrawer = timeListener;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IncarTimeBinder();
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mHandler = new Handler();
        mHandler.postDelayed(checkIncarTimeTask,1000);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }



    Runnable checkIncarTimeTask = new Runnable() {
        @Override
        public void run() {
            if (inCarTimeOut>0){
                mItimeDrawer.openDrawer();
            }
            inCarTimeOut++;
            mHandler.postDelayed(this,1000);
        }
    };

    @Override
    public boolean onUnbind(Intent intent) {

        return super.onUnbind(intent);
    }

    public class IncarTimeBinder extends Binder {
        public AppService getService() {
            return AppService.this;
        }
    }
}
