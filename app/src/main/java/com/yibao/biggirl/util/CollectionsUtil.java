package com.yibao.biggirl.util;

import com.yibao.biggirl.model.music.MusicInfo;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/10 01:19
 */
public class CollectionsUtil {
    private static String hashtag = "#";
    public static ArrayList<MusicInfo> getMusicList(ArrayList<MusicInfo> musicList) {

        Collections.sort(musicList, (info, t1) -> {
            String prev = StringUtil.getPinYin(info.getTitle());

            String last = StringUtil.getPinYin(t1.getTitle());
            if (hashtag.equals(prev)) {
                return 1;
            } else if (hashtag.equals(last)) {
                return -1;
            } else {
                return prev.compareTo(last);

            }

        });
        return musicList;
    }

}
