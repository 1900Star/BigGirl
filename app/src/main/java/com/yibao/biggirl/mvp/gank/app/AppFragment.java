package com.yibao.biggirl.mvp.gank.app;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFag;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/23 06:33
 * @author Stran
 */
public class AppFragment
        extends BaseRecyclerFag<ResultsBeanX>
        implements AppContract.View {

    private AppAdapter mAdapter;


    private AppContract.Presenter mPresenter;
    private String mLoadType;



    public static AppFragment newInstance(int loadType) {
        AppFragment fragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", loadType);
        fragment.setArguments(bundle);

        return fragment;
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        new AppPresenter(this);
        int type = getArguments().getInt("type");
        mLoadType = Constants.getLoadType(type);
    }

    @Override
    protected void onLazyLoadData() {
        super.onLazyLoadData();
        mPresenter.start(mLoadType, Constants.LOAD_DATA);
    }


    @Override
    protected void refreshData() {
        mPresenter.loadData(size, 1, mLoadType, Constants.REFRESH_DATA);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void refresh(List<ResultsBeanX> list) {
        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadData(List<ResultsBeanX> list) {
        mList.clear();
        mList.addAll(list);
        mAdapter = new AppAdapter(mActivity, list);
        RecyclerView recyclerView = getRecyclerView(mFab, 1, mAdapter);
        mFagContent.addView(recyclerView);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 1));
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadData(size, page, mLoadType, Constants.LOAD_MORE_DATA);
    }

    @Override
    public void onLoadMore(List<ResultsBeanX> list) {
        mAdapter.AddFooter(list);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }


    @Override
    public void setPrenter(AppContract.Presenter prenter) {
        mPresenter = prenter;
    }
}

