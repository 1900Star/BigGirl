package com.yibao.biggirl.mvp.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.biggirl.factory.FragmentFactory;
import com.yibao.biggirl.util.Constants;

/**
 * 作者：Stran on 2017/3/23 03:31
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class TabPagerAdapter
        extends FragmentStatePagerAdapter
{

    public TabPagerAdapter(FragmentManager fm)
    {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
                return FragmentFactory.createFragment(position);
//        return FragmentFactorys.INSTANCE.createFragment(position);
    }

    @Override
    public int getCount() {
        return Constants.arrTitle.length;
//        return 6;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Constants.arrTitle[position];

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }
}
