package com.yibao.biggirl.mvp.gank.duotu;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.gank.meizitu
 *  @文件名:   MeizituFag
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 1:54
 *  @描述：    多图
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girls.Girl;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class DuotuFag extends BaseFag<Girl> implements
        DuotuContract.View<Girl> {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout mFagContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private DuotuContract.Presenter mPresenter;
    private MztuAdapter mAdapter;


    private String mLoadType;
    private int mType;

    public static DuotuFag newInstance(int loadType) {
        DuotuFag fragment = new DuotuFag();
        Bundle bundle = new Bundle();
        bundle.putInt("type", loadType);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new DuotuPresenter(this);
        mType = getArguments().getInt("type");
    }

    @Override
    public void loadDatas() {
        mLoadType = Constants.getLoadType(mType);
        mPresenter.start(mLoadType, page);

    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadData(mLoadType, page, Constants.LOAD_MORE_DATA);
    }


    @Override
    public android.view.View onCreateView(LayoutInflater inflater,
                                          ViewGroup container,
                                          Bundle savedInstanceState) {

        android.view.View view = android.view.View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
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
        mAdapter = new MztuAdapter(getActivity(), mList, 1);
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);
    }


    public DuotuFag() {
    }

    private void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);

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
        unbinder.unbind();
    }
}
