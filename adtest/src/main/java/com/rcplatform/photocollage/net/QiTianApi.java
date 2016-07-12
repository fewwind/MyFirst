package com.rcplatform.photocollage.net;

import com.rcplatform.photocollage.bean.LoginRequest;
import com.rcplatform.photocollage.bean.LoginResultBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 2016/7/5.
 */
public interface QiTianApi {
    /**
     * 这种方式和上边的效果一样，但是简便很多，封装好的body
     *
     * @param bean
     * @return
     */
    @POST("plugin/getSticker.do")
    Call<LoginResultBean> getCommonPlugin2(@Body LoginRequest bean);
}
