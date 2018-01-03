package com.yibao.biggirl.model.app;

import com.yibao.biggirl.network.RetrofitHelper;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/26 00:52
 */
public class RemoteAppData
        implements AppDataSource {

    @Override
    public void getApp(int size, int page, String type, LoadDataCallback callback) {
        RetrofitHelper.getGankApi(Constants.GANK_API)
                .getConmmetApi(type, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankDesBean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull GankDesBean gankDesBean) {
                        List<ResultsBeanX> list = gankDesBean.getResults();
                        callback.onLoadData(gankDesBean.getResults());
                        for (int i = 0; i < list.size(); i++) {
                            ResultsBeanX beanX = list.get(i);
                            LogUtil.d(" android type " + beanX.getType());
                            LogUtil.d(" android  url " + beanX.getUrl());
                        }


                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

}
