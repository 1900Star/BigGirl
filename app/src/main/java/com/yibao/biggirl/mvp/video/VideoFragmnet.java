package com.yibao.biggirl.mvp.video;

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
import android.widget.ImageView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.LoadingPager;
import com.yibao.biggirl.model.video.VideoResultsBean;
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
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 06:53
 */
public class VideoFragmnet
        extends Fragment
        implements VideoContract.View, SwipeRefreshLayout.OnRefreshListener
{
    @BindView(R.id.android_frag_rv)
    RecyclerView       mAndroidFragRv;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    private VideoContract.Presenter mPresenter;
    private List<VideoResultsBean> mLists = new ArrayList<>();
    private VideoAdapter         mAdapter;
    private ImageView mIvCollapsing;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new VideoPresenter(this);
        mPresenter.start(Constants.FRAGMENT_VIDEO);

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
        mIvCollapsing = (ImageView) getActivity().findViewById(R.id.iv_collapsing);
//        mIvCollapsing.setImageResource(R.drawable.nav_header_bg);

        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setVisibility(View.VISIBLE);
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);


    }


    private void initData(List<VideoResultsBean> list) {
        mAdapter = new VideoAdapter(getContext(), list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mAndroidFragRv.setLayoutManager(manager);
        mAndroidFragRv.setHasFixedSize(true);
        mAndroidFragRv.setAdapter(mAdapter);

        mAndroidFragRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItem + 1 == mAdapter.getItemCount()) {

                    boolean isRefresh = mSwipeRefresh.isRefreshing();
                    if (isRefresh) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    } else {
                        //                        LogUtil.d("======  加载更多 来了 ==== " + lastItem);
                        mAdapter.changeMoreStatus(Constants.LOADING_DATA);
                        //                        LogUtil.d("========  mlist  size  page    ==============" + "===" + page);
                        //                        mPresenter.loadData(size, page, Constants.PULLUP_LOAD_MORE_DATA);

                    }

                } else if (newState == RecyclerView.SCROLL_STATE_DRAGGING) {
                    LogUtil.d("11111111111111111111111");
//                    mFab.setVisibility(View.INVISIBLE);
                } else if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    LogUtil.d("222222222222222222222222");
//                    mFab.setVisibility(View.VISIBLE);
                } else if (newState==RecyclerView.SCROLL_STATE_SETTLING){
//                    mFab.setVisibility(View.VISIBLE);
                    LogUtil.d("3333333333333333333333333");
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
    public void refresh(List<VideoResultsBean> list) {
        mAdapter.clear();
        mAdapter.AddHeader(list);
    }

    @Override
    public void loadMore(List<VideoResultsBean> list) {

    }

    @Override
    public LoadingPager.LoadedResult loadData(List<VideoResultsBean> list) {
        mLists.clear();
        mLists.addAll(list);
        initData(list);
        mSwipeRefresh.setRefreshing(false);
        return null;
    }

    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(500, 1, Constants.REFRESH_DATA);

                      mSwipeRefresh.setRefreshing(false);
                  });
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

    public VideoFragmnet newInstance() {
        return new VideoFragmnet();
    }

    @Override
    public void setPrenter(VideoContract.Presenter prenter) {
        mPresenter = prenter;
    }
}
