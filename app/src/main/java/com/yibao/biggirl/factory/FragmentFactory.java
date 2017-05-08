package com.yibao.biggirl.factory;

import com.yibao.biggirl.base.BaseFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 */
public class FragmentFactory {
    public static final int                        FRAGMENT_GIRLS    = 0;//妹子
    public static final int                        FRAGMENT_ANDROID  = 1;//安卓
    public static final int                        FRAGMENT_VEDIO    = 2;//视频
    public static       Map<Integer, BaseFragment> mCacheFragmentMap = new HashMap<>();

    public static BaseFragment createFragment(int position) {

        BaseFragment fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {

            case FRAGMENT_GIRLS:
//                fragment = new GirlsFragment();

                break;
            case FRAGMENT_ANDROID:
//                fragment = new AndroidFragment();

                break;
            case FRAGMENT_VEDIO:
//                fragment = new GirlsFragment();

                break;

            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
