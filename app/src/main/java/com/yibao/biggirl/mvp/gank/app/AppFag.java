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
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.util.Constants;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/23 06:33
 */
public class AppFag
        extends BaseFag<ResultsBeanX>
        implements SwipeRefreshLayout.OnRefreshListener, AppContract.View {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout mFagContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;

    private AppAdapter mAdapter;


    private AppContract.Presenter mPresenter;
    private int type;
    private String mLoadType;
    private RecyclerView mRecyclerView;

    public AppFag() {
    }

    public AppFag(int type) {
        this.type = type;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new AppPresenter(this);

    }

    @Override
    protected void loadMoreData() {
        mPresenter.loadData(size, page, mLoadType, Constants.LOAD_MORE_DATA);
    }

    @Override
    public void loadDatas() {
        mLoadType = Constants.getLoadType(type);
        mPresenter.start(mLoadType, 2);

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
    public void onRefresh() {

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mPresenter.loadData(size, 1, mLoadType, Constants.REFRESH_DATA);
                    mSwipeRefresh.setRefreshing(false);
                    page = 1;
                });
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
//        initData(mList);
        mAdapter = new AppAdapter(getContext(), list);
        RecyclerView recyclerView = getRecyclerView(mFab, 1, mAdapter);
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

    @OnClick(R.id.fab_fag)
    public void onViewClicked() {
        RecyclerFactory.backTop(mRecyclerView, 1);
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

