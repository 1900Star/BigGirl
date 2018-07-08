package com.yibao.biggirl.model.app;

import java.util.List;

import io.reactivex.Observable;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/25 22:59
 */
public interface AppDataSource {
    //这个接口用于将数据回调给对应Presenter层


    Observable<List<ResultsBeanX>> getAppData(int page, int size, String type);

}
