package com.yibao.biggirl.mvp.video;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.mvp.girls.GirlsContract;
import com.yibao.biggirl.mvp.girls.GirlsPresenter;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

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
public class VideoFragmnets
        extends BaseFag<ResultsBeanX>
        implements GirlsContract.View<ResultsBeanX>, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.fag_content)
    LinearLayout       mFagContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    private VideoAdapter mAdapter;
    private int page = 1;
    private int size = 20;
    //    private FloatingActionButton    mFab;
    private GirlsContract.Presenter mPresenter;
//    private RecyclerView            mRecyclerView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new GirlsPresenter(this);
        mPresenter.start(Constants.FRAGMENT_VIDEO,9);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }




    @Override
    protected GirlsContract.Presenter getPresenter() {
        return mPresenter;
    }

    @Override
    protected void loadMoreData() {
        page++;
        mPresenter.loadData(size, page,9, Constants.LOAD_MORE_DATA, Constants.FRAGMENT_VIDEO);
        LogUtil.d("AAAAAAAAAAAAAAAAAa");
    }


    @Override
    protected int getLayoutId() {
        return R.layout.girls_frag;
    }

    @Override
    protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        mAdapter = new VideoAdapter(getActivity(), mList);
        return mAdapter;
    }

    @Override
    protected int getType() {
        return 1;
    }


    private void initRecyclerView(List<ResultsBeanX> list, int type, String dataType) {
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setOnRefreshListener(this);

//        mRecyclerView = RecyclerViewFactory.creatRecyclerView(type, mAdapter);
        mFagContent.addView(mRecyclerView);
    }


    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(size,
                                          page,9,
                                          Constants.REFRESH_DATA,
                                          Constants.FRAGMENT_VIDEO);

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
        mList.addAll(list);
        mAdapter.AddFooter(mList);
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

    public VideoFragmnets newInstance() {
        return new VideoFragmnets();
    }

    @Override
    public void setPrenter(GirlsContract.Presenter prenter) {

    }


}
