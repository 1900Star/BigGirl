package com.yibao.biggirl;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.yibao.biggirl.util.RxBus;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Stran on 2017/3/23 15:12
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class MyApplication
        extends Application
{
    private static MyApplication appContext;
//    public static String currentGirl = "http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-23-17265820_645330569008169_4543676027339014144_n.jpg";
    private RxBus mRxBus;

    public static MyApplication getIntstance() {
        if (appContext == null) {
            appContext = new MyApplication();
        }
        return appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
        appContext = this;
        mRxBus = new RxBus();


    }

    public RxBus bus() {
        return mRxBus;
    }


    public static OkHttpClient defaultOkHttpClient() {
        return new OkHttpClient.Builder().addInterceptor(chain -> {
            Request request = chain.request();
            //                LogUtil.d("request ====     "+request.toString());
            Response proceed = chain.proceed(request);
            //                LogUtil.d("proced=====      "+proceed);
            return proceed;
        })
                                         .connectTimeout(3, TimeUnit.SECONDS)
                                         .writeTimeout(3, TimeUnit.SECONDS)
                                         .readTimeout(3, TimeUnit.SECONDS)
                                         .build();
    }


}
