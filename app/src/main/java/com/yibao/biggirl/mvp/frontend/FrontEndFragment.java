package com.yibao.biggirl.mvp.frontend;

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
import com.yibao.biggirl.factory.RecyclerViewFactory;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.mvp.app.AppAdapter;
import com.yibao.biggirl.mvp.app.AppContract;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

import java.util.ArrayList;
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
public class FrontEndFragment
        extends BaseFag<ResultsBeanX>
        implements SwipeRefreshLayout.OnRefreshListener,AppContract.View
{
    AppContract.Presenter mPresenter;

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout         mFagContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private AppAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        List<ResultsBeanX> list = new ArrayList<>();
//        new GirlsPresenter(this);
        mPresenter.start(Constants.FRAGMENT_FRONT, 7);

    }


    @Override
    public void loadDatas() {
        //        mPresenter.start(Constants.FRAGMENT_ANDROID, 7);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);

        unbinder = ButterKnife.bind(this, view);
        LogUtil.d("Fron  *******");
        initView();
        return view;
    }

    protected void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
    }

    private void initData(List<ResultsBeanX> list, int type) {


        mAdapter = new AppAdapter(getContext(), list);

        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(type, mAdapter);

        //        initListerner(recyclerView);

        mFagContent.addView(recyclerView);
    }


    @Override
    public void loadData(List<ResultsBeanX> list) {
        mList.clear();
        mList.addAll(list);
        initData(mList, 1);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(size,
                                          1,
                                          Constants.FRAGMENT_FRONT,
                                          Constants.REFRESH_DATA);

                      mSwipeRefresh.setRefreshing(false);
                      page = 1;
                  });
    }

    @Override
    public void refresh(List<ResultsBeanX> list) {

        mAdapter.clear();
        mAdapter.AddHeader(list);
    }

    @Override
    public void loadMore(List<ResultsBeanX> list) {

        mAdapter.AddFooter(list);

    }


    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }


    public FrontEndFragment newInstance() {

        return new FrontEndFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }


    @OnClick(R.id.fab_fag)
    public void onViewClicked() { LogUtil.d(" FrontEndFragment ");}

    @Override
    public void setPrenter(AppContract.Presenter prenter) {

    }
}

