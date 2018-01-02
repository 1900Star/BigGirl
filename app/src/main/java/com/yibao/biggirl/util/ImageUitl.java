package com.yibao.biggirl.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.model.girl.DownGrilProgressData;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.view.ZoomImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Stran on 2017/3/23 03:23
 * 描述：${保存妹子}
 * 邮箱：strangermy@outlook.com
 */
public class ImageUitl {


    private static final int UNSPLASH_URL_SIZE = 1000;
    private static String name;
    private static File file;

    public static ZoomImageView creatZoomView(Context context) {
        ZoomImageView view = new ZoomImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(1080, 1920);
        view.setScaleType(ImageView.ScaleType.MATRIX);
        view.reSetState();
        view.setLayoutParams(params);
        return view;
    }

    //加载图片
    public static void loadPic(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .error(R.mipmap.xuan)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(view);

    }

    //Glide加载圆图
    public static void loadPicCirc(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.xuan)
                .into(new BitmapImageViewTarget(view) {
                    @Override
                    protected void setResource(Bitmap resource) {
                        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(
                                context.getResources(),
                                resource);
                        circularBitmapDrawable.setCircular(true);
                        view.setImageDrawable(circularBitmapDrawable);
                    }
                });
    }

    //加载需要占位图的图片
    public static void loadPicHolder(Context context, String url, ImageView view) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .placeholder(R.mipmap.xuan)
                .error(R.mipmap.xuan)
                .into(view);


    }

    /**
     * 保存图片
     */
    public static int downloadPic(String url, int type) {
        if (type == 1) {
            name = "share_y.jpg";
        } else {
//            name = getNameFromUrl(url);
            name = randomUUID() + ".jpg";
        }

        File path = new File(Constants.DIR);
        if (!path.exists()) {
            path.mkdir();
        }
        file = new File(path + "/", name);
        if (!file.exists()) {
            try {
                file.createNewFile();

            } catch (IOException e) {
                e.printStackTrace();
                return Constants.DWON_PIC_EROOR;
            }
        } else {
            return Constants.EXISTS;
        }

        Request request = new Request.Builder().url(url).addHeader("Accept-Encoding", "identity")
                .build();
        MyApplication.defaultOkHttpClient()
                .newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(okhttp3.Call call, IOException e) {
                        e.printStackTrace();
                        LogUtil.d("下载出错 " + e.toString());
                    }


                    @Override
                    public void onResponse(okhttp3.Call call, Response response)

                    {
                        InputStream is;
                        byte[] buf = new byte[1024 * 4];
                        int len;
                        FileOutputStream fos = null;

                        is = response.body()
                                .byteStream();
                        long total = response.body()
                                .contentLength();
                        try {
                            fos = new FileOutputStream(file);

                            long sum = 0;
                            while ((len = is.read(buf)) != -1) {

                                fos.write(buf, 0, len);
                                sum += len;
                                int progress = (int) (sum * 1.0f / total * 100);
                                //Rxbus发送下载进度
                                MyApplication.getIntstance()
                                        .bus()
                                        .post(new DownGrilProgressData(progress, type));

                            }


                            fos.flush();
                            fos.close();

                        } catch (IOException e) {
                            e.printStackTrace();

                        } finally {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }


                    }


                });
        return Constants.FIRST_DWON;

    }

    //将下载的图片更新到图库
    public static boolean insertImageToPhoto() {

        try {
            MediaStore.Images.Media.insertImage(MyApplication.getIntstance()
                            .getContentResolver(),
                    file.getAbsolutePath(),
                    name,
                    null);
            MyApplication.getIntstance()
                    .sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
                            Uri.parse("file://" + file)));
        } catch (Exception e) {
            LogUtil.d("图片保存出错 ！ " + e.toString());
            e.printStackTrace();
            return false;
        }
        return true;
    }

    //初始化默认妹子数据
    public static List<String> getNormalUrl(List<String> list) {
        Collections.addAll(list, Api.picUrlArr);
        return list;
    }
    //初始化Sex数据

    public static List<String> getSexUrl(List<String> list) {
        Collections.addAll(list, Api.picUrlSex);
        return list;
    }

    //初始化Unsplash数据
    public static List<String> getUnsplashUrl(List<String> list) {
        for (int i = 0; i < UNSPLASH_URL_SIZE; i++) {
            list.add(Constants.UNSPLASH_API);
        }
        return list;
    }

    public static String randomUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    /**
     * @param url
     * @return 从下载连接中解析出文件名
     */
    @NonNull
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

}







