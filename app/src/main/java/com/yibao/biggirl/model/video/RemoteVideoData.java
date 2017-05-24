package com.yibao.biggirl.model.video;

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


    public static void getVideos(int size, int page) {
        RetrofitHelper.getGankApi()
                      .getVideo("休息视频", size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<VideoBean>() {
                          @Override
                          public void onSubscribe(Disposable d) {}

                          @Override
                          public void onNext(VideoBean videoBean) {

                          }

                          @Override
                          public void onError(Throwable e) {

                          }

                          @Override
                          public void onComplete() {}
                      });
    }

    @Override
    public void getVideo(int size, int page, LoadVDataCallback callback) {
        RetrofitHelper.getGankApi()
                      .getVideo("休息视频", size, page)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(new Observer<VideoBean>() {
                          @Override
                          public void onSubscribe(Disposable d) {}

                          @Override
                          public void onNext(VideoBean videoBean) {
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
