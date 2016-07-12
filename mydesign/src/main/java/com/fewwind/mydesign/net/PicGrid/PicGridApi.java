package com.fewwind.mydesign.net.PicGrid;

import com.squareup.okhttp.RequestBody;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Headers;
import retrofit.http.POST;

/**
 * Created by fewwind on 2016/4/1.
 */
public interface PicGridApi {
    @Headers("Content-Type: application/json")
    @POST("plugin/getSticker.do")
    Call<CommonPluginBean> getCommonPlugin(@Body RequestBody array);

    /**
     * 这种方式和上边的效果一样，但是简便很多，封装好的body
     * @param bean
     * @return
     */
    @POST("plugin/getSticker.do")
    Call<CommonPluginBean> getCommonPlugin2(@Body CommonRequestBean bean);
}