package com.fewwind.mydesign.bean;

/**
 * Created by fewwind on 2015/10/27.
 */
public class TagBean {

    String name;
    int id;

    public TagBean(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
