package com.yibao.biggirl.mvp.main;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 作者：Stran on 2017/3/23 03:31
 * 描述：${主页ViewPager的适配器}
 * 邮箱：strangermy@outlook.com
 */
public class MainPagerAdapter
        extends android.support.v4.view.PagerAdapter
{

    private List<Activity> mList;


    public MainPagerAdapter(List<Activity> list) {


        this.mList = list;
    }


    @Override
    public int getCount() {

        return mList == null
               ? 0
               : mList.size();

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
//
//    @Override
//    public void destroyItem(ViewGroup container, int position, Object object) {
//        container.removeView((View) object);
//
//    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        return mList.get(position);
    }




}
