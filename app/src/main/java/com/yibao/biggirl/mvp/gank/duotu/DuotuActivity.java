package com.yibao.biggirl.mvp.gank.duotu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseActivity;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girls.Girl;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.gank.meizitu.MztuAdapter;
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
 * Des：${解析网页中的Girl}
 * Time:2017/4/8 04:24
 */
public class DuotuActivity
        extends BaseActivity implements DuotuContract.View<Girl>, OnRvItemLongClickListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.meizi_content)
    LinearLayout mMeiziContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private String mUrl;
    private MztuAdapter mAdapter;
    private ArrayList<Girl> mList;
    private DuotuPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        unbinder = ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("link");
        mPresenter = new DuotuPresenter(this);
        mPresenter.loadDataList(mUrl,page,Constants.LOAD_DATA);
        initView();
    }

    private void initView() {

        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
        mList = new ArrayList<>();

    }

    private void initRecyclerView(List<Girl> mList, int type) {
        mAdapter = new MztuAdapter(this, mList, 1);
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(type, mAdapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mFab.setVisibility(View.VISIBLE);
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof StaggeredGridLayoutManager) {
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(
                                    lastPositions);
                            lastPosition = findMax(lastPositions);
                        }

                        if (lastPosition == recyclerView.getLayoutManager()
                                .getItemCount() - 1) {
                            page++;
                            LogUtil.d(" Page  ==    "+page);
                            mPresenter.loadDataList(mUrl,
                                    page, Constants.LOAD_MORE_DATA);
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
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));

    }


    @Override
    public void loadData(List<Girl> list) {
        mList.addAll(list);
        initRecyclerView(mList, 2);
        mSwipeRefresh.setRefreshing(false);

    }

    @Override
    public void refresh(List<Girl> list) {

        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void loadMore(List<Girl> list) {
        mList.addAll(list);
        mAdapter.AddFooter(mList);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {

                    mPresenter.loadDataList(mUrl, 1,
                            Constants.REFRESH_DATA);
                    mSwipeRefresh.setRefreshing(false);
                    page = 1;
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }


    @Override
    public void showPreview(String url) {
        TopBigPicDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), "dialog_meizitu_girl");
    }

    @Override
    public void setPrenter(DuotuContract.Presenter prenter) {
        mPresenter = (DuotuPresenter) prenter;
    }

}
