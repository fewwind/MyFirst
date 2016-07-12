package com.fewwind.mydesign.net.api;

import com.fewwind.mydesign.bean.TegResult;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

/**
 * Created by fewwind on 2016/4/1.
 */
public interface InCarApi {


    @FormUrlEncoded
    @POST("/system/dologin.php")
    Observable<InCarToken> getToken(@Field("deviceidnew") String deviceidnew);


    @FormUrlEncoded
    @POST("/system/gettags.php")
    Observable<TegResult> getTags(@Field("token") String token,@Field("tags") String tags);

    public class InCarToken {
        /**
         * statuscode : 1
         * msg : 用户不存在
         */
        private StatusEntity status;

        public logininfo data;

        public class logininfo {
            public String token;

            @Override
            public String toString() {
                return "logininfo{" +
                        "token='" + token + '\'' +
                        '}';
            }
        }


        public void setStatus(StatusEntity status) {
            this.status = status;
        }

        public StatusEntity getStatus() {
            return status;
        }

        public static class StatusEntity {
            private int statuscode;
            private String msg;

            @Override
            public String toString() {
                return "StatusEntity{" +
                        "statuscode=" + statuscode +
                        ", msg='" + msg + '\'' +
                        '}';
            }

            public void setStatuscode(int statuscode) {
                this.statuscode = statuscode;
            }

            public void setMsg(String msg) {
                this.msg = msg;
            }

            public int getStatuscode() {
                return statuscode;
            }

            public String getMsg() {
                return msg;
            }
        }


        @Override
        public String toString() {
            return "InCarToken{" +
                    "status=" + status +
                    ", data=" + data +
                    '}';
        }
    }
}