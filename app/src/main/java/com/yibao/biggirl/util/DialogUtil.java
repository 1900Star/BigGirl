package com.yibao.biggirl.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.webview.WebViewActivity;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/20 13:41
 */
public class DialogUtil

{


    public static void aboutMeDialog(Context context, int id) {
        View view = LayoutInflater.from(context)
                                  .inflate(id, null);


        new AlertDialog.Builder(context).setView(view)
                                        .show();
    }

    public static void creatDialog(Context context, int id) {
        View view = LayoutInflater.from(context)
                                  .inflate(id, null);
        initListener(context, view);


        new AlertDialog.Builder(context).setView(view)
                                        .show();
    }

    private static void initListener(Context context, View view) {
        TextView feir = (TextView) view.findViewById(R.id.tv_feir);
        TextView xuan = (TextView) view.findViewById(R.id.tv_fxx);
        feir.setOnClickListener(view1 -> showDesDetall(context, Api.myFeir));
        xuan.setOnClickListener(view12 -> showDesDetall(context, Api.myXXuan));
    }

    private static void showDesDetall(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);


    }
}
