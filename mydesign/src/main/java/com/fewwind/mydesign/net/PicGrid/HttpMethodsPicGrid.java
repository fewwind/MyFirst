package com.fewwind.mydesign.net.PicGrid;

import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.RxJavaCallAdapterFactory;

/**
 * Created by fewwind on 2016/3/17.
 */
public class HttpMethodsPicGrid {

//    retrofit的完美封装，下边是源地址
    //http://gank.io/post/56e80c2c677659311bed9841

    public static String BASE_URL = "http://livewp.rcplatformhk.net/RcStickerWeb/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private PicGridApi picGridApi;

    private HttpMethodsPicGrid() {

        OkHttpClient client = new OkHttpClient();
//        client.interceptors().add(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                MediaType mediaType = request.body().contentType();
//                try {
//                    Field field = mediaType.getClass().getDeclaredField("mediaType");
//                    field.setAccessible(true);
//                    field.set(mediaType, "application/json");
//                } catch (NoSuchFieldException e) {
//                    e.printStackTrace();
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//                return chain.proceed(request);
//            }
//        });
        client.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()).build();
        picGridApi = retrofit.create(PicGridApi.class);
    }



    private static class SingleTonHolder {
        private static final HttpMethodsPicGrid INSTANCE = new HttpMethodsPicGrid();
    }

    public static HttpMethodsPicGrid getInstance() {
        return SingleTonHolder.INSTANCE;
    }

    private static HttpMethodsPicGrid instance = null;

    public static HttpMethodsPicGrid getInstanceSync() {
        if (instance == null) {
            synchronized (HttpMethodsPicGrid.class) {
                if (instance == null) {
                    instance = new HttpMethodsPicGrid();
                }
            }
        }
        return instance;
    }

    public PicGridApi getPicGridApi() {
        return picGridApi;
    }


}
