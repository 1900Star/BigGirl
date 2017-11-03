package com.yibao.biggirl.model.girls;

import com.yibao.biggirl.network.RetrofitHelper;

import java.util.ArrayList;
import java.util.List;

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
    public void getGirls(String dataType, int size, int page, LoadGDataCallback callback) {
        List<String> urlList = new ArrayList<>();
        RetrofitHelper.getGankApi()
                      .getGril(dataType, size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<GirlsBean>() {
                          @Override
                          public void onSubscribe(Disposable d) {}

                          @Override
                          public void onNext(GirlsBean girlsBean) {
                              List<ResultsBean> results = girlsBean.getResults();


                              for (int i = 0; i < results.size(); i++) {
                                  urlList.add(results.get(i)
                                                     .getUrl());
                              }
                              callback.onLoadDatas(urlList);
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
