package com.yibao.biggirl.model.android;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/25 22:59
 */
public interface AndroidDataSource {
    //这个接口用于将数据回调给对应Presenter层
    interface LoadADataCallback {
        void onLoadData(List<AndroidAndGirl> list);

        void onDataNotAvailable();

    }

    void getAndroid(int page, int size, LoadADataCallback callback);

}
