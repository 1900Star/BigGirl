package com.yibao.biggirl.model.girls;

import android.annotation.SuppressLint;

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
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/26 00:52
 *
 * @author Stran
 */
@SuppressLint("CheckResult")
public class RemoteGirlsData
        implements GrilsDataSource {


    private int totalPages = 1;

    @Override
    public void getGirls(String dataType, int size, int page, LoadDataCallback callback) {
        List<String> urlList = new ArrayList<>();
        RetrofitHelper.getGankApi(Constants.GANK_API)
                .getConmmentApi(dataType, size, page)
                .subscribeOn(Schedulers.io())
                .map(girlsBean -> {
            for (ResultsBeanX data : girlsBean.getResults()) {
                urlList.add(data.getUrl());
            }
            return urlList;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<List<String>>() {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(List<String> list) {
                LogUtil.d("lsp", " list size    "+list.size());
                callback.onLoadDatas(list);
            }

            @Override
            public void onError(Throwable e) {
                callback.onDataNotAvailable();
            }

            @Override
            public void onComplete() {

            }
        });
    }

    @SuppressLint("CheckResult")
    @Override
    public void getMeizitu(String type, int page, LoadMeizituCallback callback) {

        String url = Constants.FRAGMENT_BAORU.equals(type)
                ? Constants.MEIZITU_TAG_API + type + "/page/" + page
                : Constants.MEIZITU_API + type + "/page/" + page;
        LogUtil.d("======== BaoruUrl ====     ", url);
        final String fakeRefer = "i.meizitu.net" + "/";
        final String realUrl = "http://api.caoliyu.cn/meizitu.php?url=%s&refer=%s";
        Observable.just(Constants.MEIZITU_API).subscribeOn(Schedulers.io()).map(s -> {

            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).timeout(10000).get();
                Element total = doc.select("div.postlist").first();
                Elements items = total.select("li");
                for (Element element : items) {
                    Girl girl = new Girl(String.format(realUrl, element.select("img").first().attr("data-original"), fakeRefer));
                    girl.setLink(element.select("a[href]").attr("href"));
                    girls.add(girl);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return girls;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(callback::onLoadDatas);
    }




    @Override
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
                RemoteGirlsData.this.getMeizis(url, i);
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
