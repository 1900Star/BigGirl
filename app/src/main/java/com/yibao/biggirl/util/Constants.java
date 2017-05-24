package com.yibao.biggirl.util;

import com.yibao.biggirl.MyApplication;

/**
 * 作者：Stran on 2017/3/23 15:26
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class Constants {
    public static String GANK_API     = "http://gank.io/";
    //    public static String UNSPLASH_API = "https://unsplash.it/3896/1920/?random";
    public static String UNSPLASH_API = "https://unsplash.it/1920/1080/?random";
    public static String UNSPLASH     = "https://api.unsplash.com/photos/?client_id=6ae42136bb4db6a882e779601db1df9b38dfecb25643be258a2fb2ea8bc50ba4";
    public static String UNSPLASH_ID  = "6ae42136bb4db6a882e779601db1df9b38dfecb25643be258a2fb2ea8bc50ba4";


    public static final String dir                   = FileUtil.getDiskCacheDir(MyApplication.getIntstance()) + "/girls";
    public static       int    LOAD_DATA             = 0;
    public static       int    REFRESH_DATA          = 1;
    public static       int    PULLUP_LOAD_MORE_DATA = 2;
    public static       int    NO_MORE_DATA          = 3;
    public static       int    LOADING_DATA          = 4;
    public static final String FRAGMENT_GIRLS        = "妹子";
    public static final String FRAGMENT_ANDROID      = "安卓";
    public static final String FRAGMENT_VIDEO        = "视频";
    public static final String FRAGMENT_IOS          = "iOS";
    public static final String FRAGMENT_FRONT        = "前端";
    public static final String FRAGMENT_EXPAND       = "拓展资源";
    public static final String FRAGMENT_APP          = "App";
    public static final String SHARE_ME              = "这是一个漂亮的妹子查看器，里面有各种前端后端的开发干货。" + "https://github.com/1900Star/BigGirl";


}
