package com.yibao.biggirl.mvp.gank.sisan;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
import com.yibao.biggirl.util.Constants;

import java.util.List;


/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.mvp.gank.sisan
 * @文件名: SisanFragment
 * @author: Stran
 * @创建时间: 2018/1/10 16:20
 * @描述： TODO
 */

public class SisanFragment extends BaseRecyclerFragment implements SisanContract.View<Girl> {
    private SisanContract.Presenter mPresenter;
    private MztuAdapter mAdapter;


    private String url;
    private int mType;

    public static SisanFragment newInstance(int loadType) {
        SisanFragment fragment = new SisanFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("position", loadType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mPresenter = new SisanPresenter(this);
        mType = getArguments().getInt("position");
    }

    @Override
    protected void onLazyLoadData() {
        super.onLazyLoadData();
        url = Constants.getLoadType(mType);
        mPresenter.start(url, page);
    }

    @Override
    protected void onVisibleToUser() {
        super.onVisibleToUser();

    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadData(url, page, Constants.LOAD_MORE_DATA);
    }


    //下拉刷新
    @Override
    protected void refreshData() {
        mPresenter.loadData(
                url, page,
                Constants.REFRESH_DATA
        );
        mSwipeRefresh.setRefreshing(false);
    }


    @Override
    public void loadData(List<Girl> list) {
        mAdapter = new MztuAdapter(mActivity, list, 3);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
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
    public void setPrenter(SisanContract.Presenter prenter) {
        this.mPresenter = prenter;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
