package com.yibao.biggirl.util;

import com.yibao.biggirl.model.music.MusicBean;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/14 05:01
 *
 * @author Stran
 */
public class RandomUtil {
    public static int getRandomPostion(ArrayList<MusicBean> list) {
        Random random = new Random();
        return random.nextInt(list.size()) + 1;

    }


}
