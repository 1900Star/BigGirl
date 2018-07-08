package com.yibao.biggirl.base;

import com.yibao.biggirl.model.app.ResultsBeanX;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @ Author: Luoshipeng
 * @ Name:   BaseObserver
 * @ Email:  strangermy98@gmail.com
 * @ Time:   2018/5/26/ 13:33
 * @ Des:    //TODO
 */
public abstract class BaseObserver implements Observer<List<ResultsBeanX>> {
    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(List<ResultsBeanX> list) {
        onSuccess(list);
    }

     protected abstract void onSuccess(List<ResultsBeanX> list);


    @Override
    public void onError(Throwable e) {
        onfailde(e);
    }

    protected abstract void onfailde(Throwable e);


    @Override
    public void onComplete() {

    }

}
