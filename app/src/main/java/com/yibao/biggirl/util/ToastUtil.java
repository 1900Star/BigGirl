package com.yibao.biggirl.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/13 04:43
 */
public class ToastUtil {
    public static void showNoMusic(Context context) {
        Toast.makeText(context, "当前没有歌曲播放-_-", Toast.LENGTH_SHORT).show();

    }

    public static void showScreenOn(Context context) {
        Toast.makeText(context, "您开启了屏幕常亮", Toast.LENGTH_SHORT).show();

    }

    public static void showScreenOf(Context context) {
        Toast.makeText(context, "您关闭了屏幕常亮", Toast.LENGTH_SHORT).show();

    }


}
