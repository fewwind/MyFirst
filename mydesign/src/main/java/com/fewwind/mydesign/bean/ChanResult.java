package com.fewwind.mydesign.bean;

import java.util.List;

/**
 * Created by fewwind on 2016/3/8.
 */
public class ChanResult {


    public StatusCls status;
    public int ver;

    public List<ChannelInfo> data;

    @Override
    public String toString() {
        return "ChanResult{" +
                "status=" + status +
                ", ver=" + ver +
                ", data=" + data +
                '}';
    }

    public static class StatusCls{
        int statuscode;
        String msg;

        public StatusCls(int statuscode, String msg) {
            this.statuscode = statuscode;
            this.msg = msg;
        }

        public int getStatuscode() {
            return statuscode;
        }

        public void setStatuscode(int statuscode) {
            this.statuscode = statuscode;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}
