package com.fewwind.mydesign.net.api;

import com.fewwind.mydesign.bean.GankAndroidBean;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

/**
 * Created by fewwind on 2016/2/19.
 */
public interface MeiZhi {
    @GET("data/Android/{count}/1")
    Call<Result<List<GankAndroidBean>>> getGankAndroids(
            @Path("count")
            int count);


    @GET("data/Android/{count}/1")
    Observable<Result<List<GankAndroidBean>>> getGankRxAndroids(
            @Path("count")
            int count);


    class Result<T>{
        public boolean error;
        T results;


        public T getResultList(){
            return results;
        }
    }
}
