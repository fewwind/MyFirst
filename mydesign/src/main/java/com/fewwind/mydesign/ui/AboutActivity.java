package com.fewwind.mydesign.ui;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.TrafficStats;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;

import com.fewwind.mydesign.R;
import com.fewwind.mydesign.bean.ChannelInfo;
import com.fewwind.mydesign.net.RxUtil;
import com.fewwind.mydesign.utils.StatusBarCompat;
import com.fewwind.mydesign.utils.TDevice;
import com.fewwind.mydesign.view.widget.CustomLineProgressBar;
import com.fewwind.mydesign.view.widget.LineProgressButton;
import com.fewwind.mydesign.view.widget.Rotate3DAnim;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class AboutActivity extends AppCompatActivity {

    private TextView mTvTitle;
    private boolean retuens;
    private ImageView qqSport;

    LineProgressButton mLineProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        StatusBarCompat.compat(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getTrafficStats();
        CollapsingToolbarLayout coordinatorLayout = (CollapsingToolbarLayout) findViewById(R.id.id_coordin_toolbar);
        coordinatorLayout.setTitle("关于我");
        initViews();

        List<ChannelInfo> lists = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ChannelInfo info = new ChannelInfo(1, i, i, "daf", "dfa", "dafawf", "da", "22");
            lists.add(info);
        }
//        observable.subscribe(observer);
//        observable1.subscribe(observer);

        Observable.from(array)
                .map(new Func1<String, Bitmap>() {
                    @Override
                    public Bitmap call(String s) {
                        return null;
                    }
                })
                .subscribe(new Subscriber<Bitmap>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Bitmap bitmap) {
                        boolean b = TDevice.hasInternet();
                        Logger.i("是否有网络---" + b);
                    }
                });


        final int stringId = R.string.app_name;
/*
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                String appName = getResources().getString(stringId);
                subscriber.onNext(appName);
                Logger.i(appName);
                subscriber.onCompleted();
            }
        })*/
        RxUtil.makeOnservable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "我是 rx java 工具";
            }
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Logger.i("我是 rx java 工具---onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(String s) {
                        mTvTitle.setText(s);
                    }
                });

    }

    private void initViews() {

        mTvTitle = (TextView) findViewById(R.id.id_about_tv);
        qqSport = (ImageView) findViewById(R.id.id_qqSport);
        mLineProgressBar = (LineProgressButton) findViewById(R.id.id_line_progerss_bar);

        mLineProgressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLineProgressBar.post(loadTask);
            }
        });

        qqSport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retuens = true;
                applyRotation(0, 180);
            }
        });


        int[] datas = {8, 6, 2, 9};
//        for (int i = 0; i < datas.length - 1; i++) {
//            for (int j = 0; j < datas.length - 1 - i; j++) {
//                if (datas[j] > datas[j + 1]) {
//                    int temp = datas[j];
//                    datas[j] = datas[j + 1];
//                    datas[j + 1] = temp;
//                }
//            }
//        }


        int tem;
        for (int i = 0; i < datas.length - 1; i++) {
            for (int j = 0; j < datas.length - 1 - i; j++) {
                if (datas[j] < datas[j + 1]) {
                    tem = datas[j];
                    datas[j] = datas[j + 1];
                    datas[j + 1] = tem;
                }
            }
        }

        for (int i = 0; i < datas.length; i++) {
            Logger.v(datas[i] + "");
        }

    }

    Observer<String> observer = new Observer<String>() {

        @Override
        public void onNext(String s) {
            Logger.d(s);
        }

        @Override
        public void onCompleted() {
            Logger.v("success");
        }

        @Override
        public void onError(Throwable e) {

        }


    };


    private String[] array = {"Hello", "World"};
    Observable<String> observable1 = Observable.from(array);


    Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
        @Override
        public void call(Subscriber<? super String> subscriber) {
            subscriber.onNext("Hello");
            subscriber.onNext("RxAndroid");
            subscriber.onNext("RxJava");
            subscriber.onCompleted();
        }
    });

    private float i = 0.0f;
    Runnable loadTask = new Runnable() {
        @Override
        public void run() {

            if (i <= 1.0) {
                mLineProgressBar.postDelayed(loadTask, 200);
                i += 0.02f;
                mLineProgressBar.setPrecent(i,"下载中 "+(int) (i * 100) + "%");
            } else{
                mLineProgressBar.setContext("下载完成");

            }
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    public static void startAboutActivity(Context context) {
        Intent intent = new Intent(context, AboutActivity.class);
        context.startActivity(intent);


    }

    public void getTrafficStats() {

        try {
            PackageManager pm = getPackageManager();
            ApplicationInfo ai = pm.getApplicationInfo("com.incarmedia", PackageManager.GET_ACTIVITIES);
            Log.d("InCarMedia", "!!" + ai.uid);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        int uid = 0;
        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ApplicationInfo appinfo = getApplicationInfo();
        List<ActivityManager.RunningAppProcessInfo> run = am.getRunningAppProcesses();
        for (ActivityManager.RunningAppProcessInfo runningProcess : run) {
            if ((runningProcess.processName != null) && runningProcess.processName.equals(appinfo.processName)) {
                uid = runningProcess.uid;
                break;
            }
        }
        long rxBytes = TrafficStats.getUidRxBytes(uid);
        long txBytes = TrafficStats.getUidTxBytes(uid);
        Logger.d("当前流量Rx---" + rxBytes / 1024 / 1024 + "当前流量Tx====" + txBytes / 1024 / 1024 + "设备uid---" + uid);
    }

    private void applyRotation(float start, float end) {
        // 计算中心点
        final float centerX = qqSport.getWidth() / 2.0f;
        final float centerY = qqSport.getHeight() / 2.0f;

        final Rotate3DAnim rotation = new Rotate3DAnim(this, start, end, centerX, centerY, 1.0f, true);
        rotation.setDuration(1500);
        rotation.setFillAfter(true);
        rotation.setInterpolator(new AccelerateInterpolator());

        rotation.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (retuens) {
                    retuens = false;
                    applyRotation(180, 0);
                }
            }
        });
        qqSport.startAnimation(rotation);
    }

    public void intentOtherActivity() {
        //                Intent intent = new Intent(Intent.ACTION_VIEW);
//                String packageName = "com.incarmedia";
//                String className = "com.incarmedia.main.SplashActivity";
//                intent.setClassName(packageName, className);
//                intent.putExtra("incat_radio", "incat_radio");
//                startActivity(intent);
    }
}
