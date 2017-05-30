package com.yibao.biggirl.mvp.girls;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.biggirl.factory.FragmentFactory;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

/**
 * 作者：Stran on 2017/3/23 03:31
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class TabPagerAdapter
        extends FragmentPagerAdapter
{

    public TabPagerAdapter(FragmentManager fm)
    {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        LogUtil.d("已经开始加载数据 了");
                return FragmentFactory.createFragment(position);
//        return FragmentFactorys.createFragment(position);
    }

    @Override
    public int getCount() {
        return Constants.arrTitle.length;
//        return 1;
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
