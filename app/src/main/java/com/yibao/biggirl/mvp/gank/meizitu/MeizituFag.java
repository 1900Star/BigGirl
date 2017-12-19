package com.yibao.biggirl.mvp.gank.meizitu;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.gank.meizitu
 *  @文件名:   MeizituFag
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 1:54
 *  @描述：    TODO
 */

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girls.Girl;
import com.yibao.biggirl.mvp.gank.girls.GirlsContract;
import com.yibao.biggirl.mvp.gank.girls.GirlsPresenter;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MeizituFag extends BaseFag<Girl> implements

        GirlsContract.ViewMeizi<Girl> {
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout mFagContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private GirlsContract.Presenter mPresenter;
    private MztuAdapter mAdapter;


    private String mLoadType;
    private int mType;

    public static MeizituFag newInstance(int loadType) {
        MeizituFag fragment = new MeizituFag();
        Bundle bundle = new Bundle();
        bundle.putInt("type", loadType);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GirlsPresenter(this);
        mType = getArguments().getInt("type");
    }

    @Override
    public void loadDatas() {
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

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);

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
        mAdapter = new MztuAdapter(getActivity(), mList, 0);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
