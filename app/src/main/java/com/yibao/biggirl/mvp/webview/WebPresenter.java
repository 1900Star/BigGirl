package com.yibao.biggirl.mvp.webview;

import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.model.favorite.FavoriteDao;
import com.yibao.biggirl.mvp.favorite.FavoriteActivity;

/**
 * Author：Sid
 * Des：${  FavoritActivity和WeibActivity共用WebPresenter，FavoritActivity需要 查询所有和
 * 取消收藏操作，WeibActivit需要：新增收藏、取消收藏、查询是否已经收藏 操作}
 * Time:2017/6/17 03:05
 */
public class WebPresenter {
    private FavoriteDao      mDao;
    private WebActivity      mWebActivity;
    private FavoriteActivity mFavoriteActivity;


    public WebPresenter(FavoriteActivity activity) {
        mFavoriteActivity = activity;
        mDao = new FavoriteDao();
    }

    public WebPresenter(WebActivity activity) {
        mWebActivity = activity;
        mDao = new FavoriteDao();
    }

    public void insertFavorite(FavoriteBean favoriteBean) {
        mDao.insertFavorite(favoriteBean, insertStatus -> mWebActivity.insertStatus(insertStatus));
    }

    //根据type(目前只有0和1)判断删除操作是来自于FavoriteFag<0>还是WebActivity<1>
    public void cancelFavorite(Long id, int type) {
        mDao.cancelFavorite(id, cancelId -> {
            if (type == 0) {
                //                    mFavoriteActivity.cancelStatus(cancelId);
            } else {

                mWebActivity.cancelStatus(cancelId);
            }
        });
    }

    public void updataFavorite(FavoriteBean bean) {
        mDao.updataFavorite(bean, list -> {
            FavoriteBean data = list.get(0);

        });
    }

    public void queryAllFavorite() {
        mDao.queryAllFavorite(list -> mFavoriteActivity.queryAllFavorite(list));

    }

    public void queryFavoriteIsCollect(String gankId) {
        mDao.quetyConditional(gankId, list -> mWebActivity.queryFavoriteIsCollect(list));

    }


}
