package com.yibao.biggirl.factory;

import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.mvp.video.VideoFragmnets;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 */
public class FragmentFactorys {
    public static final int                        FRAGMENT_GIRLS    = 0;//妹子
    public static final int                        FRAGMENT_ANDROID  = 1;//安卓

    public static       Map<Integer, BaseFag> mCacheFragmentMap = new HashMap<>();

    public static BaseFag createFragment(int position) {

        BaseFag fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {

            case FRAGMENT_GIRLS:
                fragment = new VideoFragmnets();
                break;
            case FRAGMENT_ANDROID:
                fragment = new VideoFragmnets();
                break;

            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
