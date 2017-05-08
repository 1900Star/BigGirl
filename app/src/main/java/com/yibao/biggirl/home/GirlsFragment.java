package com.yibao.biggirl.home;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yibao.biggirl.R;
import com.yibao.biggirl.model.girls.ResultsBean;
import com.yibao.biggirl.util.Constants;
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
 * 作者：Stran on 2017/3/29 01:18
 * 描述：${主列表}
 * 邮箱：strangermy@outlook.com
 */
public class GirlsFragment
        extends Fragment
        implements GirlsContract.View, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.fragment_girl_recycler)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    private GirlsContract.Presenter mPresenter;
    private GirlsAdapter            mAdapter;
    private int page = 1;
    private int size = 20;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new GirlsPresenter(this);
        mPresenter.start();

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);


        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }


    private void initRecyclerView(List<ResultsBean> mList) {

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        mAdapter = new GirlsAdapter(getActivity(), (ArrayList<ResultsBean>) mList);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                                                                            StaggeredGridLayoutManager.VERTICAL);
        manager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void loadData(List<ResultsBean> list) {

        initRecyclerView(list);
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
                      page = 1;
                  });
    }

    //刷新回调
    @Override
    public void refresh(List<ResultsBean> list) {
        mAdapter.clear();
        mAdapter.AddHeader(list);
    }

    @Override
    public void loadMore(List<ResultsBean> list) {
        LogUtil.d("============ Loade More ===========  ");
        if (list.size() % 20 == 0) {
            page++;
            //            mPresenter.loadData(size, page, Constants.LOAD_DATA);
        }
        mAdapter.AddFooter(list);

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

    private void initData() {

        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            private int[] mItemPositions;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && mItemPositions.length == mAdapter.getItemCount()) {
                    boolean isRefresh = mSwipeRefresh.isRefreshing();
                    if (isRefresh) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    } else {
                        mAdapter.changeMoreStatus(Constants.LOADING_DATA);
                        mPresenter.loadData(size, page, Constants.PULLUP_LOAD_MORE_DATA);

                    }

                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                LogUtil.d("====  RecyclerView   ==" + recyclerView.getChildCount());
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                mItemPositions = manager.findLastVisibleItemPositions(new int[recyclerView.getChildCount()]);
            }
        });


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


}
