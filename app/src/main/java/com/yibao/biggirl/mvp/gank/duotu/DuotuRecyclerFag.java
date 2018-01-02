package com.yibao.biggirl.mvp.gank.duotu;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.gank.meizitu
 *  @文件名:   MeizituRecyclerFag
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 1:54
 *  @描述：    多图
 */

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFag;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

public class DuotuRecyclerFag extends BaseRecyclerFag<Girl> implements
        DuotuContract.View<Girl> {
    private DuotuContract.Presenter mPresenter;
    private MztuAdapter mAdapter;


    private String mLoadType;
    private int mType;

    public static DuotuRecyclerFag newInstance(int loadType) {
        DuotuRecyclerFag fragment = new DuotuRecyclerFag();
        Bundle bundle = new Bundle();
        bundle.putInt("type", loadType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter = new DuotuPresenter(this);
        mType = getArguments().getInt("type");
    }

    @Override
    protected void onLazyLoadData() {
        super.onLazyLoadData();

        mLoadType = Constants.getLoadType(mType);
        mPresenter.start(mLoadType, page);
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();

    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadData(mLoadType, page, Constants.LOAD_MORE_DATA);
    }


    //下拉刷新
    @Override
    protected void refreshData() {
        mPresenter.loadData(
                mLoadType, page,
                Constants.REFRESH_DATA
        );
        mSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void loadData(List<Girl> list) {
        mList.addAll(list);
        mAdapter = new MztuAdapter(mActivity, mList, 1);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
    }


    @Override
    public void refresh(List<Girl> list) {

        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Girl> list) {
        mList.addAll(list);
        mAdapter.AddFooter(mList);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void setPrenter(DuotuContract.Presenter prenter) {
        this.mPresenter = prenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
