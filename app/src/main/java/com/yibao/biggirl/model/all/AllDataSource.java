package com.yibao.biggirl.model.all;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/25 22:59
 */
public interface AllDataSource {
    //这个接口用于将数据回调给对应Presenter层
    interface LoadADataCallback {
        void onLoadData(List<AllResultsBean> list);

        void onDataNotAvailable();

    }

    void getAll(int size, int page, LoadADataCallback callback);

}
