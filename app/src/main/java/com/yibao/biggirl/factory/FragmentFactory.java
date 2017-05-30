package com.yibao.biggirl.factory;

import android.support.v4.app.Fragment;

import com.yibao.biggirl.mvp.android.AndroidFragment;
import com.yibao.biggirl.mvp.app.AppFragment;
import com.yibao.biggirl.mvp.expand.ExpandFragment;
import com.yibao.biggirl.mvp.frontend.FrontEndFragment;
import com.yibao.biggirl.mvp.girls.GirlsFragment;
import com.yibao.biggirl.mvp.ios.IOSFragment;
import com.yibao.biggirl.mvp.video.VideoFragmnet;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 */
public class FragmentFactory {
    public static final int                    FRAGMENT_GIRLS    = 0;//妹子
    public static final int                    FRAGMENT_ANDROID  = 1;//安卓
    public static final int                    FRAGMENT_VEDIO    = 2;//视频
    public static final int                    FRAGMENT_APP      = 3;//App
    public static final int                    FRAGMENT_IOS      = 4;//iOS
    public static final int                    FRAGMENT_FRONT    = 5;//前端
    public static final int                    FRAGMENT_EXTEND   = 6;//拓展资源
    public static       Map<Integer, Fragment> mCacheFragmentMap = new HashMap<>();

    public static Fragment createFragment(int position) {

        Fragment fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {

            case FRAGMENT_GIRLS:
                fragment = new GirlsFragment();
                break;
            case FRAGMENT_ANDROID:
                fragment = new AndroidFragment();
                break;
            case FRAGMENT_VEDIO:
                fragment = new VideoFragmnet();

                break;
            case FRAGMENT_APP:
                fragment = new AppFragment();
                break;
            case FRAGMENT_IOS:
                fragment = new IOSFragment();

                break;
            case FRAGMENT_FRONT:
                fragment = new FrontEndFragment();
                break;
            case FRAGMENT_EXTEND:
                fragment = new ExpandFragment();
                break;
            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}