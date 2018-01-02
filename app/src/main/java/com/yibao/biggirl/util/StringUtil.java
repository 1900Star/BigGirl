package com.yibao.biggirl.util;

import android.content.ContentUris;
import android.net.Uri;
import android.util.Log;

import com.yibao.biggirl.R;
import com.yibao.biggirl.model.music.MusicLyrBean;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
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
    private static BufferedReader br;

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
        String underLine = String.valueOf(R.string.under_line);
        if (songName.contains(underLine)) {

            songName = songName.substring(songName.indexOf(underLine) + 1, songName.length());
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

    public static File getLrcFile(String path) {
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


    public static ArrayList<MusicLyrBean> getLyrics(File file) {
        ArrayList<MusicLyrBean> lrcList = new ArrayList<>();
        //        File                    file    = getLrcFile(path);

        if (file == null || !file.exists()) {

            lrcList.add(new MusicLyrBean(0, "没有发现歌词-_-!"));
            return lrcList;
        }
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file), "gbk"));
            String line = br.readLine();
            while (line != null) {
                ArrayList<MusicLyrBean> been = parseLine(line);
                lrcList.addAll(been);
                line = br.readLine();
                LogUtil.d(TAG, line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            lrcList.add(new MusicLyrBean(0, "歌词加载出错 _-_!"));
            return lrcList;
        } finally {
            try {
                if (br != null) {
                    br.close();
                    br = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        Collections.sort(lrcList);
        return lrcList;
    }

    private static ArrayList<MusicLyrBean> parseLine(String str) {
        ArrayList<MusicLyrBean> list    = new ArrayList<>();
        String[]                arr     = str.split("]");
        String                  content = arr[arr.length - 1];
        for (int i = 0; i < arr.length - 1; i++) {
            int          startTime = parseTime(arr[i]);
            MusicLyrBean lrcBean   = new MusicLyrBean(startTime, content);
            list.add(lrcBean);
        }
        return list;
    }

    private static int parseTime(String s) {
        String[] arr = s.split(":");
        String   min = arr[0].substring(1);
        String   sec = arr[1];

        return (int) (Integer.parseInt(min) * 60 * 1000 + Float.parseFloat(sec) * 1000);
    }

    public static String getBottomSheetTitile(int size) {

        return "收藏列表 ( " + size + " )";
    }

}
