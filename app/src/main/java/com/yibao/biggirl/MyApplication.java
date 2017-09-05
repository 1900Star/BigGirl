package com.yibao.biggirl;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

import com.squareup.leakcanary.LeakCanary;
import com.yibao.biggirl.model.greendao.DaoMaster;
import com.yibao.biggirl.model.greendao.DaoSession;
import com.yibao.biggirl.util.CrashHandler;
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
    public static boolean isShowLog = true;

    private RxBus mRxBus;

    private DaoSession mDaoSession;

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
        CrashHandler.getInstance()
                    .init(this);
        setUpDataBase();
        mRxBus = new RxBus();


    }

    private void setUpDataBase() {
        DaoMaster.DevOpenHelper mHelper = new DaoMaster.DevOpenHelper(this, "favorite-db", null);
        SQLiteDatabase          db      = mHelper.getWritableDatabase();
        DaoMaster               mMaster = new DaoMaster(db);
        mDaoSession = mMaster.newSession();


    }

    public DaoSession getDaoSession() {
        return mDaoSession;
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
