package com.yibao.biggirl.mvp.girls;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.yibao.biggirl.model.dagger2.component.DaggerGirlsComponent;
import com.yibao.biggirl.model.dagger2.moduls.GirlsModuls;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;

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
        implements GirlsContract.View, SwipeRefreshLayout.OnRefreshListener
{


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout mLlGrils;
    private GirlsAdapter mAdapter;

    private boolean isShowGankGirl;
    // 1 指定注入目标
    @Inject
    GirlsPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerGirlsComponent component = (DaggerGirlsComponent) DaggerGirlsComponent.builder()
                                                                                    .girlsModuls(new GirlsModuls(
                                                                                            this))
                                                                                    .build();
        component.in(this);
        mPresenter.start(Constants.FRAGMENT_GIRLS, 0);


    }
    @Override
    public void loadData() {
//        mPresenter.start(Constants.FRAGMENT_ANDROID, 0);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        LogUtil.d("GirlsFragment  *******");
        initView();
        initListener();
        return view;
    }


    private void initListener() {

    }


    protected void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);


    }

    private void initData() {
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


    private void initRecyclerView(List<String> mList, int type) {

        mAdapter = new GirlsAdapter(getActivity(), mList);
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(type, mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                //当前RecyclerView显示出来的最后一个的item的position
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

                            mPresenter.loadData(size,
                                                page,
                                                Constants.LOAD_MORE_DATA,
                                                Constants.FRAGMENT_GIRLS);
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
                    //                                                            mProgressBar.setVisibility(View.VISIBLE);

                }
            }

        });
        mFab.setOnClickListener(view -> GirlsFragment.this.initData());
        mLlGrils.addView(recyclerView);
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
    public void loadData(List<String> list) {
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
                      mPresenter.loadData(size,
                                          1,
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
    public void loadMore(List<String> list) {
        mList.addAll(list);
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


    public GirlsFragment newInstance() {
        return new GirlsFragment();
    }


    //切换干货和默认妹子

    private void getDefultGirl() {
        mLlGrils.removeAllViews();
        List<String> defultUrl = ImageUitl.getDefultUrl(new ArrayList<>());
        Random       random    = new Random();

        initRecyclerView(defultUrl, random.nextInt(4) + 1);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void setPrenter(GirlsContract.Presenter prenter) {
        mPresenter = (GirlsPresenter) prenter;
    }


}
