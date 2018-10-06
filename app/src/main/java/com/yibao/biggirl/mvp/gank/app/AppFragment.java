package com.yibao.biggirl.mvp.gank.app;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.util.Constants;

import java.util.List;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.mvp.gank.app
 * @文件名: AppFragment
 * @author: Stran
 * @Email: www.strangermy@outlook.com / www.stranger98@gmail.com
 * @创建时间: 2018/1/13 11:06
 * @描述： {TODO}
 */

public class AppFragment extends BaseRecyclerFragment implements AppContract.View {
    private AppAdapter mAdapter;


    private AppContract.Presenter mPresenter;
    private String mLoadType;



    public static AppFragment newInstance(int loadType) {
        AppFragment fragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", loadType);
        fragment.setArguments(bundle);

        return fragment;
    }


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
        mPresenter.loadData(size, page, mLoadType, Constants.REFRESH_DATA);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void refresh(List<ResultsBeanX> list) {
        mAdapter.addHeader(list);
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
