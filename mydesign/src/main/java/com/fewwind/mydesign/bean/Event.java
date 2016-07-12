package com.fewwind.mydesign.bean;

import java.util.List;

/**
 * Created by fewwind on 2015/12/29.
 */
public class Event {


    private List<ChannelInfo> lists;

    public Event(List<ChannelInfo> infos) {
        this.lists = infos;
    }

    public List<ChannelInfo> getLists() {
        return lists;
    }

    public static class ItemListEvent {
        public List<ChannelInfo> mLists;

        public ItemListEvent(List<ChannelInfo> mLists) {
            this.mLists = mLists;
        }
    }

}
