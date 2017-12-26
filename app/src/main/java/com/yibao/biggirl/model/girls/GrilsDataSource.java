package com.yibao.biggirl.model.girls;

import com.yibao.biggirl.model.girl.Girl;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/25 22:59
 */
public interface GrilsDataSource {
    //这个接口用于将数据回调给对应Presenter层
    interface LoadGDataCallback {
        //        void onLoadDatas(GirlsBean girlBean);
        void onLoadDatas(List<String> urlList);

        void onDataNotAvailable();

    }

    //这个接口用于将数据回调给对应Presenter层
    interface LoadGMeizituCallback {
        //        void onLoadDatas(GirlsBean girlBean);
        void onLoadDatas(List<Girl> urlList);

        void onDataNotAvailable();

    }

    void getGirls(String dataType, int size, int page, LoadGDataCallback callback);

    void getMeizitu(String type, int page, LoadGMeizituCallback callback);

    void getMeiziList(String url);

}
