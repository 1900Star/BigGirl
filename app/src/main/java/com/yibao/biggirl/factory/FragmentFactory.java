package com.yibao.biggirl.factory;

import android.support.v4.app.Fragment;

import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.mvp.gank.app.AppFag;
import com.yibao.biggirl.mvp.gank.girls.GirlsFragment;
import com.yibao.biggirl.mvp.gank.meizitu.MeizituFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 */
public class FragmentFactory {
    private static final int FRAGMENT_GIRLS = 0;//妹子
    private static final int FRAGMENT_APP = 1;//App
    private static final int FRAGMENT_IOS = 2;//iOS
    private static final int FRAGMENT_FRONT = 4;//前端
    private static final int FRAGMENT_EXTEND = 5;//拓展资源
    private static final int FRAGMENT_VEDIO = 3;//视频
    private static final int FRAGMENT_ANDROID = 6;//安卓
    private static Map<Integer, BaseFag> mCacheFragmentMap = new HashMap<>();

    public static Fragment createFragment(int position) {

        BaseFag fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {
            case FRAGMENT_GIRLS:
                fragment = new GirlsFragment();
                break;
            case FRAGMENT_APP:
                fragment = new AppFag(FRAGMENT_APP);
                break;
            case FRAGMENT_IOS:
                fragment = new AppFag(FRAGMENT_IOS);
                break;
            case FRAGMENT_VEDIO:
                fragment = new AppFag(FRAGMENT_VEDIO);
                break;
            case FRAGMENT_FRONT:
                fragment = new AppFag(FRAGMENT_FRONT);
                break;
            case FRAGMENT_EXTEND:
                fragment = new AppFag(FRAGMENT_EXTEND);
                break;
            case FRAGMENT_ANDROID:
                fragment = new MeizituFragment();
                break;
            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
