package com.yibao.biggirl.mvp.music.musicplay;

import com.yibao.biggirl.model.music.MusicDao;
import com.yibao.biggirl.model.music.MusicInfo;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/8 00:21
 */
public class MusicPlayPresenter {

    private       MusicDao           mDao;
    private final MusicPlayDialogFag mFag;

    public MusicPlayPresenter() {
        mDao = new MusicDao();
        mFag = new MusicPlayDialogFag();
    }

    public void insertMusic(MusicInfo info) {
        mDao.insertFavMusic(info, insertStatus -> {
//                mFag.insertMusic(insertStatus);
        });

    }

    public void deleteCurrent(MusicInfo info) {
        mDao.cancelFavMusic(info);
    }

    public void queryAllFav() {
        mDao.queryAllFavMusic(list -> {
//                mFag.qureAllFavoriteMusic(list);
        });
    }

    public void queryCurrent(String title) {
        mDao.quetyConditionalMusic(title, list -> {
//                mFag.qureCurrentMusic(list);
        });
    }


}
