package com.yibao.biggirl.network;

import com.yibao.biggirl.model.app.GankDesBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/5 20:57
 */
public interface GirlService {

    //代码集中营Api 《 www.Gank.io》 福利
    @GET("api/data/{type}/{count}/{page}")
    Observable<GankDesBean> getConmmetApi(@Path("type") String type,
                                          @Path("count") int count,
                                          @Path("page") int page);


}
