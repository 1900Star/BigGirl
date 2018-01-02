package com.yibao.biggirl.model.app;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/25 22:59
 */
public interface AppDataSource {
    //这个接口用于将数据回调给对应Presenter层

    interface LoadDataCallback {
        void onLoadData(List<ResultsBeanX> list);

        void onDataNotAvailable();

    }

    void getApp(int page, int size, String type, LoadDataCallback callback);

}
