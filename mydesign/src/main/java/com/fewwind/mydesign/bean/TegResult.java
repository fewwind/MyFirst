package com.fewwind.mydesign.bean;

import java.util.List;

/**
 * Created by fewwind on 2016/4/1.
 */
public class TegResult {


    /**
     * statuscode : 0
     * msg : null
     */

    private StatusEntity status;
    /**
     * status : {"statuscode":0,"msg":null}
     * tags : 0
     * data : [{"id":"194","name":"动漫铃声"},{"id":"155","name":"说唱"},{"id":"139","name":"酒吧"},{"id":"27","name":"激情"},{"id":"118","name":"欧美经典"},{"id":"142","name":"汽车"},{"id":"80","name":"军旅歌曲"},{"id":"105","name":"萨克斯"},{"id":"86","name":"儿歌"},{"id":"107","name":"葫芦丝"},{"id":"93","name":"欧美经典"},{"id":"64908","name":"邓丽君"},{"id":"180","name":"女声"},{"id":"76","name":"胎教"},{"id":"145","name":"节奏布鲁斯"},{"id":"182","name":"天籁"},{"id":"82","name":"红歌"},{"id":"195","name":"游戏铃声"},{"id":"35","name":"怀念"},{"id":"2","name":"热歌"},{"id":"151","name":"流行"},{"id":"120","name":"韩语"},{"id":"49","name":"劲爆"},{"id":"64909","name":"凤凰传奇"},{"id":"64925","name":"汪峰"}]
     * r : null
     */

    private String tags;
    private Object r;
    /**
     * id : 194
     * name : 动漫铃声
     */

    private List<DataEntity> data;

    public void setStatus(StatusEntity status) {
        this.status = status;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public void setR(Object r) {
        this.r = r;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public StatusEntity getStatus() {
        return status;
    }

    public String getTags() {
        return tags;
    }

    public Object getR() {
        return r;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class StatusEntity {
        private int statuscode;
        private Object msg;

        public void setStatuscode(int statuscode) {
            this.statuscode = statuscode;
        }

        public void setMsg(Object msg) {
            this.msg = msg;
        }

        public int getStatuscode() {
            return statuscode;
        }

        public Object getMsg() {
            return msg;
        }
    }

    public static class DataEntity {
        private String id;
        private String name;

        public void setId(String id) {
            this.id = id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public String getName() {
            return name;
        }
    }

    @Override
    public String toString() {
        return "TegResult{" +
                "status=" + status +
                ", tags='" + tags + '\'' +
                ", r=" + r +
                ", data=" + data +
                '}';
    }
}
