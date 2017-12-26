package com.yibao.biggirl.model.duotu;

import com.yibao.biggirl.model.girl.Girl;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/25 22:59
 */
public interface DuotuDataSource {


    //这个接口用于将数据回调给对应Presenter层
    interface LoadDuotuCallback {
        void onLoadDatas(List<Girl> urlList);

        void onDataNotAvailable();

    }


    void getDuotu(String type, int page, LoadDuotuCallback callback);
    void getDuotuList(String url,int page,LoadDuotuCallback callback);


}
