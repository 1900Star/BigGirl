package com.yibao.biggirl.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Parcelable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.Target;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.model.girl.MeizituData;

import java.util.ArrayList;
import java.util.List;


/**
 * @author : Stran
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.service
 * @文件名: MeizituService
 * @创建时间: 2017/12/10 14:42
 * @描述： {解析网页中的图片地址}
 */


public class MeizituService extends IntentService {
    private static final String KEY_EXTRA_GIRL_FROM = "from";
    private static final String KEY_EXTRA_GIRL_LIST = "data";

    public MeizituService() {
        super("MeizituService");
    }

    public static void start(Context context, String url, List<Girl> list) {
        Intent intent = new Intent(context, MeizituService.class);
        intent.putExtra(KEY_EXTRA_GIRL_FROM, url);
        intent.putParcelableArrayListExtra(KEY_EXTRA_GIRL_LIST, (ArrayList<? extends Parcelable>) list);
        context.startService(intent);
    }


    public static void stop(Context context) {
        context.stopService(new Intent(context, MeizituService.class));
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        String from = intent.getStringExtra(KEY_EXTRA_GIRL_FROM);
        List<Girl> girls = intent.getParcelableArrayListExtra(KEY_EXTRA_GIRL_LIST);
        for (final Girl girl : girls) {
            Bitmap bitmap = null;
            try {
                bitmap = Glide.with(MeizituService.this)
                        .load(girl.getUrl())
                        .asBitmap()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                        .get();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                girl.setHeight(bitmap.getHeight());
                girl.setWidth(bitmap.getWidth());
            }
            //  将数据发到MeizituActivity页面
            MyApplication.getIntstance().bus().post(new MeizituData(from, girl));
        }
    }
}
