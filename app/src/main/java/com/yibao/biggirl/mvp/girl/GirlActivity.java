package com.yibao.biggirl.mvp.girl;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/8 04:24
 */
public class GirlActivity
        extends AppCompatActivity
{
    @BindView(R.id.content_girl_activity)
    FrameLayout mContentGirl;

    private Bundle mBundle = null;
    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_gril);
        mBind = ButterKnife.bind(this);
        if (savedInstanceState == null) {
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
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();


    }
}
