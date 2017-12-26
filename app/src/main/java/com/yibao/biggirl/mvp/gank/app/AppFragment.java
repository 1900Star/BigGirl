package com.yibao.biggirl.mvp.gank.app;

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
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.util.Constants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/23 06:33
 */
public class AppFragment
        extends BaseFag<ResultsBeanX>
        implements AppContract.View {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout mFagContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;

    private AppAdapter mAdapter;


    private AppContract.Presenter mPresenter;
    private String mLoadType;
    private int mType;

    public static AppFragment newInstance(int loadType) {
        AppFragment fragment = new AppFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", loadType);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mType = getArguments().getInt("type");
        new AppPresenter(this);

    }

    @Override
    protected void refreshData() {
        mPresenter.loadData(size, 1, mLoadType, Constants.REFRESH_DATA);
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadData(size, page, mLoadType, Constants.LOAD_MORE_DATA);
    }

    @Override
    public void loadDatas() {
        mLoadType = Constants.getLoadType(mType);
        mPresenter.start(mLoadType, Constants.LOAD_DATA);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.girls_frag, null);

        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    protected void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);


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
        mAdapter = new AppAdapter(getContext(), list);
        RecyclerView recyclerView = getRecyclerView(mFab, 1, mAdapter);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 1));
        mFagContent.addView(recyclerView);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void loadMore(List<ResultsBeanX> list) {

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
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }


    @Override
    public void setPrenter(AppContract.Presenter prenter) {
        mPresenter = prenter;
    }
}

