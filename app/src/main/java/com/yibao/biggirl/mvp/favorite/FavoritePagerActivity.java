package com.yibao.biggirl.mvp.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseActivity;
import com.yibao.biggirl.base.BaseRecyclerActivity;
import com.yibao.biggirl.base.listener.OnRvItemSlideListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.mvp.webview.WebPresenter;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.view.FavoriteViewPager;
import com.yibao.biggirl.view.SwipeItemLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

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
