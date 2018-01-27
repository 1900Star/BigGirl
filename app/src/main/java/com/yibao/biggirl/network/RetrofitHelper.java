package com.yibao.biggirl.network;


import com.yibao.biggirl.MyApplication;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Sid
 * Des：${Retrofit 帮助类}
 * Time:2017/4/10 17:22
 */
public class RetrofitHelper {

    private static Retrofit retrofit;

    public static GirlService getGankApi(String baseUrl) {
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                                                 .addConverterFactory(GsonConverterFactory.create())
                                                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                 .client(MyApplication.defaultOkHttpClient())
                                                 .build();

            }

        }

        return retrofit.create(GirlService.class);


    }



}
