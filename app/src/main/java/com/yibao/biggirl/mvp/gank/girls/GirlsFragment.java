package com.yibao.biggirl.mvp.gank.girls;


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
import com.yibao.biggirl.model.dagger2.component.DaggerGirlsComponent;
import com.yibao.biggirl.model.dagger2.moduls.GirlsModuls;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

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
        extends BaseFag<String>
        implements
        View.OnLongClickListener,
        GirlsContract.View<String> {


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout mLlGrils;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private GirlsAdapter mAdapter;

    private boolean isShowGankGirl;


    // 1 指定注入目标
    @Inject
    GirlsPresenter mGirlsPresenter;

    private int mRandomNum;

    public GirlsFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerGirlsComponent.builder()
                .girlsModuls(new GirlsModuls(this))
                .build()
                .in(this);

        mGirlsPresenter.start(Constants.FRAGMENT_GIRLS, Constants.GIRLS);

    }

    @Override
    protected void loadMoreData() {
        mGirlsPresenter.loadData(size,
                page, 3,
                Constants.LOAD_MORE_DATA,
                Constants.FRAGMENT_GIRLS);
    }

    @Override
    public void loadDatas() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initListener();
        return view;
    }


    private void initListener() {
        mFab.setOnLongClickListener(this);
        mFab.setOnClickListener(view -> initData(0));
    }


    protected void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);


    }

    private void initData(int type) {
        switch (type) {
            case 0:
                if (isShowGankGirl) {
                    mLlGrils.removeAllViews();
                    initRecyclerView(mList, 2);
                    mSwipeRefresh.setRefreshing(false);
                    isShowGankGirl = false;
                } else {
                    getDefultGirl(0);
                    isShowGankGirl = true;
                }
                break;
            case 1:
                getDefultGirl(1);
                break;
            default:
                break;
        }

    }


    private void initRecyclerView(List<String> mList, int type) {
        Random random = new Random();
        mRandomNum = random.nextInt(4) + 1;
        mAdapter = new GirlsAdapter(getActivity(), mList);
        RecyclerView recyclerView = getRecyclerView(mFab, type, mAdapter);
        mLlGrils.addView(recyclerView);
    }


    //下拉刷新
    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mGirlsPresenter.loadData(size,
                            1, 3,
                            Constants.REFRESH_DATA,
                            Constants.FRAGMENT_GIRLS);
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
        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void loadData(List<String> list) {
        mList.addAll(list);
        initRecyclerView(mList, 2);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void loadMore(List<String> list) {
        mList.addAll(list);
        mAdapter.AddFooter(list);
        mAdapter.notifyDataSetChanged();

    }


    //切换干货和默认妹子

    private void getDefultGirl(int type) {
        mLlGrils.removeAllViews();
        List<String> defultUrl;
        if (type == 1) {
            defultUrl = ImageUitl.getSexUrl(new ArrayList<>());
        } else {
            defultUrl = ImageUitl.getDefultUrl(new ArrayList<>());

        }
        initRecyclerView(defultUrl, mRandomNum);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void setPrenter(GirlsContract.Presenter prenter) {
    }


    @Override
    public boolean onLongClick(View view) {
        initData(1);
        return true;
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


}
