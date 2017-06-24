package com.yibao.biggirl.model.app;

import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.android.AndroidDesBean;
import com.yibao.biggirl.model.girls.GirlsBean;
import com.yibao.biggirl.network.RetrofitHelper;

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
 * Time:2017/4/26 00:52
 */
public class RemoteAppData
        implements AppDataSource
{

    @Override
    public void getApp(int size, int page, String type, LoadADataCallback callback) {
        Observable.zip(RetrofitHelper.getGankApi()
                                     .getAndroid(type, size, page),
                       RetrofitHelper.getGankApi()
                                     .getGril("福利", size, page),
                       this::zipHelper)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new AndroidObserver(callback));


    }


    private class AndroidObserver
            implements Observer<AndroidAndGirl>
    {


        private LoadADataCallback mCallback;

        private AndroidObserver(LoadADataCallback callback) {
            mCallback = callback;
        }

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(AndroidAndGirl bean) {
            List<AndroidAndGirl> list = new ArrayList<>();
            for (int i = 0; i < bean.mGirlData.size(); i++) {
                AndroidAndGirl itemData = new AndroidAndGirl(bean.mAndroidData, bean.mGirlData);
                list.add(itemData);
            }
            mCallback.onLoadData(list);
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

    private AndroidAndGirl zipHelper(AndroidDesBean androidDesBean, GirlsBean girlBean) {
        AndroidAndGirl item = new AndroidAndGirl();
        for (int i = 0; i < girlBean.getResults()
                                    .size(); i++) {

            item.mAndroidData = androidDesBean.getResults();
            item.mGirlData = girlBean.getResults();

        }


        return item;
    }

}
