package com.yibao.biggirl.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yibao.biggirl.util.LogUtil;


/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.service
 * @文件名: TestService
 * @author: Stran
 * @创建时间: 2018/1/6 23:02
 * @描述： TODO
 */

public class TestService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {

        return null;
    }

    @Override
    public void onCreate() {
        LogUtil.d(getClass().getSimpleName(),"  onCreate");
        super.onCreate();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(getClass().getSimpleName(),"  onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(getClass().getSimpleName(),"  onDestroy");

    }
}
