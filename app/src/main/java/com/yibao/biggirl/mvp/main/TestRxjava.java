package com.yibao.biggirl.mvp.main;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.mvp.main
 * @文件名: TestRxjava
 * @author: Stran
 * @Email: www.strangermy@outlook.com / www.strangermy98@gmail.com
 * @创建时间: 2018/3/19 16:35
 * @描述： {TODO}
 */

public class TestRxjava {
    public static void getMeiziLis() {
        Observable.just(5).subscribeOn(Schedulers.io()).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                return integer + "个数据";
            }
        })
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                System.out.println("这是第 收到数据    " + s);

            }
        });

    }
}
