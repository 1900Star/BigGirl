package com.yibao.biggirl.util;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/29 09:11
 */
public class ObservableUtil<T>

{
    public static void getObservable(){

        Observable.just(2)
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Object>() {
            @Override
            public void accept(@NonNull Object o)
                    throws Exception
            {

            }
        });

    }

}
