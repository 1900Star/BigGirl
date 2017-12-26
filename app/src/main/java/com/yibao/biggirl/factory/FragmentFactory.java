package com.yibao.biggirl.factory;

import android.support.v4.app.Fragment;

import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.mvp.gank.app.AppFragment;
import com.yibao.biggirl.mvp.gank.duotu.DuotuFag;
import com.yibao.biggirl.mvp.gank.girls.GirlsFragment;
import com.yibao.biggirl.mvp.gank.meizitu.MeizituFag;

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
    private static final int FRAGMENT_VEDIO = 3;//视频
    private static final int FRAGMENT_FRONT = 4;//前端
    private static final int FRAGMENT_EXTEND = 5;//拓展资源
    private static final int FRAGMENT_JAPAN = 6;//Japan
    private static final int FRAGMENT_HOT = 7;//Hot
    private static final int FRAGMENT_SEX = 8;//Sex
    private static final int FRAGMENT_CUTE = 9;//Cute

    private static final int FRAGMENT_MFSTAR = 10;//MFStar
    private static final int FRAGMENT_PANS = 11;//Pans
    private static final int FRAGMENT_UGIRLS = 12;//Ugirls
    private static final int FRAGMENT_ROSI = 13;//Rosi
    private static final int FRAGMENT_MEIYAN = 14;//Meiyan
    private static final int FRAGMENT_TUIGIRL = 15;//TuiGirl

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
                fragment = AppFragment.newInstance(FRAGMENT_APP);
                break;
            case FRAGMENT_IOS:
                fragment = AppFragment.newInstance(FRAGMENT_IOS);
                break;
            case FRAGMENT_VEDIO:
                fragment = AppFragment.newInstance(FRAGMENT_VEDIO);
                break;
            case FRAGMENT_FRONT:
                fragment = AppFragment.newInstance(FRAGMENT_FRONT);
                break;
            case FRAGMENT_EXTEND:
                fragment = AppFragment.newInstance(FRAGMENT_EXTEND);
                break;
//                妹子图
            case FRAGMENT_JAPAN:
                fragment = MeizituFag.newInstance(FRAGMENT_JAPAN);
                break;
            case FRAGMENT_HOT:
                fragment = MeizituFag.newInstance(FRAGMENT_HOT);
                break;
            case FRAGMENT_SEX:
                fragment = MeizituFag.newInstance(FRAGMENT_SEX);
                break;
            case FRAGMENT_CUTE:
                fragment = MeizituFag.newInstance(FRAGMENT_CUTE);
                break;
//                多图
            case FRAGMENT_MFSTAR:
                fragment = DuotuFag.newInstance(FRAGMENT_MFSTAR);
                break;
            case FRAGMENT_PANS:
                fragment = DuotuFag.newInstance(FRAGMENT_PANS);
                break;
            case FRAGMENT_UGIRLS:
                fragment = DuotuFag.newInstance(FRAGMENT_UGIRLS);
                break;
            case FRAGMENT_ROSI:
                fragment = DuotuFag.newInstance(FRAGMENT_ROSI);
                break;
            case FRAGMENT_MEIYAN:
                fragment = DuotuFag.newInstance(FRAGMENT_MEIYAN);
                break;
            case FRAGMENT_TUIGIRL:
                fragment = DuotuFag.newInstance(FRAGMENT_TUIGIRL);
                break;
            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
