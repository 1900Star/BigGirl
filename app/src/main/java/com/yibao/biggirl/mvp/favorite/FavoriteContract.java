package com.yibao.biggirl.mvp.favorite;

import com.yibao.biggirl.model.favorite.FavoriteBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/17 17:49
 */
public interface FavoriteContract {
     interface View
    {

        void insertStatus(Long status);

        void cancelStatus(Long id);

        void queryAllFavorite(List<FavoriteBean> list);
        void queryFavoriteIsCollect(List<FavoriteBean> list);

    }

}
