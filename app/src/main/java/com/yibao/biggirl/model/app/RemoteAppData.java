package com.yibao.biggirl.model.app;

import com.yibao.biggirl.network.RetrofitHelper;
import com.yibao.biggirl.util.Constants;

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
                              callback.onLoadData(gankDesBean.getResults());
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
