package com.yibao.biggirl.mvp.gank.meizitu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BasePicActivity;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.girl.MeizituData;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.gank.girls.GirlsContract;
import com.yibao.biggirl.mvp.gank.girls.GirlsPresenter;
import com.yibao.biggirl.service.MeizituService;
import com.yibao.biggirl.util.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${解析网页中的Girl}
 * Time:2017/4/8 04:24
 */
public class MeizituPicActivity
        extends BasePicActivity implements OnRvItemLongClickListener {

    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @BindView(R.id.meizi_content)
    LinearLayout mMeiziContent;
    @BindView(R.id.fab_fag)
    FloatingActionButton mFab;
    private GirlsContract.Presenter mPresenter;
    private String mUrl;
    private MztuAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meizi);
        unbinder = ButterKnife.bind(this);
        mUrl = getIntent().getStringExtra("link");
        mPresenter = new GirlsPresenter();
        mPresenter.start(mUrl, Constants.MeiSingle);
        getMeizituData();
        initView();
    }

    @Override
    protected void refreshData() {
        mPresenter.start(mUrl, Constants.MeiSingle);
    }


    private void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
        mAdapter = new MztuAdapter(this, null, 0);
        if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
            mPresenter.start(mUrl, 0);
        } else {
            mAdapter.notifyDataSetChanged();
            mSwipeRefresh.setRefreshing(false);
        }
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(2, mAdapter);
        rvScrollListener(recyclerView);
        mMeiziContent.addView(recyclerView);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
    }

    private void rvScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        mFab.setVisibility(View.VISIBLE);
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
    }

    //    数据来自MeizituService页面的RxBus发送
    public void getMeizituData() {
        mDisposable.add(mApplication.bus()
                .toObserverable(MeizituData.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> {
                    if (!data.getFrom().equals(mUrl)) {
                        return;
                    }
                    mSwipeRefresh.setRefreshing(false);
                    if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
                        mAdapter.setNewData(data.getGirls());
                        mSwipeRefresh.setRefreshing(false);
                    } else {
                        mAdapter.addData(mAdapter.getData().size(), data.getGirls());
                    }

                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MeizituService.stop(MeizituPicActivity.this);
        unbinder.unbind();
    }


    @Override
    public void showPreview(String url) {
        TopBigPicDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), "dialog_meizitu_girl");
    }
}
