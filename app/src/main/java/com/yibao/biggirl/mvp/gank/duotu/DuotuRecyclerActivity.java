package com.yibao.biggirl.mvp.gank.duotu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRecyclerActivity;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.base
 * @文件名: BaseRecyclerActivity
 * @author: Stran
 * @创建时间: 2017/4/8 04:24
 * @描述： 凡是页面包含RecyclerView的,都要继承这个BaseRecyclerActivity
 */
public class DuotuRecyclerActivity
        extends BaseRecyclerActivity<Girl> implements DuotuContract.View<Girl>{

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.meizi_content)
    LinearLayout mMeiziContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private String mUrl;
    private MztuAdapter mAdapter;
    private DuotuPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        unbinder = ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("link");
        mPresenter = new DuotuPresenter(this);
        mPresenter.loadDataList(mUrl, page, Constants.LOAD_DATA);
        initView();
    }

    private void initView() {

        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);

    }

    @Override
    public void loadData(List<Girl> list) {
        mList.addAll(list);
        mAdapter = new MztuAdapter(this, mList, 2);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mMeiziContent.addView(recyclerView);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void refresh(List<Girl> list) {
        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Girl> list) {
        mList.addAll(list);
        mAdapter.AddFooter(mList);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    protected void refreshData() {
        mPresenter.loadDataList(mUrl, 1,
                Constants.REFRESH_DATA);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void onLongTouchPreview(String url) {
        TopBigPicDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), "dialog_meizitu_girl");
    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadDataList(mUrl,
                page, Constants.LOAD_MORE_DATA);
    }

    @Override
    public void setPrenter(DuotuContract.Presenter prenter) {
        mPresenter = (DuotuPresenter) prenter;
    }

}
