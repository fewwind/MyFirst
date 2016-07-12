package com.fewwind.mydesign.bean;

import java.io.Serializable;

/**
 * Created by fewwind on 2015/11/4.
 */
public class PlayListInfo implements Serializable {
/*
    "data": [
    {
        "id": "6458",
            "name": "来生缘",
            "singer": "刘德华",
            "icon": "%$2%/albumpic/13/42290.jpg",
            "lrc": "%$2%/lrc/13/6458.lrc",
            "path": "%$2%/song/13/6458.mp3",
            "catid": "1"
    },*/

    String id;
    String name;
    String singer;
    String icon;
    String lrc;
    String path;
    String catid;

    public PlayListInfo(String id, String name, String singer, String icon, String lrc, String path, String catid) {
        this.id = id;
        this.name = name;
        this.singer = singer;
        this.icon = icon;
        this.lrc = lrc;
        this.path = path;
        this.catid = catid;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public String getIcon() {
        return icon;
    }

    public String getLrc() {
        return lrc;
    }

    public String getPath() {
        return path;
    }

    public String getCatid() {
        return catid;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setLrc(String lrc) {
        this.lrc = lrc;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setCatid(String catid) {
        this.catid = catid;
    }

    @Override
    public String toString() {
        return "PlayListInfo{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", singer='" + singer + '\'' +
                ", icon='" + icon + '\'' +
                ", lrc='" + lrc + '\'' +
                ", path='" + path + '\'' +
                ", catid='" + catid + '\'' +
                '}';
    }
}
