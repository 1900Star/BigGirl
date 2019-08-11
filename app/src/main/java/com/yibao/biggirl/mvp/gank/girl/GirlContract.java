package com.yibao.biggirl.mvp.gank.girl;

import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/17 17:49
 */
public interface GirlContract {
     interface View
    {

        void insertStatus(Long status);

        void cancelStatus(Long id);
        void queryAllFavorite(List<FavoriteWebBean> list);
        void queryFavoriteIsCollect(List<FavoriteWebBean> list);


    }

}
