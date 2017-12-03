package com.yibao.biggirl.mvp.map;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

/**
 * Author：Sid
 * Des：${是否支持GoogleService}
 * Time:2017/12/2 23:53
 */
public class CheckGoogleService {
    public static boolean checkGoogleService(Context context) {
        GoogleApiAvailability googleAPI = GoogleApiAvailability.getInstance();
        int result = googleAPI.isGooglePlayServicesAvailable(context);
        return result == ConnectionResult.SUCCESS;
    }
}
