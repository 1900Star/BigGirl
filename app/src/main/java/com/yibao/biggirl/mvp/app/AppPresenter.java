package com.yibao.biggirl.mvp.app;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.android.AndroidDesBean;
import com.yibao.biggirl.model.app.AppDataSource;
import com.yibao.biggirl.model.app.RemoteAppData;
import com.yibao.biggirl.model.girl.TestData;
import com.yibao.biggirl.model.girls.GirlsBean;
import com.yibao.biggirl.network.RetrofitHelper;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 10:03
 */
public class AppPresenter
        implements AppContract.Presenter
{
    private        AppContract.View mView;
    private static RemoteAppData    mRemoteAppData;

    public AppPresenter(AppContract.View view) {
        this.mView = view;
        mRemoteAppData = new RemoteAppData();
        mView.setPrenter(this);
    }


    @Override
    public void start(String type) {
        loadData(20, 1, type, Constants.LOAD_DATA);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {

    }


    @Override
    public void loadData(int size, int page, String type, final int status) {
        mRemoteAppData.getApp(size, page, type, new AppDataSource.LoadADataCallback() {
            @Override
            public void onLoadData(List<AndroidAndGirl> list) {
                if (status == Constants.LOAD_DATA) {
                    mView.loadData(list);
                } else if (status == Constants.REFRESH_DATA) {
                    mView.refresh(list);
                } else if (status == Constants.LOAD_MORE_DATA) {
                    mView.loadMore(list);
                }

                //                mView.showNormal();
            }

            @Override
            public void onDataNotAvailable() {
                mView.showError();
            }
        });


    }


    //*******************************************************8测试加载数据
    public static List<AndroidAndGirl> getDatas(int size, int page, String type) {
        List<AndroidAndGirl> list = new ArrayList<>();
        Observable.zip(RetrofitHelper.getGankApi()
                                     .getAndroid(type, size, page),
                       RetrofitHelper.getGankApi()
                                     .getGril("福利", size, page),
                       AppPresenter::zipHelper)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new AndroidObserver(list));

        return list;
    }


    private static class AndroidObserver
            implements Observer<AndroidAndGirl>
    {

        List<AndroidAndGirl> mList;

        private AndroidObserver(List<AndroidAndGirl> list) {
            mList = list;
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(AndroidAndGirl bean) {

            for (int i = 0; i < bean.mGirlData.size(); i++) {
                AndroidAndGirl itemData = new AndroidAndGirl(bean.mAndroidData, bean.mGirlData);
                mList.add(itemData);
            }
            LogUtil.d("XXXXXXXXXXXXXXXXXXXXXX  == " + mList.size());
            MyApplication.getIntstance()
                         .bus()
                         .post(new TestData(mList));
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private static AndroidAndGirl zipHelper(AndroidDesBean androidDesBean, GirlsBean girlBean) {
        AndroidAndGirl item = new AndroidAndGirl();
        for (int i = 0; i < girlBean.getResults()
                                    .size(); i++) {

            item.mAndroidData = androidDesBean.getResults();
            item.mGirlData = girlBean.getResults();

        }


        return item;
    }


}
