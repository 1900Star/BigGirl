package com.yibao.biggirl.network;

import com.yibao.biggirl.model.android.AndroidDesBean;
import com.yibao.biggirl.model.girls.GirlsBean;
import com.yibao.biggirl.model.unsplash.Unsplash;
import com.yibao.biggirl.model.video.VideoBean;

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
    Observable<GirlsBean> getGril(@Path("type") String type,
                                  @Path("count") int count,
                                  @Path("page") int page);

    //Android
    @GET("api/data/{type}/{count}/{page}")
    Observable<AndroidDesBean> getAndroid(@Path("type") String type,
                                          @Path("count") int count,
                                          @Path("page") int page);

    //Video
    @GET("api/data/{type}/{count}/{page}")
    Observable<VideoBean> getVideo(@Path("type") String type,
                                   @Path("count") int count,
                                   @Path("page") int page);

    //Unsplash
    @GET
    Observable<Unsplash> getUnsplash();


}
