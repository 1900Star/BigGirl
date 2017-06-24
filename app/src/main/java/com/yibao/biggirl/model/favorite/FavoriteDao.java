package com.yibao.biggirl.model.favorite;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.greendao.FavoriteBeanDao;
import com.yibao.biggirl.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/16 02:39
 */
public class FavoriteDao
        implements FavoriteDaoInterface
{
    private FavoriteBeanDao dao;

    public FavoriteDao() {

        dao = MyApplication.getIntstance()
                           .getDaoSession()
                           .getFavoriteBeanDao();
    }

    @Override
    public void insertFavorite(FavoriteBean bean, InsertFavoriteCallBack callBack)
    {
        Observable.just(dao.insert(bean))
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(insertStatus -> callBack.insertStatus(bean.getId()));
        LogUtil.d("存入后的ID :" + bean.getId());

    }


    @Override
    public void cancelFavorite(Long id, CancelFavoriteCallBack callBack) {

        dao.deleteByKey(id);
        callBack.cancelFavorite(id);


    }

    @Override
    public void quetyConditional(String gankId, QueryConditionalCallBack callBack)
    {

        Observable.just(dao.queryBuilder()
                           .where(FavoriteBeanDao.Properties.GankId.eq(gankId))
                           .build()
                           .list())
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(callBack::conditionalQuery);


    }

    @Override
    public void queryAllFavorite(QueryAllFavoriteCallBack callBack) {
        Observable.just(dao.queryBuilder()
                           .list())
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(callBack::queryAllFavorite);

    }


    @Override
    public void updataFavorite(FavoriteBean bean, UpdataFavoriteCallBack callBack)
    {
        dao.update(bean);

    }

}
