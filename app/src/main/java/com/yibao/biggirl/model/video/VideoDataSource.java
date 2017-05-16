package com.yibao.biggirl.model.video;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 07:48
 */
public interface VideoDataSource {
    //这个接口用于将数据回调给对应Presenter层
    interface LoadVDataCallback {
        void onLoadDatas(VideoBean videoBean);

        void onDataNotAvailable();

    }

    void getVideo(int size, int page, LoadVDataCallback callback);
}
