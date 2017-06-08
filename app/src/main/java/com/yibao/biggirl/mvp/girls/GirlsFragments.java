package com.yibao.biggirl.mvp.girls;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseFag;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
public class GirlsFragments
        extends BaseFag<String>
        implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener
{

    @BindView(R.id.fag_content)
    LinearLayout       mFagContent;
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


    @Override
    public void init() {
        mPresenter = new GirlsPresenter(this);
        mPresenter.start(Constants.FRAGMENT_GIRLS);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        initData();
    }

    @Override
    protected RecyclerView.Adapter<RecyclerView.ViewHolder> getAdapter() {
        return new GirlsAdapter(getActivity(), mList);
    }


    private void initData() {
        mFab.setOnClickListener(this);
        mSwipeRefresh.setOnRefreshListener(this);
        mFagContent.addView(mRecyclerView);

    }

    @Override
    public void loadData(List<String> list) {
        mList.addAll(list);
        //        initRecyclerView(mList, 2, Constants.FRAGMENT_GIRLS);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    protected void laodMoreData() {
        mPresenter.loadData(size, page, Constants.LOAD_MORE_DATA, Constants.FRAGMENT_GIRLS);
    }

    @Override
    protected int getType() {
        return 2;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.girls_frag;
    }


    //下拉刷新
    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(size,
                                          page,
                                          Constants.REFRESH_DATA,
                                          Constants.FRAGMENT_GIRLS);
                      mSwipeRefresh.setRefreshing(false);
                      isShowGankGirl = false;
                      page = 1;
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

    public GirlsFragments newInstance() {
        return new GirlsFragments();
    }

    //切换干货和默认妹子
    @Override
    public void onClick(View view) {
        if (isShowGankGirl) {
            mFagContent.removeAllViews();
//                        initData(mList, 2, Constants.FRAGMENT_GIRLS);
            mSwipeRefresh.setRefreshing(false);
            isShowGankGirl = false;


        } else {
            getDefultGirl();
            isShowGankGirl = true;
        }

    }

    private void getDefultGirl() {
        mFagContent.removeAllViews();
        List<String> defultUrl = ImageUitl.getDefultUrl(new ArrayList<>());
        Random       random    = new Random();

//        initRecyclerView(defultUrl, random.nextInt(4) + 1, Constants.FRAGMENT_GIRLS);
        mSwipeRefresh.setRefreshing(false);
    }


}
