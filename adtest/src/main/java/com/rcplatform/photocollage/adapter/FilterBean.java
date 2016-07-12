package com.rcplatform.photocollage.adapter;

/**
 * Created by admin on 2016/5/23.
 */
public class FilterBean {

    boolean isUnLock;
    String name;
    int type;

    public FilterBean(boolean isUnLock, String name, int type) {
        this.isUnLock = isUnLock;
        this.name = name;
        this.type = type;
    }

    public boolean isUnLock() {
        return isUnLock;
    }

    public void setUnLock(boolean unLock) {
        isUnLock = unLock;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }
}
