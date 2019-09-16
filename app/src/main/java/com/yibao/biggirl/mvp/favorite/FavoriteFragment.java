package com.yibao.biggirl.mvp.favorite;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.yibao.biggirl.base.BaseRecyclerFragment;
import com.yibao.biggirl.base.listener.OnRvItemSlideListener;
import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.mvp.webview.WebPresenter;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.view.SwipeItemLayout;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * @ Author: Luoshipeng
 * @ Name:   FavoriteFragment
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/7/28/ 21:38
 * @ Des:    TODO
 */
public class FavoriteFragment extends BaseRecyclerFragment implements FavoriteContract.View, OnRvItemSlideListener {

    private WebPresenter mWebPresenter;
    private FavoriteAdapter mAdapter;

    public static FavoriteFragment newInstance(int pageType) {
        FavoriteFragment fragment = new FavoriteFragment();
        Bundle args = new Bundle();
        args.putInt("pageType", pageType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebPresenter = new WebPresenter(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebPresenter.queryAllFavorite();
    }

    @Override
    protected void refreshData() {
        mDisposable.add(Observable.timer(2, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    mWebPresenter.queryAllFavorite();
                    mAdapter.notifyDataSetChanged();
                }));
    }


    @Override
    protected void processLogic(Bundle savedInstanceState) {
        mAdapter = new FavoriteAdapter(getActivity(), null);
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(1, mAdapter);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getActivity()));
        mAdapter.notifyDataSetChanged();
        mAdapter.changeLoadMoreStatus(Constants.NOT_MORE_DATA_RV);
        mFagContent.addView(recyclerView);
    }

    @Override
    public void insertStatus(Long status) {

    }

    @Override
    public void cancelStatus(Long id) {

    }

    @Override
    public void deleteFavorite(Long id) {
        LogUtil.d("要删除的 ID :" + id);
        mWebPresenter.cancelFavorite(id, 0);
    }

    @Override
    public void queryAllFavorite(List<FavoriteWebBean> list) {
        mAdapter.setNewData(list);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void queryFavoriteIsCollect(List<FavoriteWebBean> list) {

    }

    @Override
    protected void loadMoreData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mWebPresenter.destroyView();
    }
}
