package com.yibao.biggirl.util;

import android.content.ContentUris;
import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Author：Sid
 * Des：${歌曲的时间格式处理}
 * Time:2017/8/12 18:39
 */
public class StringUtil {
    private static final int    HOUR = 60 * 60 * 1000;
    private static final int    MIN  = 60 * 1000;
    private static final int    SEC  = 1000;
    private static       String TAG  = "StringUtil";

    //解析时间
    public static String parseDuration(int duration) {
        int hour = duration / HOUR;
        int min  = duration % HOUR / MIN;
        int sec  = duration % MIN / SEC;
        if (hour == 0) {

            return String.format("%02d:%02d", min, sec);
        } else {
            return String.format("%02d:%02d:%02d", hour, min, sec);
        }
    }

    public static String getSongName(String songName) {
        if (songName.contains("_")) {
            songName = songName.substring(songName.indexOf("_") + 1, songName.length());
        }
        return songName;
    }

    public static Uri getAlbulm(long albulmId) {

        return ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
                                          albulmId);

    }

    //yyyy-MM-dd HH:mm:ss
    public static String getCurrentTime() {
        long             time   = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("MM-dd HH:mm");
        Date             date   = new Date(time);
        return format.format(date);

    }

    private static File getLrcFile(String path) {
        File   file;
        String lrcName = path.replace(".mp3", ".lrc");//找歌曲名称相同的.lrc文件
        file = new File(lrcName);
        if (!file.exists()) {
            lrcName = path.replace(".mp3", ".txt");//歌词可能是.txt结尾
            file = new File(lrcName);
            if (!file.exists()) {
                return null;
            }
        }
        return file;

    }

    public static void getLrc(String path) {
        File file = getLrcFile(path);
        if (file != null) {
            Log.i(TAG, "switchSongUI: mFile != null");
            try {
                BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(new FileInputStream(
                        file)));
                StringBuilder sb = new StringBuilder();
                String        line;
                while ((line = inputStreamReader.readLine()) != null) {
                    sb.append(line)
                      .append('\n');
                }
                LogUtil.d("Lru : " + sb.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 输入汉字返回拼音的通用方法函数。
    public static String getPinYin(String hanzi) {
        ArrayList<HanziToPinyin.Token> tokens = HanziToPinyin.getInstance()
                                                             .get(hanzi);
        StringBuilder sb = new StringBuilder();
        if (tokens != null && tokens.size() > 0) {
            for (HanziToPinyin.Token token : tokens) {
                if (HanziToPinyin.Token.PINYIN == token.type) {
                    sb.append(token.target);
                } else {
                    sb.append(token.source);
                }
            }
        }

        return sb.toString()
                 .toUpperCase()
                 .substring(0, 1);
    }
}
