package com.yibao.biggirl.mvp.girls;


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
import butterknife.OnClick;
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
        implements SwipeRefreshLayout.OnRefreshListener,
                   View.OnLongClickListener,
                   GirlsContract.View<String>
{


    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.fag_content)
    LinearLayout         mLlGrils;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private GirlsAdapter mAdapter;

    private boolean isShowGankGirl;

    // 1 指定注入目标
    @Inject
    GirlsPresenter mGirlsPresenter;
    private int mRandomNum;
    //    private ArrayList<String> mList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //        mList = new ArrayList<>();
        DaggerGirlsComponent.builder()
                            .girlsModuls(new GirlsModuls(this))
                            .build()
                            .in(this);
        mGirlsPresenter.start(Constants.FRAGMENT_GIRLS, 0);


    }


    @Override
    public void loadDatas() {

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
        mFab.setOnLongClickListener(this);
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
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(type, mAdapter);
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
                                                        .getItemCount() - 1)
                        {
                            page++;

                            mGirlsPresenter.loadData(size,
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

                View lastChildView = recyclerView.getLayoutManager()
                                                 .getChildAt(recyclerView.getLayoutManager()
                                                                         .getChildCount() - 1);
                int lastChildBottom = lastChildView.getBottom();
                int recyclerBottom  = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                int lastPosition = recyclerView.getLayoutManager()
                                               .getPosition(lastChildView);

                if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager()
                                                                                     .getItemCount() - 1)
                {

                }
            }

        });
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

    //下拉刷新
    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mGirlsPresenter.loadData(size,
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
        //        mGirlsPresenter = (GirlsPresenter) prenter;
    }


    @OnClick(R.id.fab_fag)
    public void onViewClicked() {
        initData(0);
    }

    @Override
    public boolean onLongClick(View view) {
        initData(1);
        return true;
    }


}
