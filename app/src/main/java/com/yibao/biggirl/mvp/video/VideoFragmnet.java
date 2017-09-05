package com.yibao.biggirl.mvp.video;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.factory.RecyclerViewFactory;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.mvp.app.AppContract;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

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
 * Time:2017/5/9 06:53
 */
public class VideoFragmnet
        extends BaseFag<ResultsBeanX>
        implements SwipeRefreshLayout.OnRefreshListener,AppContract.View
{


    @BindView(R.id.fag_content)
    LinearLayout       mFagContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private VideoAdapter mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
//            mGirlsPresenter.start(Constants.FRAGMENT_VIDEO, 2);

        }
    }

    @Override
    public void loadDatas() {
        //                mGirlsPresenter.start(Constants.FRAGMENT_ANDROID, 2);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.girls_frag, null);


        unbinder = ButterKnife.bind(this, view);
        LogUtil.d("Video  *******");
        initView();
        return view;
    }


    protected void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
    }

    private void initRecyclerView(List<ResultsBeanX> list, int type, String dataType) {

        mAdapter = new VideoAdapter(getActivity(), list);
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(type,

                                                                          mAdapter);


        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    //当前状态为停止滑动状态SCROLL_STATE_IDLE时
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mFab.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof GridLayoutManager) {
                            //通过LayoutManager找到当前显示的最后的item的position
                            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            //因为StaggeredGridLayoutManager的特殊性可能导致最后显示的item存在多个，所以这里取到的是一个数组
                            //得到这个数组后再取到数组中position值最大的那个就是最后显示的position值了
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(
                                    lastPositions);
                            lastPosition = findMax(lastPositions);
                        }

                        //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
                        //如果相等则说明已经滑动到最后了
                        if (lastPosition == recyclerView.getLayoutManager()
                                                        .getItemCount() - 1)
                        {
                            page++;

//                            mGirlsPresenter.loadData(size,
//                                                     page,
//                                                     Constants.FRAGMENT_VIDEO,
//                                                     Constants.LOAD_DATA);
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

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager()
                                                 .getChildAt(recyclerView.getLayoutManager()
                                                                         .getChildCount() - 1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition = recyclerView.getLayoutManager()
                                               .getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部
                if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager()
                                                                                     .getItemCount() - 1)
                {
                    //                    mProgressBar.setVisibility(View.VISIBLE);
                }
            }

        });

        mFagContent.addView(recyclerView);
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
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {

//                      mGirlsPresenter.loadData(size,
//                                               page,
//                                               Constants.FRAGMENT_VIDEO,
//                                               Constants.LOAD_DATA);

                      mSwipeRefresh.setRefreshing(true);
                      page = 1;
                  });
    }

    @Override
    public void loadData(List<ResultsBeanX> list) {
        mList.addAll(list);
        initRecyclerView(mList, 1, Constants.FRAGMENT_VIDEO);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void refresh(List<ResultsBeanX> list) {
        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();
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

    public VideoFragmnet newInstance() {
        return new VideoFragmnet();
    }




    @OnClick(R.id.fab_fag)
    public void onViewClicked() {LogUtil.d("VideoFragment ");}



    @Override
    public void setPrenter(AppContract.Presenter prenter) {

    }
}
