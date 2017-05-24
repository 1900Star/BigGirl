package com.yibao.biggirl.mvp.girls;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.yibao.biggirl.R;
import com.yibao.biggirl.factory.RecyclerViewFactory;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * 作者：Stran on 2017/3/29 01:18
 * 描述：${主列表}
 * 邮箱：strangermy@outlook.com
 */
public class GirlsFragment
        extends Fragment
        implements GirlsContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener
{


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.ll_grils)
    LinearLayout mLlGrils;
    private GirlsContract.Presenter mPresenter;
    private GirlsAdapter            mAdapter;
    private List<String>            mList;
    private int page = 1;
    private FloatingActionButton mFab;
    private boolean              isShowGankGirl;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GirlsPresenter(this);
        mPresenter.start(Constants.FRAGMENT_GIRLS);


    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        //        initData();
        return view;
    }


    private void initView() {
        mList = new ArrayList<>();
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setOnClickListener(this);

    }


    private void initRecyclerView(List<String> mList, int type) {
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        mAdapter = new GirlsAdapter(getActivity(), mList);
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(type, mFab, mAdapter);
        mLlGrils.addView(recyclerView);
    }


    @Override
    public void loadData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        initRecyclerView(mList, 2);
        mSwipeRefresh.setRefreshing(false);
    }

    //下拉刷新
    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(500, page, Constants.REFRESH_DATA);
                      LogUtil.d("page   ====== " + page);
                      mSwipeRefresh.setRefreshing(false);
                      isShowGankGirl = false;
                      page = 1;
                  });
    }

    //刷新回调
    @Override
    public void refresh(List<String> list) {
        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(mList);
    }

    @Override
    public void loadMore(List<String> list) {
        if (list.size() % 20 == 0) {
            page++;
            //            mPresenter.loadData(size, page, Constants.LOAD_DATA);
        }
        mList.addAll(list);
        mAdapter.AddFooter(mList);

        mAdapter.changeMoreStatus(Constants.PULLUP_LOAD_MORE_DATA);
        mSwipeRefresh.setRefreshing(false);
        Toast.makeText(getActivity(), "更新了 " + list.size() + "个妹子", Toast.LENGTH_SHORT);

    }

    @Override
    public void showError() {
        mAdapter.changeMoreStatus(Constants.NO_MORE_DATA);
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
    public void setPrenter(GirlsContract.Presenter prenter) {
        this.mPresenter = prenter;
    }

    public GirlsFragment newInstance() {
        return new GirlsFragment();
    }

    //切换干货和默认妹子
    @Override
    public void onClick(View view) {

        if (isShowGankGirl) {
            mLlGrils.removeAllViews();
            initRecyclerView(mList, 2);
            mSwipeRefresh.setRefreshing(false);
            isShowGankGirl = false;


        } else {
            getDefultGirl();
            isShowGankGirl = true;
        }

    }

    private void getDefultGirl() {
        mLlGrils.removeAllViews();
        List<String> defultUrl = ImageUitl.getDefultUrl(new ArrayList<>());
        Random       random    = new Random();

        initRecyclerView(defultUrl, random.nextInt(4)+1);
        mSwipeRefresh.setRefreshing(false);
    }
}
