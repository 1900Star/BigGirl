package com.yibao.biggirl.mvp.music.musicplay;

import com.yibao.biggirl.model.music.MusicInfo;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/8 00:12
 */
public interface MusicView {
    void  insertMusic(Long status);
    void  deleteMusic();
    void qureAllFavoriteMusic(List<MusicInfo> list);


    void qureCurrentMusic(List<MusicInfo> list);

}
