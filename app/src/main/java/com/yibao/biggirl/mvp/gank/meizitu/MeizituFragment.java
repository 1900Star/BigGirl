package com.yibao.biggirl.mvp.gank.meizitu;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.gank.meizitu
 *  @文件名:   MeizituFragment
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

public class MeizituFragment extends BaseRecyclerFragment implements

        GirlsContract.ViewMeizi<Girl> {

    private GirlsContract.Presenter mPresenter;
    private MztuAdapter mAdapter;

    private String mLoadType;

    public static MeizituFragment newInstance(int loadType) {
        MeizituFragment fragment = new MeizituFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", loadType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter = new GirlsPresenter(this);
        int position = getArguments().getInt("position");
        mLoadType = Constants.getLoadType(position);
    }

    @Override
    protected void onLazyLoadData() {
        super.onLazyLoadData();
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
        mAdapter = new MztuAdapter(mActivity, list, 1);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void refresh(List<Girl> list) {
        mAdapter.clear();
        mAdapter.addHeader(list);
    }

    @Override
    public void loadMore(List<Girl> list) {
        mAdapter.addFooter(list);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }


}
