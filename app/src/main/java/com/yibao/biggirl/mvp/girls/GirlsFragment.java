package com.yibao.biggirl.mvp.girls;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.yibao.biggirl.R;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;
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
        implements GirlsContract.View, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener
{

    @BindView(R.id.fragment_girl_recycler)
    RecyclerView       mRecyclerView;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    private GirlsContract.Presenter mPresenter;
    private GirlsAdapter            mAdapter;
    private List<String>            mList;
    private int page = 1;
    private int size = 20;
    private FloatingActionButton mFab;
    private boolean              isShowGankGirl;
    public static final int IDLE = 0;

    public static final int DRAGGING = 1;


    public static final int SETTLING = 2;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new GirlsPresenter(this);
        mPresenter.start(Constants.FRAGMENT_GIRLS);

    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = View.inflate(getActivity(), R.layout.girls_frag, null);


        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }

    private void initView() {
        mList = new ArrayList<>();
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);

    }


    private void initRecyclerView(List<String> mList) {

        mSwipeRefresh.setOnRefreshListener(this);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        mAdapter = new GirlsAdapter(getActivity(), mList);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,
                                                                            StaggeredGridLayoutManager.VERTICAL);
        manager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(mAdapter);


    }


    @Override
    public void loadData(List<String> list) {
        mList.clear();
        mList.addAll(list);
        initRecyclerView(mList);
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
        mAdapter.AddHeader(mList);
    }

    @Override
    public void loadMore(List<String> list) {
        //        LogUtil.d("============ Loade More ===========  ");
        if (list.size() % 20 == 0) {
            page++;
            //            mPresenter.loadData(size, page, Constants.LOAD_DATA);
        }
        mList.addAll(list);
        mAdapter.AddFooter(mList);

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
        mFab.setOnClickListener(this);
        mSwipeRefresh.setColorSchemeColors(Color.RED, Color.GREEN, Color.YELLOW);
        mSwipeRefresh.setOnRefreshListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {


            private int[] mBottom;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastCount = mAdapter.getItemCount() - 1;
                if (newState == IDLE && mBottom[0] == lastCount || mBottom[1] == lastCount) {
                    boolean isRefresh = mSwipeRefresh.isRefreshing();
                    if (isRefresh) {
                        mAdapter.notifyItemRemoved(mAdapter.getItemCount());
                    } else {
                        mAdapter.changeMoreStatus(Constants.LOADING_DATA);
                        mPresenter.loadData(size, page, Constants.PULLUP_LOAD_MORE_DATA);

                    }

                } else if (newState == DRAGGING | newState == SETTLING) {
                    mFab.setVisibility(View.INVISIBLE);

                } else {
                    mFab.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //                LogUtil.d("====  RecyclerView   ==" + recyclerView.getChildCount());
                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();

                mBottom = manager.findFirstCompletelyVisibleItemPositions(new int[2]);
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


    @Override
    public void onClick(View view) {

        if (isShowGankGirl) {
            initRecyclerView(mList);
            mSwipeRefresh.setRefreshing(false);
            isShowGankGirl = false;

        } else {
            getDefultGirl();
            isShowGankGirl = true;
        }

    }

    private void getDefultGirl() {
        List<String> defultUrl = ImageUitl.getDefultUrl(ImageUitl.getDefultUrl(new ArrayList<>()));
        initRecyclerView(defultUrl);
        mSwipeRefresh.setRefreshing(false);
        //        mAdapter.notifyDataSetChanged();
    }
}
