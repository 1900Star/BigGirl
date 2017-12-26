package com.yibao.biggirl.util;

import android.text.TextUtils;

import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.model.girl.Girl;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${将数据转换成其它类型，放到集合中并返回}
 * Time:2017/6/18 22:44
 */
public class PackagingDataUtil {

    private static String imageUrl;

    public static List<String> objectToList(List<Girl> list) {

        List<String> data = new ArrayList();
        Flowable.fromIterable(list)
                .subscribeOn(Schedulers.io())
                .subscribe(girl -> {
                    if (TextUtils.isEmpty(girl.getLink())) {
                        data.add(girl.getUrl());
                    } else {
                        data.add(girl.getLink());
                    }
                });

        return data;
    }

    public static FavoriteWebBean objectToFavorite(ResultsBeanX data) {


        List<String> images = data.getImages();
        if (images != null && images.size() > 0) {
            imageUrl = images.get(0);
        }
        String who = data.getWho();
        String name = who == null
                ? "Smartisan"
                : who;
        String time = FileUtil.getCreatTime(data.getCreatedAt());
        FavoriteWebBean bean = new FavoriteWebBean((long) 0,
                data.getUrl(),
                data.get_id(),
                imageUrl,
                data.getDesc(),
                name,
                data.getType(),
                time);

        return bean;


    }

}
