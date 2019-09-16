package com.yibao.biggirl.mvp.favorite;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRecyclerActivity;
import com.yibao.biggirl.base.listener.OnRvItemSlideListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.mvp.webview.WebPresenter;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.view.SwipeItemLayout;

import java.util.List;
import java.util.Objects;
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
public class FavoriteActivity
        extends BaseRecyclerActivity
        implements OnRvItemSlideListener,
        FavoriteContract.View {


    @BindView(R.id.favorite_content)
    LinearLayout mFagContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    WebPresenter mWebPresenter;
    private FavoriteAdapter mAdapter;

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

    private void initData() {
        mAdapter = new FavoriteAdapter(this, null);
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(1, mAdapter);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(this));
        mAdapter.notifyDataSetChanged();
        mAdapter.changeLoadMoreStatus(Constants.NOT_MORE_DATA_RV);
        mFagContent.addView(recyclerView);

    }


    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar_favorite);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setOnRefreshListener(this);
    }


    @Override
    public void showWebDetail(FavoriteWebBean bean, Long id) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("id", id);
        LogUtil.d("跳转 ID :" + bean.getId());
        intent.putExtra("favoriteBean", bean);
        startActivity(intent);
    }

    @Override
    protected void loadMoreData() {

    }

    @Override
    public void deleteFavorite(Long id) {
        LogUtil.d("要删除的 ID :" + id);
        mWebPresenter.cancelFavorite(id, 0);
    }


    /**
     * 收藏页面没有增加收藏功能，这个方法不会执行。
     *
     * @param status
     */
    @Override
    public void insertStatus(Long status) {
    }

    @Override
    public void cancelStatus(Long id) {

    }


    @Override
    public void queryAllFavorite(List<FavoriteWebBean> list) {
        mAdapter.setNewData(list);
        mSwipeRefresh.setRefreshing(false);


    }


    /**
     * 这个方法不需要，WebActivity需要
     *
     * @param list
     */
    @Override
    public void queryFavoriteIsCollect(List<FavoriteWebBean> list) {

    }


    @Override
    protected void refreshData() {
        mDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mWebPresenter.queryAllFavorite();
                    mAdapter.notifyDataSetChanged();
                }));

    }

    @Override
    public void onResume() {
        super.onResume();
        mWebPresenter.queryAllFavorite();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        mWebPresenter.destroyView();
    }


}
