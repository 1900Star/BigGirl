package com.yibao.biggirl.model.sisanjiujiu;

import com.yibao.biggirl.model.girl.Girl;

import java.util.List;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.model.sisanjiujiu
 * @文件名: SisanDataSource
 * @author: Stran
 * @创建时间: 2018/1/10 15:46
 * @描述： TODO
 */

public interface SisanDataSource {
    //这个接口用于将数据回调给对应Presenter层
    interface LoadSisanCallback {
        void onLoadDatas(List<Girl> urlList);

        void onDataNotAvailable();

    }


    void getSisan(String type, int page, SisanDataSource.LoadSisanCallback callback);
    void getSisanSingle(String url,SisanDataSource.LoadSisanCallback callback);
}
