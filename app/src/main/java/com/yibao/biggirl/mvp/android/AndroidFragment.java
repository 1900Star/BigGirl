package com.yibao.biggirl.mvp.android;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.factory.RecyclerViewFactory;
import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.dagger2.component.DaggerAndroidComponent;
import com.yibao.biggirl.model.dagger2.moduls.AndroidModuls;
import com.yibao.biggirl.util.Constants;

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
 * Time:2017/4/23 06:33
 */
public class AndroidFragment
        extends Fragment
        implements AndroidContract.View, SwipeRefreshLayout.OnRefreshListener
{
    AndroidContract.Presenter mPresenters;
    @BindView(R.id.fag_content)
    LinearLayout       mFagContent;
    @BindView(R.id.swipe_refresh)
    SwipeRefreshLayout mSwipeRefresh;
    Unbinder unbinder;

    private AndroidAdapter mAdapter;

    private int page = 1;
    private int size = 20;
    private FloatingActionButton mFab;
    private LinearLayoutManager  mManager;
    private int                  totalItemCount;
    private int                  lastVisibleItem;
    private       boolean loading           = false;
    private final int     VISIBLE_THRESHOLD = 1;

    @Inject
    AndroidPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DaggerAndroidComponent component = (DaggerAndroidComponent) DaggerAndroidComponent.builder()
                                                                                          .androidModuls(
                                                                                                  new AndroidModuls(
                                                                                                          this))
                                                                                          .build();
        component.in(this);
        mPresenter.start(Constants.FRAGMENT_ANDROID);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        View view = View.inflate(getActivity(), R.layout.girls_frag, null);

        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {

        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);
        mSwipeRefresh.setColorSchemeColors(Color.BLUE, Color.RED, Color.YELLOW);
        mSwipeRefresh.setRefreshing(true);
        mSwipeRefresh.setOnRefreshListener(this);
    }


    private void initData(List<AndroidAndGirl> list, int type, String dataType) {


        mAdapter = new AndroidAdapter(getContext(), list);
        RecyclerView recyclerView = RecyclerViewFactory.creatRecyclerView(type, mAdapter);
        mFagContent.addView(recyclerView);

    }

    @Override
    public void loadData(List<AndroidAndGirl> list) {

        initData(list, 1, Constants.FRAGMENT_ANDROID);
        mSwipeRefresh.setRefreshing(false);
    }

    @Override
    public void onRefresh() {

        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(size, page, Constants.REFRESH_DATA);
                      mSwipeRefresh.setRefreshing(false);
                      page = 1;
                  });
    }

    @Override
    public void refresh(List<AndroidAndGirl> list) {

    }

    @Override
    public void loadMore(List<AndroidAndGirl> list) {

    }


    @Override
    public void showError() {

    }

    @Override
    public void showNormal() {

    }

    @Override
    public void setPrenter(AndroidContract.Presenter prenter) {
        this.mPresenters = prenter;
    }

    public AndroidFragment newInstance() {

        return new AndroidFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();


    }
}

