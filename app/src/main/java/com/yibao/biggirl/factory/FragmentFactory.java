package com.yibao.biggirl.factory;

import android.annotation.SuppressLint;
import androidx.fragment.app.Fragment;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.mvp.gank.app.AppFragment;
import com.yibao.biggirl.mvp.gank.duotu.DuotuRecyclerFragment;
import com.yibao.biggirl.mvp.gank.girls.GirlsFragment;

import java.util.HashMap;
import java.util.Map;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 14:55
 *
 * @author Stran
 */
public class FragmentFactory {
    private static final int FRAGMENT_GIRLS = 0;
    private static final int FRAGMENT_ANDRID = 1;
    private static final int FRAGMENT_APP = 2;
    private static final int FRAGMENT_IOS = 3;
    private static final int FRAGMENT_VEDIO = 4;
    private static final int FRAGMENT_FRONT = 5;
    private static final int FRAGMENT_EXTEND = 6;

    private static final int FRAGMENT_MFSTAR = 7;
    private static final int FRAGMENT_PANS = 8;
    private static final int FRAGMENT_UGIRLS = 9;
    private static final int FRAGMENT_ROSI = 10;
    private static final int FRAGMENT_MEIYAN = 11;
    private static final int FRAGMENT_TUIGIRL = 12;
    private static final int FRAGMENT_BOBOSHE = 13;
    private static final int FRAGMENT_DISIYINGXIANG = 14;
    private static final int FRAGMENT_YUNVLANG = 15;
    private static final int FRAGMENT_XIUREN = 16;
    private static final int TYPE_SABAR = 17;
    private static final int TYPE_TOPQUEEN = 18;
    private static final int TYPE_IMAGETV = 19;
    private static final int TYPE_WPB = 20;
    private static final int TYPE_YS = 21;
    private static final int TYPE_TOUTIAO = 22;
    private static final int TYPE_DGC = 23;
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, BaseRecyclerFragment> mCacheFragmentMap = new HashMap<>(25);

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
            case TYPE_SABAR:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_SABAR);
                break;
            case TYPE_TOPQUEEN:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_TOPQUEEN);
                break;
            case TYPE_IMAGETV:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_IMAGETV);
                break;
            case TYPE_WPB:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_WPB);
                break;
            case TYPE_YS:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_YS);
                break;
            case TYPE_TOUTIAO:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_TOUTIAO);
                break;
            case TYPE_DGC:
                fragment = DuotuRecyclerFragment.newInstance(TYPE_DGC);
                break;
            default:
                break;
        }
        //保存fragment到集合中
        mCacheFragmentMap.put(position, fragment);

        return fragment;
    }
}
