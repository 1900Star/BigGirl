package com.yibao.biggirl.util;

import android.graphics.Color;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/6 01:01
 */
public class ColorUtil {
    public static int errorColor       = Color.argb(255, 255, 64, 129);
    public static int successColor     = Color.argb(255, 90, 181, 63);
    public static int saveColor        = Color.argb(255, 245, 115, 160);
    public static int picAlreadyExists = Color.argb(255, 215, 62, 236);
    public static int exitColor = Color.argb(255, 230, 195, 65);
    public static int transparentColor = Color.argb(0, 0, 0, 0);


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
