package com.yibao.biggirl.model.favoriteweb;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.greendao.FavoriteWebBeanDao;
import com.yibao.biggirl.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/16 02:39
 */
public class FavoriteWebDao
        implements FavoriteDaoInterface
{
    private FavoriteWebBeanDao dao;

    public FavoriteWebDao() {

         dao = MyApplication.getIntstance()
                                                             .getDaoSession()
                                                             .getFavoriteWebBeanDao();
    }

    @Override
    public void insertFavorite(FavoriteWebBean bean, InsertFavoriteCallBack callBack)
    {
        Observable.just(dao.insert(bean))
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Consumer<Long>() {
                      @Override
                      public void accept(@NonNull Long insertStatus)
                              throws Exception
                      {callBack.insertStatus(bean.getId());}
                  });
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
//
        Observable.just(dao.queryBuilder()
                           .where(FavoriteWebBeanDao.Properties.GankId.eq(gankId))
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
    public void updataFavorite(FavoriteWebBean bean, UpdataFavoriteCallBack callBack)
    {
        dao.update(bean);

    }

}
