package com.yibao.biggirl.util;

import com.yibao.biggirl.network.Api;

import java.util.Random;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/14 05:01
 */
public class RandomUtil {
    public static String getRandomUrl() {

        Random random = new Random();

        return Api.picUrlArr[random.nextInt(Api.picUrlArr.length) + 1];

    }
}
