package com.yibao.biggirl.util;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/20 13:41
 */
public class DialogUtil {
    public static void creatDialog(Context context, int id) {
        new AlertDialog.Builder(context).setView(LayoutInflater.from(context)
                                                               .inflate(id, null))
                                        .show();
    }
}
