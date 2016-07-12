package com.fewwind.mydesign.bean;

import java.io.Serializable;

/**
 * Created by fewwind on 2015/10/15.
 */
public class ChannelInfo implements Serializable ,Comparable<ChannelInfo> {
/*    "ver":"2",
            "id":"133",
            "subappid":"1",
            "orderno":"201",
            "name":"BobJames钢琴大峡谷",
            "desc":"FUSION JAZZ 在这类音乐的领域里日益受到肯定和重视，如此成果JAMES 实功不可没。基本上来说，FUSION就是融和多种音乐的综合体，也较为一般人接受，JAMES 的音乐在作曲、编曲上十分用心，录音和制作也很严谨，给人有清爽脱俗的感觉。",
            "icon":"%$4%channels/1439878080.jpg",
            "cat":"欧美劲曲",
            "cat_id":"2",
            "param":null,
            "play_status":"1"*/
    private int id;
    private int ver;
    private int orderno;
    private String icon;
    private String name;
    private String desc;
    private String cat;
    private String  cat_id;

    public ChannelInfo(int id, int ver, int orderno, String icon, String name, String desc, String cat, String cat_id) {
        this.id = id;
        this.ver = ver;
        this.orderno = orderno;
        this.icon = icon;
        this.name = name;
        this.desc = desc;
        this.cat = cat;
        this.cat_id = cat_id;
    }

    @Override
    public String toString() {
        return "ChannelInfo{" +
                "id=" + id +
                ", ver=" + ver +
                ", orderno=" + orderno +
                ", icon='" + icon + '\'' +
                ", name='" + name + '\'' +
                ", desc='" + desc + '\'' +
                ", cat='" + cat + '\'' +
                ", cat_id='" + cat_id + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVer() {
        return ver;
    }

    public void setVer(int ver) {
        this.ver = ver;
    }

    public int getOrderno() {
        return orderno;
    }

    public void setOrderno(int orderno) {
        this.orderno = orderno;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getCat_id() {
        return cat_id;
    }

    public void setCat_id(String cat_id) {
        this.cat_id = cat_id;
    }

    @Override
    public int compareTo(ChannelInfo another) {
        return this.getId()-another.getId();
    }


    @Override
    public boolean equals(Object o) {
        return this.getId()==((ChannelInfo)o).getId();
    }
}
