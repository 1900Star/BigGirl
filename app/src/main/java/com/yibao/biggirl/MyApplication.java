package com.yibao.biggirl;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;
import android.widget.TextView;

import com.squareup.leakcanary.LeakCanary;
import com.yibao.biggirl.model.greendao.DaoMaster;
import com.yibao.biggirl.model.greendao.DaoSession;
import com.yibao.biggirl.util.CrashHandler;
import com.yibao.biggirl.util.RxBus;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Stran on 2017/3/23 15:12
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class MyApplication
        extends Application {
    private static MyApplication appContext;
    public static boolean isShowLog = true;
    private static OkHttpClient okHttpClient;
    private RxBus mRxBus;

    private DaoSession mDaoSession;
    private TextView textView;

    public static MyApplication getIntstance() {
        if (appContext == null) {
            appContext = new MyApplication();
        }
        return appContext;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        TextView textView = new TextView(this);

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
        SQLiteDatabase db = mHelper.getWritableDatabase();
        DaoMaster mMaster = new DaoMaster(db);
        mDaoSession = mMaster.newSession();


    }

    public DaoSession getDaoSession() {
        return mDaoSession;
    }


    public RxBus bus() {
        return mRxBus;
    }


    public static OkHttpClient defaultOkHttpClient() {

        if (okHttpClient == null) {
            synchronized (MyApplication.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()
                            .connectTimeout(3, TimeUnit.SECONDS)
                            .writeTimeout(3, TimeUnit.SECONDS)
                            .readTimeout(3, TimeUnit.SECONDS)
                            .build();
                }
            }
        }
        return okHttpClient;

    }

}
