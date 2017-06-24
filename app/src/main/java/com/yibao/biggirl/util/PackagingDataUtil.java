package com.yibao.biggirl.util;

import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.model.favorite.FavoriteBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/18 22:44
 */
public class PackagingDataUtil {

    private static String imageUrl;

    public static FavoriteBean objectToFavorite(ResultsBeanX data) {


        List<String> images = data.getImages();
        if (images != null && images.size() > 0) {
            imageUrl = images.get(0);
        }
        String who = data.getWho();
        String name = who == null
                      ? "Smartisan"
                      : who;
//        String str  = data.getCreatedAt();
//        long   id   = FileUtil.getId(str);
        String time = FileUtil.getCreatTime(data.getCreatedAt());
        FavoriteBean bean = new FavoriteBean((long) 0,
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
