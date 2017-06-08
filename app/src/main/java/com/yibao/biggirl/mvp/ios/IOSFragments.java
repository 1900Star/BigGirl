package com.yibao.biggirl.mvp.ios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseFragment;
import com.yibao.biggirl.base.LoadingPager;
import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.model.girls.ResultsBean;
import com.yibao.biggirl.mvp.app.AppAdapter;
import com.yibao.biggirl.mvp.app.AppContract;
import com.yibao.biggirl.mvp.app.AppPresenter;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/23 06:33
 */
public class IOSFragments
        extends BaseFragment
        implements SwipeRefreshLayout.OnRefreshListener
{
    AppPresenter mPresenter;

    private static List<AndroidAndGirl> mLists;
    private static AppAdapter           mAdapter;

    private int page = 1;
    private int size = 20;
    private FloatingActionButton mFab;

    private boolean isNetwork = NetworkUtil.isNetworkConnected(getActivity());
    private CompositeDisposable  disposables;
    private MyApplication        mApplication;
    private List<AndroidAndGirl> mDatas;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApplication = (MyApplication) getActivity().getApplication();
        disposables = new CompositeDisposable();
        mPresenter = new AppPresenter(this);
        initView();

        //                mPresenter.start(Constants.FRAGMENT_IOS);


    }


    //加载数据，同时判断数据的状态，根据不同的状态返回不同的视图
    @Override
    protected LoadingPager.LoadedResult initData() {
        mDatas = AppPresenter.getDatas(20, 1, Constants.FRAGMENT_IOS);

        if (!isNetwork) {


            //            disposables.add(mApplication.bus()
            //                                        .toObserverable(TestData.class)
            //                                        .subscribe(new Consumer<TestData>() {
            //                                            @Override
            //                                            public void accept(@NonNull TestData testData)
            //                                                    throws Exception
            //                                            {
            //                                                mLists.addAll(testData.getList());
            //
            //                                            }
            //                                        }));

            LogUtil.d("BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB  == " + mDatas.size());

            AndroidAndGirl data = mDatas.get(0);

            LoadingPager.LoadedResult state = checkResResult(data);
            if (state != LoadingPager.LoadedResult.SUCCESS) {//出现问题,homeBean==null
                LogUtil.d("这是第一个数据状态==" + state);
                return state;
            }


            List<ResultsBeanX> androidData = data.getAndroidData();
            List<ResultsBean>  grilData    = data.getGrilData();

            state = checkResResult(androidData);
            if (state != LoadingPager.LoadedResult.SUCCESS) {//出现了问题,mItemInfoBeanList.size==0
                LogUtil.d("这是第二个数据状态==" + state);
                return state;
            }

            state = checkResResult(grilData);
            if (state != LoadingPager.LoadedResult.SUCCESS) {//出现了问题,mPictureUrls.size==0
                LogUtil.d("这是第三个数据状态==" + state);
                return state;
            }
            LogUtil.d("这是第四个数据状态==" + state);
            return state;//这个时候的state肯定就是success
        } else {
            return LoadingPager.LoadedResult.ERROR;
        }
    }


    @Override
    protected View initSuccessView() {
        LogUtil.d("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
        mAdapter = new AppAdapter(getActivity(), mDatas);
//        return RecyclerViewFactory.creatRecyclerView(1, mFab, mAdapter);

        return null;

    }

    @Override
    protected void startLoad() {

    }

    private void initView() {
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        mFab.setVisibility(View.VISIBLE);

    }

    //


    @Override
    public void loadData(List<AndroidAndGirl> list) {
        mLists = new ArrayList<>();
        mLists.clear();
        mLists.addAll(list);
        LogUtil.d("AAAAAAAAAAAAAAAAAAA" + mLists.size());


    }


    @Override
    public void onRefresh() {

        Observable.timer(1, TimeUnit.SECONDS)
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      mPresenter.loadData(size, 1, Constants.FRAGMENT_IOS, Constants.REFRESH_DATA);

                      page = 1;
                  });
    }


    @Override
    public void refresh(List<AndroidAndGirl> list) {

        mAdapter.clear();
        mAdapter.AddHeader(list);
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
    public void setPrenter(AppContract.Presenter prenter) {
        this.mPresenter = (AppPresenter) prenter;
    }

    public IOSFragments newInstance() {

        return new IOSFragments();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();


    }
}

