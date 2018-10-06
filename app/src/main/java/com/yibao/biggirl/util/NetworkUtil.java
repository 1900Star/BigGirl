package com.yibao.biggirl.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/7 15:52
 */
public class NetworkUtil {
    /**
     * 判断是否有网络连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {

        if (context != null) {
            ConnectivityManager manager = (ConnectivityManager) context.getSystemService(
                    Context.CONNECTIVITY_SERVICE);
            NetworkInfo info;
            if (manager != null) {
                info = manager.getActiveNetworkInfo();
                return info.isAvailable();
            }
        }
        return false;
    }


}
