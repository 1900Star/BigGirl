package com.yibao.biggirl.mvp.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnDeleteItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.mvp.webview.WebPresenter;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.view.SwipeItemLayout;

import java.util.ArrayList;
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
 */
public class FavoriteActivity
        extends AppCompatActivity
        implements OnDeleteItemClickListener,
        FavoriteContract.View,
        OnRvItemClickListener<FavoriteWebBean>,
        SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.fag_content)
    LinearLayout mFagContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    private List<FavoriteWebBean> mList;
    WebPresenter mWebPresenter;
    private FavoriteAdapter mAdapter;
    private String TAG = "FavoriteActivity";
    private boolean isUpdateFavo = false;
    private Unbinder mBind;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_favorite);
        mBind = ButterKnife.bind(this);
        mWebPresenter = new WebPresenter(this);
        initView();
        initData();
    }

    protected void initView() {

        ActionBar toolbar = getSupportActionBar();
        toolbar.setTitle("收藏");
        toolbar.setDisplayHomeAsUpEnabled(true);
        mList = new ArrayList<>();
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setOnRefreshListener(this);
    }

    protected void initData() {
        //        updateFavo();
        mWebPresenter.queryAllFavorite();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showDetail(FavoriteWebBean bean, Long id) {
        isUpdateFavo = true;
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("id", id);
        LogUtil.d("跳转 ID :" + bean.getId());
        intent.putExtra("favoriteBean", bean);
        startActivity(intent);
    }

    @Override
    public void deleteFavorite(Long id) {
        LogUtil.d("要删除的 ID :" + id);
        mWebPresenter.cancelFavorite(id, 0);
    }

    //收藏页面没有增加收藏功能，这个方法不会执行。
    @Override
    public void insertStatus(Long status) {
    }

    @Override
    public void cancelStatus(Long id) {
        LogUtil.d(TAG + "Adapter  取消收藏  :  " + id);
        if (id > 0) {
            mAdapter.notifyDataSetChanged();
            SnakbarUtil.showSuccessView(mFagContent);

        }
    }

    @Override
    public void queryAllFavorite(List<FavoriteWebBean> list) {
        mList.clear();
        mList.addAll(list);
        mSwipeRefresh.setRefreshing(false);

        mAdapter = new FavoriteAdapter(this, mList);
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(1, mAdapter);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mAdapter.notifyDataSetChanged();
        mFagContent.addView(recyclerView);


    }

    //这个方法不需要，WebActivity需要
    @Override
    public void queryFavoriteIsCollect(List<FavoriteWebBean> list) {

    }

    @Override
    public void onRefresh() {
        Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mWebPresenter.queryAllFavorite();
                    mAdapter.notifyDataSetChanged();
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (isUpdateFavo) {
            LogUtil.d("收藏页面刷新       ******************");
            mAdapter.refreshItem();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();

    }


    @Override
    public void showBigGirl(int position, List<FavoriteWebBean> list) {

    }
}
