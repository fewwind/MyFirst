package com.fewwind.mydesign.net;

import com.fewwind.mydesign.net.api.MeiZhi;
import com.squareup.okhttp.OkHttpClient;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by fewwind on 2016/3/10.
 */
public class RestClient {
    String BASE_URL = "http://gank.avosapps.com/api/";
    private static RestClient instance = new RestClient();
    private  static MeiZhi api;

    private RestClient(){
        OkHttpClient okHttpClient = new OkHttpClient();
        // okHttpClient.networkInterceptors().add(new AddHeaderInterceptor());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(MeiZhi.class);

    }

    public static MeiZhi api(){
        return api;
    }
}
