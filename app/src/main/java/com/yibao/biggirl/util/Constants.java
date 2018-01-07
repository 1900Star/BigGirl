package com.yibao.biggirl.util;

import com.yibao.biggirl.MyApplication;

/**
 * 作者：Stran on 2017/3/23 15:26
 * 描述：${常量类}
 * 邮箱：strangermy@outlook.com
 *
 * @author Stran
 */
public class Constants {
    /**
     * 多图   MFStar模范学院
     */
    private static String DUOTU_MOFAN_API = "http://www.duotu555.com/mm/51/list_51_";
    /**
     * 多图   Pans写真
     */
    private static String DUOTU_PANS_API = "http://www.duotu555.com/mm/46/list_46_";
    /**
     * 多图  尤果网
     */
    private static String DUOTU_UGIRLS_API = "http://www.duotu555.com/mm/5/list_5_";
    /**
     * 多图   ROSI写真
     */
    private static String DUOTU_ROSI_API = "http://www.duotu555.com/mm/6/list_6_";
    /**
     * 多图   魅妍社
     */
    private static String DUOTU_MEIYAN_API = "http://www.duotu555.com/mm/11/list_11_";
    /**
     * 多图   推女郎
     */
    private static String DUOTU_TUGIRL_API = "http://www.duotu555.com/mm/14/list_14_";
    /**
     * 多图   波波社
     */
    private static String DUOTU_BOBOSHE_API = "http://www.duotu555.com/mm/9/list_9_";
    /**
     * 多图   第四印象
     */
    private static String DUOTU_DISIYINGXIANG_API = "http://www.duotu555.com/mm/49/list_49_";
    /**
     * 多图   Yunvlang
     */
    private static String DUOTU_YUNVLANG_API = "http://www.duotu555.com/mm/32/list_32_";
    /**
     * 多图   XieRen
     */
    private static String DUOTU_XIUREN_API = "http://www.duotu555.com/mm/8/list_8_";

    /**
     * 妹子图 API
     */
    public static String MEIZITU_API = "http://www.mzitu.com/";
    public static final int MEIZITU = 3;
    public static int MeiSingle = 2;

    /**
     * GANK.IO  API
     */
    public static String GANK_API = "http://gank.io/";
    /**
     * UNSPLASH  API
     */
    public static String UNSPLASH_API = "https://unsplash.it/3896/1920/?random";
    public static String UNSPLASH_URL = "https://picsum.photos/1920/1080/?image=";
    public static String UNSPLASH = "https://api.unsplash.com/photos/?client_id=6ae42136bb4db6a882e779601db1df9b38dfecb25643be258a2fb2ea8bc50ba4";
//    public static String UNSPLASH_ID = "6ae42136bb4db6a882e779601db1df9b38dfecb25643be258a2fb2ea8bc50ba4";


    public static final String DIR = FileUtil.getDiskCacheDir(MyApplication.getIntstance()) + "/girls";
    public static final String DELETE_DIR = FileUtil.getDiskCacheDir(MyApplication.getIntstance()) + "/girls/share_y.jpg";

    //保存图片状态码

    public static int FIRST_DWON = 0;
    public static int EXISTS = 1;
    public static int DWON_PIC_EROOR = 2;
    //fragment 加载状态码

    public static int LOAD_DATA = 0;
    public static int REFRESH_DATA = 1;
    public static int LOAD_MORE_DATA = 2;
    //    public static int NO_MORE_DATA = 3;
//    public static int LOADING_DATA = 4;

    public static String[] arrTitle = {"Girl",
            "Android",
            "App",
            "iOS",
            "Video",
            "前端",
            "拓展资源",
            "Japan", "Hot", "Sex", "Cute", "MFStar", "Pans", "Ugirls", "Rosi", "Meiyan", "TuiGirl", "Boboshe", "Disiyxiang", "Yunvlang", "Xiuren"};


    public static final int TYPE_GIRLS = 0;
    private static final int TYPE_ANDROID = 1;
    private static final int TYPE_APP = 2;
    private static final int TYPE_IOS = 3;

    private static final int TYPE_VEDIO = 4;

    private static final int TYPE_FRONT = 5;

    private static final int TYPE_EXTEND = 6;

    private static final int TYPE_JAPAN = 7;

    private static final int TYPE_HOT = 8;

    private static final int TYPE_SEX = 9;

    private static final int TYPE_CUTE = 10;


    private static final int TYPE_MFSTAR = 11;

    private static final int TYPE_PANS = 12;

    private static final int TYPE_UGIRLS = 13;
    private static final int TYPE_ROSI = 14;

    private static final int TYPE_MEIYAN = 15;
    private static final int TYPE_TUIGIRL = 16;
    private static final int TYPE_TUIBOBO = 17;
    private static final int TYPE_DISIYINGXIANG = 18;
    private static final int TYPE_YUNVLANG = 19;
    private static final int TYPE_XIUREN = 20;

    private static final String FRAGMENT_ANDROID = "Android";
    public static final String FRAGMENT_GIRLS = "福利";
    private static final String FRAGMENT_VIDEO = "休息视频";
    private static final String FRAGMENT_IOS = "iOS";
    private static final String FRAGMENT_FRONT = "前端";
    private static final String FRAGMENT_EXPAND = "拓展资源";
    private static final String FRAGMENT_APP = "App";
    private static final String FRAGMENT_HOT = "hot";
    public static final String FRAGMENT_JAPAN = "japan";
    private static final String FRAGMENT_SEX = "xinggan";
    private static final String FRAGMENT_CUTE = "mm";

    public static final String SHARE_ME = "这是一个漂亮的妹子查看器，里面有各种前端后端的开发干货。https://github.com/1900Star/BigGirl";

    private static String mLoadType;

    public static String getLoadType(int type) {
        switch (type) {
            case TYPE_ANDROID:
                mLoadType = Constants.FRAGMENT_ANDROID;
                break;
            case TYPE_APP:
                mLoadType = Constants.FRAGMENT_APP;
                break;
            case TYPE_IOS:
                mLoadType = Constants.FRAGMENT_IOS;
                break;
            case TYPE_VEDIO:
                mLoadType = Constants.FRAGMENT_VIDEO;
                break;
            case TYPE_FRONT:
                mLoadType = Constants.FRAGMENT_FRONT;
                break;
            case TYPE_EXTEND:
                mLoadType = Constants.FRAGMENT_EXPAND;
                break;
            case TYPE_JAPAN:
                mLoadType = Constants.FRAGMENT_JAPAN;
                break;
            case TYPE_HOT:
                mLoadType = Constants.FRAGMENT_HOT;
                break;
            case TYPE_SEX:
                mLoadType = Constants.FRAGMENT_SEX;
                break;
            case TYPE_CUTE:
                mLoadType = Constants.FRAGMENT_CUTE;
                break;
            case TYPE_MFSTAR:
                mLoadType = Constants.DUOTU_MOFAN_API;
                break;
            case TYPE_PANS:
                mLoadType = Constants.DUOTU_PANS_API;
                break;
            case TYPE_UGIRLS:
                mLoadType = Constants.DUOTU_UGIRLS_API;
                break;
            case TYPE_ROSI:
                mLoadType = Constants.DUOTU_ROSI_API;
                break;
            case TYPE_MEIYAN:
                mLoadType = Constants.DUOTU_MEIYAN_API;
                break;
            case TYPE_TUIGIRL:
                mLoadType = Constants.DUOTU_TUGIRL_API;
                break;
            case TYPE_TUIBOBO:
                mLoadType = Constants.DUOTU_BOBOSHE_API;
                break;
            case TYPE_DISIYINGXIANG:
                mLoadType = Constants.DUOTU_DISIYINGXIANG_API;
                break;
            case TYPE_YUNVLANG:
                mLoadType = Constants.DUOTU_YUNVLANG_API;
                break;
            case TYPE_XIUREN:
                mLoadType = Constants.DUOTU_XIUREN_API;
                break;
            default:
                break;
        }
        return mLoadType;
    }

    public static final int MODE_KEY = 0;
    public static final String MUSIC_MODE = "music_mode";
    public static final String PLAY_MODE_KEY = "play_mode";

    public static final String MUSIC_POSITION = "music_position";
    public static final String MUSIC_ITEM_POSITION = "music_position";

    public static final String MUSIC_CONFIG = "music_config";
    public static final String MUSIC_REMENBER_FLAG = "music_remenber_flag";

}
