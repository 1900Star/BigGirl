package com.yibao.biggirl.mvp.gank.all;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.mvp.gank.app.AppAdapter;
import com.yibao.biggirl.mvp.gank.app.AppContract;
import com.yibao.biggirl.mvp.gank.app.AppPresenter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * Author：Sid
 * Des：${AllData}
 * Time:2017/4/23 06:33
 * @author Stran
 */
public class AllFragment
        extends BaseRecyclerFragment
        implements AppContract.View {

    private AppAdapter mAdapter;


    private AppContract.Presenter mPresenter;
    private String mLoadType;





    @Override
    protected void processLogic(Bundle savedInstanceState) {
        new AppPresenter(this);
        int type = getArguments().getInt("position");
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
        mAdapter.clear();
        mAdapter.addHeader(list);
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public void loadData(List<ResultsBeanX> list) {
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
        mAdapter.addFooter(list);
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

