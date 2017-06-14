package com.yibao.biggirl.mvp.main;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.yibao.biggirl.R;
import com.yibao.biggirl.ScrollingActivity;
import com.yibao.biggirl.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ContentActivity
        extends AppCompatActivity
{

    @BindView(R.id.main_view_pager)
    ViewPager            mMainViewPager;
    @BindView(R.id.bottom_navigation_view)
    BottomNavigationView mBottomNavigationView;
    private List<Activity> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        ButterKnife.bind(this);
        initData();
        initListener();
    }

    private void initData() {
        mList = new ArrayList<>();
        mList.add(ScrollingActivity.newInstance());
        mList.add(ScrollingActivity.newInstance());
        mList.add(new WebViewActivity());
        MainPagerAdapter adapter = new MainPagerAdapter(mList);
        mMainViewPager.setAdapter(adapter);
    }

    private void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {

                case R.id.bottom_home:
                    mMainViewPager.setCurrentItem(0);
                    break;
                case R.id.bottom_service:
                    mMainViewPager.setCurrentItem(1);
                    break;
                case R.id.bottom_me:
                    mMainViewPager.setCurrentItem(2);
                    break;
                default:
                    break;
            }

            return false;

        });
        mMainViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

}
