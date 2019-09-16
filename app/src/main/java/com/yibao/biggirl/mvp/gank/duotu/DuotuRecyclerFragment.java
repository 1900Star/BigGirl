package com.yibao.biggirl.mvp.gank.duotu;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.gank.meizitu
 *  @文件名:   MeizituFragment
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 1:54
 *  @描述：    多图
 */

import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

public class DuotuRecyclerFragment extends BaseRecyclerFragment implements
        DuotuContract.View<Girl> {
    private DuotuContract.Presenter mPresenter;
    private MztuAdapter mAdapter;


    private String mLoadType;
    private int mPosition;

    public static DuotuRecyclerFragment newInstance(int loadType) {
        DuotuRecyclerFragment fragment = new DuotuRecyclerFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", loadType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter = new DuotuPresenter(this);
        mPosition = getArguments().getInt("position");
        mLoadType = Constants.getLoadType(mPosition);
    }

    @Override
    protected void onLazyLoadData() {
        super.onLazyLoadData();

        mPresenter.start(mLoadType, page);
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
        mAdapter = new MztuAdapter(mActivity, list, 2);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
    }


    @Override
    public void refresh(List<Girl> list) {
        mAdapter.addHeader(list);
    }

    @Override
    public void loadMore(List<Girl> list) {
        mAdapter.addFooter(list);
    }

    @Override
    public void setPrenter(DuotuContract.Presenter prenter) {
        this.mPresenter = prenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unsubscribe();
    }
}
