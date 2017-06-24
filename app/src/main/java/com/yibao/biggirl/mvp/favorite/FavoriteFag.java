package com.yibao.biggirl.mvp.favorite;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnDeleteItemClickListener;
import com.yibao.biggirl.factory.RecyclerViewFactory;
import com.yibao.biggirl.model.dagger2.component.DaggerFavoriteComponent;
import com.yibao.biggirl.model.dagger2.moduls.FavoriteModuls;
import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.mvp.webview.WebPresenter;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.view.SwipeItemLayout;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/17 07:56
 */
public class FavoriteFag
        extends Fragment
        implements SwipeRefreshLayout.OnRefreshListener,
                   FavoriteContract.View,
                   OnDeleteItemClickListener
{
    private static final String TAG = "FavoriteFag";
    @BindView(R.id.fag_content)
    LinearLayout       mFagContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;
    @Inject
    WebPresenter mWebPresenter;


    private List<FavoriteBean> mList;
    private FavoriteAdapter    mAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerFavoriteComponent component = (DaggerFavoriteComponent) DaggerFavoriteComponent.builder()
                                                                                             .favoriteModuls(
                                                                                                     new FavoriteModuls(
                                                                                                             this))
                                                                                             .build();
        component.in(this);
        mWebPresenter.queryAllFavorite();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.girls_frag, null);
        unbinder = ButterKnife.bind(this, view);
        initData();
        return view;
    }


    private void initData() {
        mList = new LinkedList<>();
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setOnRefreshListener(this);


    }

    @Override
    public void onResume() {
        super.onResume();
        mWebPresenter.queryAllFavorite();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onRefresh() {
        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mWebPresenter.queryAllFavorite();
                      mAdapter.notifyDataSetChanged();
                  });

    }


    @Override
    public void insertStatus(Long tatus) {

    }

    @Override
    public void cancelStatus(Long id) {
        LogUtil.d(TAG + "Adapter  取消收藏  :  " + id);
        if (id > 0) {
            //            mAdapter.notifyDataSetChanged();

        }
    }

    @Override
    public void queryAllFavorite(List<FavoriteBean> list) {
        mList.clear();
        mList.addAll(list);
        mSwipeRefresh.setRefreshing(false);
        mAdapter = new FavoriteAdapter(getActivity(), mList);
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(1, mAdapter, null);
        recyclerView.addOnItemTouchListener(new SwipeItemLayout.OnSwipeItemTouchListener(getActivity()));

        mFagContent.addView(recyclerView);
    }

    @Override
    public void queryFavoriteIsCollect(List<FavoriteBean> list) {

    }


    public FavoriteFag newInstance() {
        return new FavoriteFag();
    }

    @Override
    public void deleteFavorite(Long id) {
        LogUtil.d("这是准备删除   :   " + id);
        //        mWebPresenter.cancelFavorite(id, 0);
    }
}
