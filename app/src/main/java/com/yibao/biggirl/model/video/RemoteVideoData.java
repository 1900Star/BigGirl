package com.yibao.biggirl.model.video;

import com.yibao.biggirl.model.android.AndroidDesBean;
import com.yibao.biggirl.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 07:33
 */
public class RemoteVideoData
        implements VideoDataSource
{


    @Override
    public void getVideo(int size, int page,String loadType,LoadVDataCallback callback) {
        RetrofitHelper.getGankApi()
                      .getConmmetApi(loadType, size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<AndroidDesBean>() {
                          @Override
                          public void onSubscribe(Disposable d) {}

                          @Override
                          public void onNext(AndroidDesBean videoBean) {
                              callback.onLoadDatas(videoBean);
                          }

                          @Override
                          public void onError(Throwable e) {
                              callback.onDataNotAvailable();
                          }

                          @Override
                          public void onComplete() {}
                      });
    }
}
