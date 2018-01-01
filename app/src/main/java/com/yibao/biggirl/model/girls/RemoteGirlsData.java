package com.yibao.biggirl.model.girls;

import com.yibao.biggirl.MyApplication;
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
 */
public class RemoteGirlsData
        implements GrilsDataSource {


    private int totalPages = 1;

    @Override
    public void getGirls(String dataType, int size, int page, LoadGDataCallback callback) {
        List<String> urlList = new ArrayList<>();
        RetrofitHelper.getGankApi(Constants.GANK_API)
                .getGril(dataType, size, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GirlsBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(GirlsBean girlsBean) {
                        List<ResultsBean> results = girlsBean.getResults();


                        for (int i = 0; i < results.size(); i++) {
                            urlList.add(results.get(i)
                                    .getUrl());
                        }
                        callback.onLoadDatas(urlList);
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

    @Override
    public void getMeizitu(String type, int page, LoadGMeizituCallback callback) {
//        4399网站
//        String url = "https://www.4493.com/xingganmote/index-2.htm";
        String url = Constants.MEIZITU_API + type + "/page/" + page;

        final String fakeRefer = url + "/";
        final String realUrl = "http://api.caoliyu.cn/meizitu.php?url=%s&refer=%s";
        //            LogUtil.d(" 4399 ===    " + urlList.size());
//                for (int i = 0; i < urlList.size(); i++) {
//                    Girl girl = urlList.get(i);
//                    LogUtil.d("mm131  Link==    " + girl.getLink());
//                    LogUtil.d("mm131   Url==    " + girl.getUrl());
//                }
        Observable.just(Constants.MEIZITU_API).subscribeOn(Schedulers.io()).map(s -> {

            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).timeout(10000).get();
//                        4399网站
//                Element total = doc.select("div.piclist").first();
//                Elements items = total.select("li");
                Element total = doc.select("div.postlist").first();
                Elements items = total.select("li");
                for (Element element : items) {
                    Girl girl = new Girl(String.format(realUrl, element.select("img").first().attr("data-original"), fakeRefer));
//                    Girl girl = new Girl(String.format(realUrl, element.select("img").first().attr("src"), fakeRefer));
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
//            4399
//        String url = "https://www.4493.com/xingganmote/140883/1.htm";
        Observable.just(url).subscribeOn(Schedulers.io()).map(s -> {
            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(url).timeout(10000).get();
                Element total = doc.select("div.pagenavi").first();
                Elements spans = total.select("span");
                for (Element str : spans) {
                    int page;
                    try {
                        page = Integer.parseInt(str.text());
                        if (page >= totalPages)
                            totalPages = page;
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            return girls;
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(girlList -> {
            LogUtil.d("totalPages ==== " + totalPages);
            for (int i = 0; i < totalPages; i++) {
                getMeizis(url, i);
            }
        });
    }

    private void getMeizis(String baseUrl, int page) {
//        LogUtil.d("mm131   Url==    " + baseUrl);
        String url = baseUrl + "/" + page;


//        String url = "https://www.4493.com/xingganmote/140883/1.htm";
        final String fakeRefer = baseUrl + "/";
        final String realUrl = "http://api.caoliyu.cn/meizitu.php?url=%s&refer=%s";
        Observable.just(url).subscribeOn(Schedulers.io()).map(s -> {
            List<Girl> girls = new ArrayList<>();
            try {
                Document doc = Jsoup.connect(s).timeout(10000).get();
            Element total = doc.select("div.main-image").first();
//                    4399
//                    Element total = doc.select("div.picsbox picsboxcenter").first();
                String attr = total.select("img").first().attr("src");
                girls.add(new Girl(String.format(realUrl, attr, fakeRefer)));
            } catch (IOException e) {
                e.printStackTrace();
            }
            return girls;

        }).observeOn(AndroidSchedulers.mainThread()).subscribe(girlList -> MeizituService.start(MyApplication.getIntstance(), baseUrl, girlList));


    }


}
