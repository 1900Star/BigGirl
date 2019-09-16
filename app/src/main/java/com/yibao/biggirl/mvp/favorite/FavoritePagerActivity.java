package com.yibao.biggirl.mvp.favorite;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.yibao.biggirl.R;
import com.yibao.biggirl.view.FavoriteViewPager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/17 18:45
 *
 * @author Stran
 */
public class FavoritePagerActivity
        extends AppCompatActivity {


    @BindView(R.id.vp_favorite)
    FavoriteViewPager mVpFavorite;
    @BindView(R.id.tablayout_favorite)
    TabLayout mTablayoutFavorite;

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_pager_favorite);
        mBind = ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        Toolbar toolbar = findViewById(R.id.favorite_bar_pager);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());
    }

    private void initData() {
        mTablayoutFavorite.setupWithViewPager(mVpFavorite);
        FavoritePagerAdapter pagerAdapter = new FavoritePagerAdapter(getSupportFragmentManager());
        mVpFavorite.setAdapter(pagerAdapter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();

    }


}
