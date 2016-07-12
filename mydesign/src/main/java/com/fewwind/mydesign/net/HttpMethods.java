package com.fewwind.mydesign.net;

import com.fewwind.mydesign.bean.GankAndroidBean;
import com.fewwind.mydesign.net.api.MeiZhi;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fewwind on 2016/3/17.
 */
public class HttpMethods {

//    retrofit的完美封装，下边是源地址
    //http://gank.io/post/56e80c2c677659311bed9841


    public static final String BASE_URL = "http://gank.avosapps.com/api/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private MeiZhi meiZhi;

    private HttpMethods() {

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        meiZhi = retrofit.create(MeiZhi.class);
    }


    private static class SingleTonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private static HttpMethods instance = null;

    public static HttpMethods getInstanceSync() {
        if (instance == null) {
            synchronized (HttpMethods.class) {
                if (instance == null) {
                    instance = new HttpMethods();
                }
            }
        }
        return instance;
    }

    public void getAndroidGank(Subscriber<MeiZhi.Result<List<GankAndroidBean>>> subscriber, int count) {

        meiZhi.getGankRxAndroids(count).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

    }

    public void getInCarToken(Subscriber<MeiZhi.Result<List<GankAndroidBean>>> subscriber, int count) {

        meiZhi.getGankRxAndroids(count).subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber);

    }

}
