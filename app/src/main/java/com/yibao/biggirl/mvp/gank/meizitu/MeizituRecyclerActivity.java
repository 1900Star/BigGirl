package com.yibao.biggirl.mvp.gank.meizitu;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRecyclerActivity;
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
public class MeizituRecyclerActivity
        extends BaseRecyclerActivity {
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
        mPresenter.loadData(size, page, Constants.MeiSingle, Constants.LOAD_MORE_DATA, mUrl);
    }

    private void initView() {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
        mAdapter = new MztuAdapter(this, null, 1);
        mAdapter.changeLoadMoreStatus(Constants.NOTHING_MORE_RV);
        if (mAdapter.getData() == null || mAdapter.getData().size() == 0) {
            mPresenter.start(mUrl, 0);
        } else {
            mAdapter.notifyDataSetChanged();
            mSwipeRefresh.setRefreshing(false);
        }
        RecyclerView recyclerView = getRecyclerView(mFab, 2, mAdapter);

        mMeiziContent.addView(recyclerView);
        mFab.setOnClickListener(view -> RecyclerFactory.backTop(recyclerView, 2));
    }

    /**
     * 数据来自MeizituService页面的RxBus发送
     */
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
    public void onLongTouchPreview(String url) {
        TopBigPicDialogFragment.newInstance(url)
                .show(getSupportFragmentManager(), "dialog_meizitu_girl");
    }

    /**
     * 这个方法不会使用，因为数据是在后台Service中通过RxBus直接不停的发过来的，
     * 因此不需要显示 加载更多的状态
     */
    @Override
    protected void loadMoreData() {


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MeizituService.stop(MeizituRecyclerActivity.this);
        unbinder.unbind();
        mPresenter.unsubscribe();
    }
}
