package com.yibao.biggirl.model.girls;

import com.yibao.biggirl.network.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/26 00:52
 */
public class RemoteGirlsData
        implements GrilsDataSource
{

    @Override
    public void getGirls(int size, int page, LoadGDataCallback callback) {

        RetrofitHelper.getGankApi()
                      .getGril("福利", size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<GirlsBean>() {
                          @Override
                          public void onSubscribe(Disposable d) {}

                          @Override
                          public void onNext(GirlsBean girlsBean) {
                              callback.onLoadDatas(girlsBean);
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
