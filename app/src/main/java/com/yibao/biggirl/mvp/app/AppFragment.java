package com.yibao.biggirl.mvp.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yibao.biggirl.R;
import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.dagger2.component.DaggerAppComponent;
import com.yibao.biggirl.model.dagger2.moduls.AppModuls;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/23 06:33
 */
public class AppFragment
        extends Fragment
        implements AppContract.View, SwipeRefreshLayout.OnRefreshListener
{
    AppContract.Presenter mPresenters;
    @BindView(R.id.android_frag_rv)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    private List<AndroidAndGirl> mLists = new ArrayList<>();
    private AppAdapter mAdapter;

    private int page = 1;
    private int size = 20;
    private FloatingActionButton mFab;
    @Inject
    AppPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerAppComponent component = (DaggerAppComponent) DaggerAppComponent.builder()
                                                                              .appModuls(new AppModuls(
                                                                                      this))
                                                                              .build();
        component.in(this);
        mPresenter.start(Constants.FRAGMENT_APP);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.android_frag, null);

        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);

        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
    }


    private void initData(List<AndroidAndGirl> list) {


        mAdapter = new AppAdapter(getContext(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == mAdapter.getItemCount()) {
                    boolean isRefresh = mSwipeRefresh.isRefreshing();
                    if (isRefresh) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    } else {
                        LogUtil.d("======  加载更多 来了 ==== " + lastItem);
                        mAdapter.changeMoreStatus(Constants.LOADING_DATA);
                        LogUtil.d("========  mlist  size  page    ==============" + "===" + page);
                        //                        mPresenter.loadData(size, page, Constants.LOAD_MORE_DATA);

                    }

                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    mFab.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
                lastItem = manager.findLastVisibleItemPosition();
            }
        });
    }


    @Override
    public void loadData(List<AndroidAndGirl> list) {
        mLists.clear();
        mLists.addAll(list);
        initData(mLists);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(size, 1, Constants.FRAGMENT_APP, Constants.REFRESH_DATA);

                      mSwipeRefresh.setRefreshing(false);
                      page = 1;
                  });
    }

    @Override
    public void refresh(List<AndroidAndGirl> list) {

        mAdapter.clear();
        mAdapter.AddHeader(list);
    }

    @Override
    public void loadMore(List<AndroidAndGirl> list) {
        //        if (mLists.size() % 20 == 0) {
        //
        //            page++;
        //            mPresenter.loadData(size, page, Constants.LOAD_DATA);
        //        }
        mAdapter.AddFooter(list);
        LogUtil.d("========  Add Footer    ==============" + mLists.size() + " ===" + page);

    }


    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void setPrenter(AppContract.Presenter prenter) {
        this.mPresenters = prenter;
    }

    public AppFragment newInstance() {

        return new AppFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }
}

