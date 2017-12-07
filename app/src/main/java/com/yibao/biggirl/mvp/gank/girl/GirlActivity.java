package com.yibao.biggirl.mvp.gank.girl;


import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.HideToolbarListener;
import com.yibao.biggirl.util.ActivityUtils;
import com.yibao.biggirl.util.SystemUiVisibilityUtil;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/8 04:24
 */
public class GirlActivity
        extends AppCompatActivity
        implements HideToolbarListener
{


    private Bundle mBundle = null;
    private View      mDecorView;
    private ActionBar mActionBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_gril);
        getSupportActionBar().setTitle("Girl");
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        if (savedInstanceState == null) {
            mDecorView = getWindow().getDecorView();
            mActionBar = getSupportActionBar();
            mBundle = getIntent().getExtras();
            initData();

        }
    }

    private void initData() {
        GirlFragment girlFragment = (GirlFragment) getSupportFragmentManager().findFragmentById(R.id.content_girl_activity);


        if (girlFragment == null) {

            girlFragment = new GirlFragment().newInstance();
            girlFragment.setArguments(mBundle);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                                                girlFragment,
                                                R.id.content_girl_activity);
        }
    }


    @Override
    public void hideToolbar() {
        if (Build.VERSION.SDK_INT >= 21) {


            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);

        }
        mActionBar.hide();

    }

    @Override
    public void showToolbar() {
        int option = View.SYSTEM_UI_FLAG_VISIBLE;
        mDecorView.setSystemUiVisibility(option);
        mActionBar.show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}
