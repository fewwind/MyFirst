package com.rcplatform.photocollage.net;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by fewwind on 2016/3/17.
 */
public class HttpMethods {

//    retrofit的完美封装，下边是源地址
    //http://gank.io/post/56e80c2c677659311bed9841

    public static String BASE_URL = "http://livewp.rcplatformhk.net/RcStickerWeb/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private QiTianApi qiTianApi;

    private HttpMethods() {

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
//        client.setConnectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(client).addConverterFactory(GsonConverterFactory.create())
                .build();
        qiTianApi = retrofit.create(QiTianApi.class);
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

    public QiTianApi getPicGridApi() {
        return qiTianApi;
    }


}
