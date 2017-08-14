package com.yibao.biggirl.model.app;

import com.yibao.biggirl.model.android.AndroidDesBean;
import com.yibao.biggirl.network.RetrofitHelper;

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
        implements AppDataSource
{

    @Override
    public void getApp(int size, int page, String type, LoadADataCallback callback) {
        RetrofitHelper.getGankApi()
                      .getConmmetApi(type, size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<AndroidDesBean>() {
                          @Override
                          public void onSubscribe(@NonNull Disposable d) {

                          }

                          @Override
                          public void onNext(@NonNull AndroidDesBean androidDesBean) {
                              callback.onLoadData(androidDesBean.getResults());
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
