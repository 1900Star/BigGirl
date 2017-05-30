package com.yibao.biggirl.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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

import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：Stran on 2017/3/23 03:23
 * 描述：${保存妹子}
 * 邮箱：strangermy@outlook.com
 */
public class ImageUitl {

    public static ImageView creatZoomView(Context context) {
        ZoomImageView view = new ZoomImageView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT);
        view.setScaleType(ImageView.ScaleType.MATRIX);
        view.reSetState();
        view.setLayoutParams(params);
        //        viewGroup.addView(view, params);

        return view;
    }

    //加载图片
    public static void loadPic(Context context, String url, ImageView view) {
        Glide.with(context)
             .load(url)
             .asBitmap()
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .error(R.mipmap.xuan)
             .into(view);
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
    public static boolean downloadPic(Bitmap bitmap, String url, boolean isShowPhotos)
    {
        String name = getNameFromUrl(url);
        File   path = new File(Constants.dir);
        if (!path.exists()) {
            path.mkdir();
        }
        File file = new File(path + "/", name);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            return true;
        }
        Request request = new Request.Builder().url(url)
                                               .build();
        MyApplication.defaultOkHttpClient()
                     .newCall(request)
                     .enqueue(new Callback() {
                         @Override
                         public void onFailure(okhttp3.Call call, IOException e) {

                         }


                         @Override
                         public void onResponse(okhttp3.Call call, Response response)

                         {
                             InputStream      is;
                             byte[]           buf = new byte[1024 * 4];
                             int              len;
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
                                                  .post(new DownGrilProgressData(progress));

                                 }
                                 bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                                 //发个意图让MediasSanner去扫描SD卡，将下载的图片更新到图库
                                 Intent intent = new Intent();
                                 intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                                 intent.setData(Uri.fromFile(file));
                                 MyApplication.getIntstance()
                                              .sendBroadcast(intent);
                                 LogUtil.d("TTTtt", "广播发出去了");
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


        if (isShowPhotos) {


            try {
                MediaStore.Images.Media.insertImage(MyApplication.getIntstance()
                                                                 .getContentResolver(),
                                                    file.getAbsolutePath(),
                                                    name,
                                                    null);
                LogUtil.d("============================== 保存成功");
            } catch (Exception e) {

                e.printStackTrace();
            }

            //
            //            MyApplication.getIntstance()
            //                         .sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
            //                                                   Uri.fromFile(file.getAbsoluteFile())));
            //                    MyApplication.getIntstance()
            //                                 .sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE,
            //                                                           Uri.parse("file://" + file)));
        }
        return true;

    }


    //初始化数据
    public static List<String> getDefultUrl(List<String> list) {
        Collections.addAll(list, Api.picUrlArr);
        return list;
    }

    //初始化数据
    public static List<String> getUnsplashUrl(List<String> list) {
        for (int i = 0; i < 1000; i++) {
            list.add(Constants.UNSPLASH_API);
        }
        return list;
    }

    /**
     * @param url
     * @return
     * 从下载连接中解析出文件名
     */
    @NonNull
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }

}







