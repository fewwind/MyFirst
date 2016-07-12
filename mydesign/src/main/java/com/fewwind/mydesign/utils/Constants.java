package com.fewwind.mydesign.utils;

/**
 * Created by fewwind on 2015/10/14.
 */
public class Constants {

    public final static String NetUrlBase = "http://dev.in-carmedia.com/";

//	public final static String NetUrlBase = "http://api.in-carmedia.com/";


/*    "%$1%": "http://api.in-carmedia.com/",
            "%$2%": "http://mp3.huayuansoft.com/",
            "%$3%": "http://api.in-carmedia.com/plugins/",
            "%$4%": "http://mp3.huayuansoft.com/",
            "%$5%": "http://api.in-carmedia.com/app/",
            "%$6%": "http://api.in-carmedia.com/",
            "%$7%": "http://api.in-carmedia.com/"*/

    public static final String NAVIGATE_CUSCHAN = "nav_cus_chan";
    public static final String NAVIGATE_PLAYLISTS = "nav_play_lists";
    public static final String NAVIGATE_MYCHANS = "nav_my_chan";
    public static final String NAVIGATE_SYSCHANS = "nav_sys_chan";
    public static final String NAVIGATE_SHARE = "nav_share";
    public static final String NAVIGATE_SETTING = "nav_setting";
    public static final String CACHEMYCHANNELS = "mychannels";
    public static final String CACHE_CUSCHANNELS = "cuschannels";
    public static final String CACHEALLCHANNELS = "allchannels";
    public static final String CACHECATS = "cats";
    public static final String MYSP = "mydesign";
    public static final String KEY_CHAN_ID = "KEY_CHAN_ID";
    public static final String KEY_CURRENT_PLAY = "KEY_CURRENT_PLAY";



    public static final String BROAD_URL = "send_broad_pley_url";
    public static final String BROAD_Play_OR_PAUSE = "BROAD_Play_OR_PAUSE";
    public static final String BROAD_PLAY_STATUS = "send.current.play.status";
    public static final String BROAD_TOTAL_DURATION = "send.total.duration";


    public static String URL_Login = NetUrlBase + "/system/dologin";
    public static final String URL_TAG =NetUrlBase+ "/system/gettags.php";
    public static final String URL_ALLCHANS =NetUrlBase+ "/system/getsystemchannels.php";
    public static final String URL_PLAYLIST = NetUrlBase+"/1/getprogramlist.php";


    public static final String URL_HEAD = "http://mp3.huayuansoft.com/";



    public static final int PLAY_STATUS_NONE = 0x11;
    public static final int PLAY_STATUS_PLAY = 0x12;
    public static final int PLAY_STATUS_PAUSE = 0x13;


}
