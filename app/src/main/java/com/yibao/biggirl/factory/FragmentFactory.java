package com.yibao.biggirl.factory;

import android.support.v4.app.Fragment;

import com.yibao.biggirl.android.AndroidFragment;
import com.yibao.biggirl.home.GirlsFragment;

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
    public static final int                    FRAGMENT_IOS      = 3;//iOS
    public static final int                    FRAGMENT_FRONT    = 4;//前端
    public static final int                    FRAGMENT_EXTEND   = 5;//拓展资源
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
            case FRAGMENT_IOS:
                                fragment = new GirlsFragment();

                break;
            case FRAGMENT_VEDIO:
                                fragment = new GirlsFragment();

                break;
            case FRAGMENT_FRONT:
                                fragment = new GirlsFragment();

                break;
            case FRAGMENT_EXTEND:
                                fragment = new GirlsFragment();

                break;

            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
