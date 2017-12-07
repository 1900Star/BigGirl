package com.yibao.biggirl.mvp.gank.meizitu;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.model.girls.Girl;
import com.yibao.biggirl.mvp.gank.girl.GirlActivity;
import com.yibao.biggirl.mvp.gank.girls.GirlsAdapter;
import com.yibao.biggirl.mvp.gank.girls.GirlsContract;
import com.yibao.biggirl.mvp.gank.girls.GirlsPresenter;
import com.yibao.biggirl.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/8 04:24
 */
public class MeizituActivity
        extends AppCompatActivity implements GirlsContract.ViewTuLists<Girl>, SwipeRefreshLayout.OnRefreshListener, OnRvItemClickListener<String> {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.meizi_content)
    LinearLayout mMeiziContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private GirlsContract.Presenter mPresenter;
    private String mUrl;
    private GirlsAdapter mAdapter;
//    private MztuAdapter mAdapter;
    private int page = 1;
    private int size = 20;
    private List<Girl> mList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        getSupportActionBar().setTitle("Girl");
        unbinder = ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("link");
        mPresenter = new GirlsPresenter(this);
        mPresenter.start(mUrl, 0);
        initView();
        initData();
    }

    private void initView() {

        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
    }

    private void initData() {
        mList = new ArrayList<>();


    }


    private void initRvData(List<String> mList, int type) {
//        mAdapter = new MztuAdapter(this, mList);
        mAdapter = new GirlsAdapter(this, mList);
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(type, mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mFab.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof GridLayoutManager) {
                            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(
                                    lastPositions);
                            lastPosition = findMax(lastPositions);
                        }

                        if (lastPosition == recyclerView.getLayoutManager()
                                .getItemCount() - 1) {

                            page++;
//
//                            mPresenter.loadData(size,
//                                    page, 0,
//                                    Constants.LOAD_MORE_DATA,
//                                    Constants.FRAGMENT_MEIZITU);
                            LogUtil.d("PAGE===" + page);
                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        mFab.setVisibility(View.INVISIBLE);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        mFab.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }

        });
        mMeiziContent.addView(recyclerView);
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    public void loadData(List<Girl> list) {
        List<String> data = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            data.add(list.get(i).getUrl());
            LogUtil.d("Singtu   "+list.get(i).getUrl());

        }
        LogUtil.d("Singtu Size  "+data.size());
        initRvData(data, 2);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

//                    mPresenter.loadData(size,
//                            1, 0,
//                            Constants.REFRESH_DATA,
//                            mUrl);
                    mSwipeRefresh.setRefreshing(false);
                    page = 1;
                });
    }

    @Override
    public void refresh(List<Girl> list) {
        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
//        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Girl> list) {
        mList.addAll(list);
//        mAdapter.AddFooter(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void setPrenter(GirlsContract.Presenter prenter) {
        this.mPresenter = prenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void showDetail(FavoriteWebBean bean, Long id) {
    }

    @Override
    public void showBigGirl(int position, List<String> list) {
        LogUtil.d("MeiziTu  *****************");
        Intent intent = new Intent(this, GirlActivity.class);
        intent.putStringArrayListExtra("girlList", (ArrayList<String>) list);
        intent.putExtra("position", position);
        startActivity(intent);
    }
}
