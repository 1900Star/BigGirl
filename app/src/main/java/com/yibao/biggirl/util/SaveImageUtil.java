package com.yibao.biggirl.util;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.widget.ImageView;

import com.yibao.biggirl.girl.GirlAdapter;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


/**
 * 作者：Stran on 2017/3/23 19:52
 * 描述：保存图片到本地
 * 邮箱：strangermy@outlook.com
 */
public class SaveImageUtil {
    //直接
    public static void saveBitmap(String url, final GirlAdapter mAdapter) {
        ImageView iv     = (ImageView) mAdapter.getPrimaryItem();
        Bitmap    bitmap = BitmapUtil.drawableToBitmap(iv.getDrawable());


        String name = url.substring(url.lastIndexOf("/") + 1, url.length());

        Observable.just(BitmapUtil.saveBitmap(bitmap, Constants.dir, name, true))
                  .subscribeOn(Schedulers.io())
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(aBoolean -> {
                      if (aBoolean) {
                          SnakbarUtil.showSuccessStatus(iv);
                      } else {
                          SnakbarUtil.showErrorStatus(iv);

                      }
                  });


    }

    public static void savePic(String url, final GirlAdapter mAdapter)
    {

        //        String name = url.substring(url.lastIndexOf("/") + 1, url.length());
        //        LogUtil.d("name ====    " + name);


//        ImageUitl.downloadPic(url,
//
//                              new ImageUitl.OnDownloadListener() {
//                                  @Override
//                                  public void onDownloadSuccess() {
//                                      File    file= new File(Constants.dir + "/", getNameFromUrl(url));
//
//                                      try {
//                                          FileOutputStream fos = new FileOutputStream(file);
//                                          //通过mAdapter拿到当显示的图片
//                                          ImageView iv     = (ImageView) mAdapter.getPrimaryItem();
//                                          Bitmap    bitmap = BitmapUtil.drawableToBitmap(iv.getDrawable());
//                                          //将图片保存到SD卡上
////                                          bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
//                                                                                    LogUtil.d( "下载成功  文件大小==" + bitmap.getByteCount());
//
//                                          //发个意图让MediasSanner去扫描SD卡，将下载的图片更新到图库
//                                          Intent intent = new Intent();
//                                          intent.setAction(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//                                          intent.setData(Uri.fromFile(file));
//                                          MyApplication.getIntstance()
//                                                       .sendBroadcast(intent);
//                                          //                                          LogUtil.d(TAG, "广播发出去了");
//
//
//                                      } catch (FileNotFoundException e) {
//                                          e.printStackTrace();
//                                      }
//
//                                  }
//
//                                  //                                  @Override public void onDownloading(int progress) {}
//
//                                  @Override
//                                  public void onDownloadFailed() {
//                                      LogUtil.d("--------------------------    failed");
//
//                                  }
//                              });
    }

    @NonNull
    private static String getNameFromUrl(String url) {
        return url.substring(url.lastIndexOf("/") + 1, url.length());
    }
}
