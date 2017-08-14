package com.yibao.biggirl.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/13 04:43
 */
public class ToastUtil {
    public static void showLong(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();

    }
}
