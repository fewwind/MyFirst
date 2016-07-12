package com.fewwind.mydesign.bean;

import java.io.Serializable;

/**
 * Created by fewwind on 2015/10/16.
 */
public class CatBean implements Serializable,Comparable<CatBean> {

    private String catId;
    private String catName;

    public CatBean(String catId, String catName) {
        this.catId = catId;
        this.catName = catName;
    }


    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    @Override
    public String toString() {
        return "CatBean{" +
                "catId='" + catId + '\'' +
                ", catName='" + catName + '\'' +
                '}';
    }

    @Override
    public int compareTo(CatBean another) {
        return Integer.parseInt(this.getCatId()) -Integer.parseInt( another.getCatId());
    }
}
