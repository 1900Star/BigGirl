package com.yibao.biggirl.model.favorite;

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

        void updataAllFavorite(List<FavoriteBean> list);

    }

    interface QueryAllFavoriteCallBack {

        void queryAllFavorite(List<FavoriteBean> list);

    }


    interface QueryConditionalCallBack {

        void conditionalQuery(List<FavoriteBean> list);

    }

    void insertFavorite(FavoriteBean bean, InsertFavoriteCallBack callBack);


    void cancelFavorite(Long id, CancelFavoriteCallBack callBack);

    void quetyConditional(String gankId, QueryConditionalCallBack callBack);

    void queryAllFavorite(QueryAllFavoriteCallBack callBack);

    void updataFavorite(FavoriteBean bean, UpdataFavoriteCallBack callBack);

}
