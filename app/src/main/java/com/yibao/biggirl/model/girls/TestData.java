package com.yibao.biggirl.model.girls;

import android.annotation.SuppressLint;
import android.util.Log;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.network.RetrofitHelper;
import com.yibao.biggirl.service.MeizituService;
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
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/26 00:52
 *
 * @author Stran
 */
@SuppressLint("CheckResult")
public class TestData {


    private int totalPages = 1;

    @SuppressLint("CheckResult")
    public String getMeizitu() {
        String url = "https://inbangbang.com/page/10/";
            Document document;
            try {
//                document= Jsoup.connect(url).timeout(10000).get();
                document = Jsoup.connect(url).get();
                Elements items = document.select("cardlist");
//                Elements items = total.select("cardlist");
                Elements card_list = items.select("card_list");
                LogUtil.d(card_list.toString());
                for (Element element : card_list) {
//                    String img = element.select("img").first().attr("data-original");
                    Girl girl = new Girl("");
//                    girl.setLink(element.select("a[href]").attr("href"));
                    String href = element.select("a[href]").attr("href");
                    LogUtil.d(href);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


//        String url = Constants.FRAGMENT_BAORU.equals(type)
//                ? Constants.MEIZITU_TAG_API + type + "/page/" + page
//                : Constants.MEIZITU_API + type + "/page/" + page;
//        String url = "https://inbangbang.com/page/10/";

//        LogUtil.d("======== BaoruUrl ====     ", url);
//        final String fakeRefer = "i.meizitu.net" + "/";
//        final String realUrl = "http://api.caoliyu.cn/meizitu.php?url=%s&refer=%s";
//        Observable.just("https://inbangbang.com").subscribeOn(Schedulers.io()).map(s -> {
//
//            List<Girl> girls = new ArrayList<>();
//            try {
////                Document doc = Jsoup.connect(url).timeout(10000).get();
//                Document document = Jsoup.connect(url).get();
//                Element total = document.select("div.cardlist").first();
//                Elements items = total.select("card-item");
//                for (Element element : items) {
//                    String img = element.select("img").first().attr("data-original");
//                    Girl girl = new Girl(img);
//                    girl.setLink(element.select("a[href]").attr("href"));
//                    girls.add(girl);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//            return girls;
//        }).observeOn(AndroidSchedulers.mainThread()).subscribe();
        return "";
    }


    public void getMeiziList(String url) {
        Observable.just(url).subscribeOn(Schedulers.io()).map(s -> {
            try {
                Document doc = Jsoup.connect(s).timeout(10000).get();
                Element total = doc.select("div.pagenavi").first();
                Elements spans = total.select("span");
                for (Element str : spans) {
                    int page;
                    try {
                        page = Integer.parseInt(str.text());
                        if (page >= totalPages) {
                            totalPages = page;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return totalPages;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(integer -> {

            for (int i = 0; i < integer; i++) {
                TestData.this.getMeizis(url, i);
            }
        });
    }

    private void getMeizis(String baseUrl, int page) {
        String url = baseUrl + "/" + page;

        final String fakeRefer = baseUrl + "/";
        final String realUrl = "http://api.caoliyu.cn/meizitu.php?url=%s&refer=%s";
        Observable.just(url).subscribeOn(Schedulers.io()).map(s -> {
            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(s).timeout(10000).get();
                Element total = doc.select("div.main-image").first();
                String attr = total.select("img").first().attr("src");
                girls.add(new Girl(String.format(realUrl, attr, fakeRefer)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return girls;

        }).observeOn(AndroidSchedulers.mainThread()).subscribe(girlList -> MeizituService.start(MyApplication.getIntstance(), baseUrl, girlList));


    }


}
