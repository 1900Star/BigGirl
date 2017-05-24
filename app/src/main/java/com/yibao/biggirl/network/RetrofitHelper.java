package com.yibao.biggirl.network;


import com.google.gson.Gson;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author：Sid
 * Des：${Retrofit 帮助类}
 * Time:2017/4/10 17:22
 */
public class RetrofitHelper {

    private static Retrofit retrofit;

    public static GirlService getGankApi() {
        if (retrofit == null) {
            synchronized (RetrofitHelper.class) {
                retrofit = new Retrofit.Builder().baseUrl(Constants.GANK_API)
                                                 .addConverterFactory(GsonConverterFactory.create())
                                                 .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                                                 .client(MyApplication.defaultOkHttpClient())
                                                 .build();

            }

        }
        return retrofit.create(GirlService.class);


    }

    public static void getUnsplashApi() {
        Request request = new Request.Builder().url(Constants.UNSPLASH)
                                               .build();
        MyApplication.defaultOkHttpClient()
                     .newCall(request)
                     .enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {

                         }

                         @Override
                         public void onResponse(Call call, Response response)
                                 throws IOException
                         {
                             String json = response.body()
                                                   .string();
                             Gson gson = new Gson();
                             LogUtil.d("Json  = " + json);
                             //                             Unsplash unsplash = gson.fromJson(json, Unsplash.class);
                             //                             UrlsBean urls     = unsplash.getUrls();
                             //                             LogUtil.d("Url==" + urls.getRaw() + "\n" + urls.getFull() + "\n" + urls.getRegular() + "\n" + urls.getSmall());

                         }
                     });


    }

}
