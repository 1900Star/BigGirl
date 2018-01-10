package com.yibao.biggirl.mvp.gank.meizitu;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.gank.meizitu
 *  @文件名:   MeizituRecyclerFragment
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 1:54
 *  @描述：    TODO
 */

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.gank.girls.GirlsContract;
import com.yibao.biggirl.mvp.gank.girls.GirlsPresenter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

public class MeizituRecyclerFragment extends BaseRecyclerFragment<Girl> implements

        GirlsContract.ViewMeizi<Girl> {

    private GirlsContract.Presenter mPresenter;
    private MztuAdapter mAdapter;

    private String mLoadType;
    private int mType;

    public static MeizituRecyclerFragment newInstance(int loadType) {
        MeizituRecyclerFragment fragment = new MeizituRecyclerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", loadType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter = new GirlsPresenter(this);
        mType = getArguments().getInt("type");
    }

    @Override
    protected void onLazyLoadData() {
        super.onLazyLoadData();
        mLoadType = Constants.getLoadType(mType);
        mPresenter.start(mLoadType, Constants.MEIZITU);
    }


    @Override
    protected void loadMoreData() {
        mPresenter.loadData(size,
                page, Constants.MEIZITU,
                Constants.LOAD_MORE_DATA,
                Constants.FRAGMENT_JAPAN);
    }



    //下拉刷新

    @Override
    protected void refreshData() {
        mPresenter.loadData(size,
                1, 0,
                Constants.REFRESH_DATA,
                mLoadType);
        mSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void setPrenter(GirlsContract.Presenter prenter) {
        this.mPresenter = prenter;
    }

    @Override
    public void loadData(List<Girl> list) {
        mList.addAll(list);
        mAdapter = new MztuAdapter(mActivity, mList, 1);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);

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
    public void showError() {

    }

    @Override
    public void showNormal() {

    }


}
