package com.yibao.biggirl.mvp.gank.all;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yibao.biggirl.R;
import com.yibao.biggirl.util.ActivityUtils;
import com.yibao.biggirl.util.SystemUiVisibilityUtil;

/**
 * @author：Sid
 * Des：${TODO}
 * Time:2017/4/8 04:24
 */
public class AllActivity
        extends AppCompatActivity

{



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_gril);
        getSupportActionBar().setTitle("All");
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        if (savedInstanceState == null) {
            initData();

        }
    }

    private void initData() {
        AllFragment girlFragment = (AllFragment) getSupportFragmentManager().findFragmentById(R.id.content_girl_activity);


        if (girlFragment == null) {

            girlFragment = new AllFragment();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                                                girlFragment,
                                                R.id.content_girl_activity);
        }
    }



}
