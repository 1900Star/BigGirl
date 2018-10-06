package com.yibao.biggirl.model.sisanjiujiu;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;

import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.model.sisanjiujiu
 * @文件名: RemoteSisanjiujiu
 * @author: Stran
 * @创建时间: 2018/1/10 15:46
 * @描述： TODO
 */

public class RemoteSisanjiujiu implements SisanDataSource {


    @SuppressLint("CheckResult")
    @Override
    public void getSisan(String url, int page, LoadSisanCallback callback) {
        String baseUrl = url + page + ".htm";

        Observable.just(baseUrl).subscribeOn(Schedulers.io()).map(s -> {

            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(baseUrl).timeout(10000).get();

                Element total = doc.select("div.piclist").first();
                Elements items = total.select("li");
                for (int i = 1; i < items.size(); i++) {
                    Element element = items.get(i);
                    Girl girl = new Girl(element.select("img").first().attr("src"));
                    girl.setLink(element.select("a[href]").last().attr("href"));
                    girls.add(girl);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return girls;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(urlList -> {
            callback.onLoadDatas(urlList);
           LogUtil.d(" SisanFragment Size   == " + urlList.size());
            for (int i = 0; i < 3; i++) {
                Girl girl = urlList.get(i);
                System.out.println(" url    ==  " + girl.getUrl());

                System.out.println(" link    ==  " + girl.getLink());
            }
        });


    }

    @Override
    public void getSisanSingle(String link, LoadSisanCallback callback) {
        String url = getUrl(link);
        Observable.just(url).subscribeOn(Schedulers.io()).map(s -> {
            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).timeout(10000).get();
                Element total = doc.select("div.picsbox").first();
                Elements items = total.select("p");
                for (Element element : items) {
                    Girl girl = new Girl(element.select("img").first().attr("src"));
                    girls.add(girl);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return girls;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(callback::onLoadDatas);
    }

    @NonNull
    private String getUrl(String link) {
        String sub = link.substring(link.lastIndexOf("/"));
        String replace = link.replace(sub, ".htm");
//        return Constants.SISAN_BASE_URL + replace;
        return "";
    }

}

