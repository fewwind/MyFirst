package com.fewwind.mydesign.net;

import com.fewwind.mydesign.net.api.InCarApi;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by fewwind on 2016/3/17.
 */
public class HttpMethodsInCar {

//    retrofit的完美封装，下边是源地址
    //http://gank.io/post/56e80c2c677659311bed9841

    public final static String BASE_URL = "http://dev.in-carmedia.com/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private InCarApi inCarApi;

    private HttpMethodsInCar() {

        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        inCarApi = retrofit.create(InCarApi.class);
    }


    private static class SingleTonHolder {
        private static final HttpMethodsInCar INSTANCE = new HttpMethodsInCar();
    }

    public static HttpMethodsInCar getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private static HttpMethodsInCar instance = null;

    public static HttpMethodsInCar getInstanceSync() {
        if (instance == null) {
            synchronized (HttpMethodsInCar.class) {
                if (instance == null) {
                    instance = new HttpMethodsInCar();
                }
            }
        }
        return instance;
    }

    public InCarApi getInCarApi(){
        return inCarApi;
    }


}
