package com.yibao.biggirl.util;

import android.graphics.Color;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/6 01:01
 */
public class ColorUtil {
    public static int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red   = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue  = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }


}
