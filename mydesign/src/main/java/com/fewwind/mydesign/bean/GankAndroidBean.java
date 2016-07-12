package com.fewwind.mydesign.bean;

/**
 * Created by fewwind on 2016/2/19.
 */
public class GankAndroidBean {

 public String who;
 public String publishedAt;
 public String desc;
 public String type;
 public String url;

    public GankAndroidBean(String who,  String desc, String type, String url, String publishedAt) {
        this.who = who;

        this.desc = desc;
        this.type = type;
        this.url = url;
        this.publishedAt = publishedAt;
    }

    @Override
    public String toString() {
        return "GankAndroidBean{" +
                "who='" + who + '\'' +
                ", desc='" + desc + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

//    "who": "MVP",
//            "publishedAt": "2016-02-19T03:45:05.177Z",
//            "desc": "Android夜间模式最佳实践",
//            "type": "Android",
//            "url": "http://mp.weixin.qq.com/s?__biz=MzA4MjU5NTY0NA==&mid=401740657&idx=1&sn=8e6727fbe094ea42d5fd80b185a49395#rd",
//            "used": true,
//            "objectId": "56c67c661532bc0052e96ed0",
//            "createdAt": "2016-02-19T02:22:30.596Z",
//            "updatedAt": "2016-02-19T03:45:07.224Z"
}
