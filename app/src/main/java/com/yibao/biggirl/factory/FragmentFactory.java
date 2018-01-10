package com.yibao.biggirl.factory;

import android.support.v4.app.Fragment;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.mvp.gank.app.AppFragment;
import com.yibao.biggirl.mvp.gank.duotu.DuotuRecyclerFragment;
import com.yibao.biggirl.mvp.gank.girls.GirlsFragment;
import com.yibao.biggirl.mvp.gank.meizitu.MeizituRecyclerFragment;
import com.yibao.biggirl.mvp.gank.sisan.SisanFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 */
public class FragmentFactory {
    private static final int FRAGMENT_GIRLS = 0;//妹子
    private static final int FRAGMENT_ANDRID = 1;//Android
    private static final int FRAGMENT_APP = 2;//App
    private static final int FRAGMENT_IOS = 3;//iOS
    private static final int FRAGMENT_VEDIO = 4;//视频
    private static final int FRAGMENT_FRONT = 5;//前端
    private static final int FRAGMENT_EXTEND = 6;//拓展资源
    private static final int FRAGMENT_JAPAN = 7;//Japan
    private static final int FRAGMENT_HOT = 8;//Hot
    private static final int FRAGMENT_SEX = 9;//Sex
    private static final int FRAGMENT_CUTE = 10;//Cute

    private static final int FRAGMENT_MFSTAR = 11;//MFStar
    private static final int FRAGMENT_PANS = 12;//Pans
    private static final int FRAGMENT_UGIRLS = 13;//Ugirls
    private static final int FRAGMENT_ROSI = 14;//Rosi
    private static final int FRAGMENT_MEIYAN = 15;//Meiyan
    private static final int FRAGMENT_TUIGIRL = 16;//TuiGirl
    private static final int FRAGMENT_BOBOSHE = 17;//Boboshe
    private static final int FRAGMENT_DISIYINGXIANG = 18;//第四印象
    private static final int FRAGMENT_YUNVLANG = 19;//Yunvlang
    private static final int FRAGMENT_XIUREN = 20;//秀人
    private static final int FRAGMENT_SXINGGAN = 21;//43 性感
    private static final int FRAGMENT_SGAOQING = 22;//43 高清
    private static final int FRAGMENT_SWEIMEI = 23;//43 唯美

    private static Map<Integer, BaseRecyclerFragment> mCacheFragmentMap = new HashMap<>();

    public static Fragment createFragment(int position) {

        BaseRecyclerFragment fragment = null;


        //优先从集合中取出来
        if (mCacheFragmentMap.containsKey(position)) {
            fragment = mCacheFragmentMap.get(position);
            return fragment;
        }

        switch (position) {
            case FRAGMENT_GIRLS:
                fragment = new GirlsFragment();
                break;
            case FRAGMENT_ANDRID:
                fragment = AppFragment.newInstance(FRAGMENT_ANDRID);
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
                fragment = MeizituRecyclerFragment.newInstance(FRAGMENT_JAPAN);
                break;
            case FRAGMENT_HOT:
                fragment = MeizituRecyclerFragment.newInstance(FRAGMENT_HOT);
                break;
            case FRAGMENT_SEX:
                fragment = MeizituRecyclerFragment.newInstance(FRAGMENT_SEX);
                break;
            case FRAGMENT_CUTE:
                fragment = MeizituRecyclerFragment.newInstance(FRAGMENT_CUTE);
                break;
//                多图
            case FRAGMENT_MFSTAR:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_MFSTAR);
                break;
            case FRAGMENT_PANS:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_PANS);
                break;
            case FRAGMENT_UGIRLS:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_UGIRLS);
                break;
            case FRAGMENT_ROSI:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_ROSI);
                break;
            case FRAGMENT_MEIYAN:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_MEIYAN);
                break;
            case FRAGMENT_TUIGIRL:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_TUIGIRL);
                break;
            case FRAGMENT_BOBOSHE:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_BOBOSHE);
                break;
            case FRAGMENT_DISIYINGXIANG:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_DISIYINGXIANG);
                break;
            case FRAGMENT_YUNVLANG:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_YUNVLANG);
                break;
            case FRAGMENT_XIUREN:
                fragment = DuotuRecyclerFragment.newInstance(FRAGMENT_XIUREN);
                break;
//                4493
            case FRAGMENT_SXINGGAN:
                fragment = SisanFragment.newInstance(FRAGMENT_SXINGGAN);
                break;
            case FRAGMENT_SGAOQING:
                fragment = SisanFragment.newInstance(FRAGMENT_SGAOQING);
                break;
            case FRAGMENT_SWEIMEI:
                fragment = SisanFragment.newInstance(FRAGMENT_SWEIMEI);
                break;
            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
