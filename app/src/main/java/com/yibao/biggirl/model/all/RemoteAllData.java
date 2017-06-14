package com.yibao.biggirl.model.all;

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
public class RemoteAllData
        implements AllDataSource
{


    @Override
    public void getAll(int size, int page, LoadADataCallback callback) {
        RetrofitHelper.getGankApi()
                      .getAll("all", size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<AllBean>() {
                          @Override
                          public void onSubscribe(@NonNull Disposable d) {

                          }

                          @Override
                          public void onNext(@NonNull AllBean allBean) {
                              callback.onLoadData(allBean.getResults());
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
