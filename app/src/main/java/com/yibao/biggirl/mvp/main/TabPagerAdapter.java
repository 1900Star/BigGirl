package com.yibao.biggirl.mvp.main;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.biggirl.factory.FragmentFactory;
import com.yibao.biggirl.util.Constants;

/**
 * 作者：Stran on 2017/3/23 03:31
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 * @author Stran
 */
public class TabPagerAdapter
        extends FragmentStatePagerAdapter {

    public TabPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {
        return FragmentFactory.createFragment(position);
    }

    @Override
    public int getCount() {
        return Constants.arrTitle.length;
//        return 7;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return ((Fragment) object).getView() == view;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Constants.arrTitle[position];

    }


    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        super.destroyItem(container, position, object);
    }
}
