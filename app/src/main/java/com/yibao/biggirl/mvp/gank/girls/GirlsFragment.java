package com.yibao.biggirl.mvp.gank.girls;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.yibao.biggirl.base.BaseRecyclerFag;
import com.yibao.biggirl.model.dagger2.component.DaggerGirlsComponent;
import com.yibao.biggirl.model.dagger2.moduls.GirlsModuls;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;


/**
 * 作者：Stran on 2017/3/29 01:18
 * 描述：${主列表}
 * 邮箱：strangermy@outlook.com
 */
public class GirlsFragment
        extends BaseRecyclerFag<String>
        implements
        View.OnLongClickListener,
        GirlsContract.View<String> {

    private GirlsAdapter mAdapter;

    private boolean isShowGankGirl;


    //     1 指定注入目标
    @Inject
    GirlsPresenter mGirlsPresenter;

    private int mRandomNum;

    public GirlsFragment() {
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        DaggerGirlsComponent.builder()
                .girlsModuls(new GirlsModuls(this))
                .build()
                .in(this);

        mGirlsPresenter.start(Constants.FRAGMENT_GIRLS, Constants.TYPE_GIRLS);

    }


    private void initData(int type) {
        switch (type) {
            case 0:
                if (isShowGankGirl) {
                    mFagContent.removeAllViews();
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
        mAdapter = new GirlsAdapter(mActivity, mList);
        RecyclerView recyclerView = getRecyclerView(mFab, type, mAdapter);
        mFagContent.addView(recyclerView);
        mFab.setOnClickListener(view -> initData(0));
        mFab.setOnLongClickListener(this);
    }


    //    下拉刷新
    @Override
    protected void refreshData() {
        mGirlsPresenter.loadData(size,
                1, 3,
                Constants.REFRESH_DATA,
                Constants.FRAGMENT_GIRLS);
        mSwipeRefresh.setRefreshing(false);
        isShowGankGirl = false;
    }


    //    刷新回调
    @Override
    public void refresh(List<String> list) {
        mList.clear();
        mAdapter.clear();
        mList.addAll(list);
        mAdapter.AddHeader(list);
        mAdapter.notifyDataSetChanged();
    }

    //    RecyclerView上拉加载更多
    @Override
    protected void loadMoreData() {
        mGirlsPresenter.loadData(size,
                page, 3,
                Constants.LOAD_MORE_DATA,
                Constants.FRAGMENT_GIRLS);
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


    //切换干货和默认妹子

    private void getDefultGirl(int type) {
        mFagContent.removeAllViews();
        List<String> defultUrl;
        if (type == 1) {
            defultUrl = ImageUitl.getSexUrl(new ArrayList<>());
        } else {
            defultUrl = ImageUitl.getNormalUrl(new ArrayList<>());

        }
        initRecyclerView(defultUrl, mRandomNum);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void setPrenter(GirlsContract.Presenter prenter) {
    }


    @Override
    public boolean onLongClick(View view) {
        initData(1);
        return true;
    }

    @Override
    public void showError() {
    }

    @Override
    public void showNormal() {

    }


}
