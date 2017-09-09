package com.yibao.biggirl.model.music;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/8 00:24
 */
public interface MusicDataSource {

    interface InsertFavMusicCallBack {
        void insertStatus(Long insertStatus);

    }


    interface QueryAllFavMusicCallBack {
        void queryAllFavMusic(List<MusicInfo> list);

    }

    interface QueryConditionalMusicCallBack {
        void quetyConditional(List<MusicInfo> list);

    }



    void insertFavMusic(MusicInfo info, InsertFavMusicCallBack callBack);

    void cancelFavMusic(MusicInfo info);

    void queryAllFavMusic(QueryAllFavMusicCallBack callBack);

    void quetyConditionalMusic(String title, QueryConditionalMusicCallBack callBack);



}
