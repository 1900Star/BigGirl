package com.yibao.biggirl.mvp.webview;

import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.model.favorite.FavoriteDao;
import com.yibao.biggirl.mvp.favorite.FavoriteContract;
import com.yibao.biggirl.mvp.favorite.FavoriteFag;

/**
 * Author：Sid
 * Des：${  mView只负责查询所有、取消收藏，mWebActivity负责：新增收藏、取消收藏、查询是否已经收藏 }
 * Time:2017/6/17 03:05
 */
public class WebPresenter {
    private FavoriteDao           mDao;
    private WebActivity           mWebActivity;
    private FavoriteContract.View mFag;

    public WebPresenter(FavoriteFag fag) {

        mFag = fag;
        mDao = new FavoriteDao();
    }

    public WebPresenter(WebActivity activity) {
        mWebActivity = activity;
        mDao = new FavoriteDao();
    }

    public void insertFavorite(FavoriteBean favoriteBean) {
        mDao.insertFavorite(favoriteBean, insertStatus -> mWebActivity.insertStatus(insertStatus));
    }

    public void cancelFavorite(Long id, int type) {
        //根据type判断删除操作是来自于FavoriteFag还是WebActivity
        mDao.cancelFavorite(id, cancelId -> {
            if (type == 0) {
                mFag.cancelStatus(cancelId);
            }
            mWebActivity.cancelStatus(cancelId);
        });
    }

    public void updataFavorite(FavoriteBean bean) {
        mDao.updataFavorite(bean, list -> {
            FavoriteBean data = list.get(0);

        });
    }

    public void queryAllFavorite() {
        mDao.queryAllFavorite(list -> mFag.queryAllFavorite(list));

    }

    public void queryFavoriteIsCollect(String gankId) {
        mDao.quetyConditional(gankId, list -> mWebActivity.queryFavoriteIsCollect(list));

    }


}
