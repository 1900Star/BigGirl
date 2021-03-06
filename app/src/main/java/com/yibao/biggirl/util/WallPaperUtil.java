package com.yibao.biggirl.util;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.yibao.biggirl.mvp.gank.girl.GirlAdapter;

import java.io.IOException;


/**
 * 作者：Stran on 2017/3/28 22:22
 * 描述：${壁纸设置}
 * 邮箱：strangermy@outlook.com
 */
public class WallPaperUtil {

    /**
     * 将当前图片设置为壁纸
     *
     * @param adapter
     */

    public static void setWallPaper(Context context, GirlAdapter adapter) {
        WallpaperManager mWpManager = WallpaperManager.getInstance(context);
        ImageView view = (ImageView) adapter.getPrimaryItem();
        Bitmap bitmap = BitmapUtil.drawableToBitmap(view.getDrawable());

        try {
            mWpManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 从图库选择壁纸
     *
     * @param context
     */
    public static void choiceWallPaper(Context context) {
        Intent chooseIntent = new Intent(Intent.ACTION_SET_WALLPAPER);
        context.startActivity(Intent.createChooser(chooseIntent, "选择壁纸"));
    }

    public int add(int a, int b) {
        return a + b;
    }


    public void str() {

        StringBuilder sb = new StringBuilder("www.smartisan.com/page/");
        for (int i = 1; i < 10; i++) {
            LogUtil.d("StringBuilder   == " + sb.append(i));
        }

    }

}
