package com.yibao.biggirl.model.music;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.greendao.MusicBeanDao;
import com.yibao.biggirl.model.greendao.MusicInfoDao;
import com.yibao.biggirl.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/8 00:36
 */
public class MusicDao
        implements MusicDataSource
{

    private final MusicBeanDao dao;

    public MusicDao() {
        dao = MyApplication.getIntstance()
                           .getDaoSession()
                           .getMusicBeanDao();
    }

    @Override
    public void insertFavMusic(MusicBean info, InsertFavMusicCallBack callBack) {
        Observable.just(dao.insert(info))
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aLong -> {
                      LogUtil.d("insertStatus *** :"+aLong);
                      callBack.insertStatus(info.getId());});


    }

    @Override
    public void cancelFavMusic(MusicBean info) {
        dao.delete(info);
    }

    @Override
    public void queryAllFavMusic(QueryAllFavMusicCallBack callBack) {
        Observable.just(dao.queryBuilder().list())
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(callBack::queryAllFavMusic);

    }

    @Override
    public void quetyConditionalMusic(String title, QueryConditionalMusicCallBack callBack)
    {
        Observable.just(dao.queryBuilder().where(MusicInfoDao.Properties.Title.eq(title)).build().list())
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(callBack::quetyConditional);
    }


}
