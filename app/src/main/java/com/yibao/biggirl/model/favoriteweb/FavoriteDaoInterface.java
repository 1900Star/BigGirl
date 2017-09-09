package com.yibao.biggirl.model.favoriteweb;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/17 02:53
 */
public interface FavoriteDaoInterface {

    interface InsertFavoriteCallBack {
        void insertStatus(Long insertStatus);

    }

    interface CancelFavoriteCallBack {

        void cancelFavorite(Long id);
    }

    interface UpdataFavoriteCallBack {

        void updataAllFavorite(List<FavoriteWebBean> list);

    }

    interface QueryAllFavoriteCallBack {

        void queryAllFavorite(List<FavoriteWebBean> list);

    }


    interface QueryConditionalCallBack {

        void conditionalQuery(List<FavoriteWebBean> list);

    }

    void insertFavorite(FavoriteWebBean bean, InsertFavoriteCallBack callBack);

    void cancelFavorite(Long id, CancelFavoriteCallBack callBack);

    void quetyConditional(String gankId, QueryConditionalCallBack callBack);

    void queryAllFavorite(QueryAllFavoriteCallBack callBack);

    void updataFavorite(FavoriteWebBean bean, UpdataFavoriteCallBack callBack);

}
