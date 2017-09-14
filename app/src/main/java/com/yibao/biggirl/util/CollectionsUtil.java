package com.yibao.biggirl.util;

import android.content.Context;

import com.yibao.biggirl.model.music.MusicInfo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/10 01:19
 */
public class CollectionsUtil {

    public static ArrayList<MusicInfo> getMusicList(Context context) {

        ArrayList<MusicInfo> musicList = MusicListUtil.getMusicList(context);
        Collections.sort(musicList, (info, t1) -> {
            String prev = StringUtil.getPinYin(info.getTitle());

            String last = StringUtil.getPinYin(t1.getTitle());
            if (prev.equals("#")) {
                return 1;
            } else if (last.equals("#")) {
                return -1;
            } else {
                return prev.compareTo(last);

            }

        });
        return musicList;
    }

}
