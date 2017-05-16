package com.yibao.biggirl.util;

import com.yibao.biggirl.MyApplication;

/**
 * 作者：Stran on 2017/3/23 15:26
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class Constants {
    public static String GANK_API = "http://gank.io/";


    public static final String dir                   = FileUtil.getDiskCacheDir(MyApplication.getIntstance()) + "/girls";
    public static       int    LOAD_DATA             = 0;
    public static       int    REFRESH_DATA          = 1;
    public static       int    PULLUP_LOAD_MORE_DATA = 2;
    public static       int    NO_MORE_DATA          = 3;
    public static       int    LOADING_DATA          = 4;


}
