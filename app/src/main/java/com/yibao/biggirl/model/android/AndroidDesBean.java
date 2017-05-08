package com.yibao.biggirl.model.android;

import com.google.gson.Gson;

import java.util.List;

/**
 * Author：Sid
 * Des：${Android 页面}
 * Time:2017/4/23 07:29
 */
public class AndroidDesBean {

    /**
     * error : false
     * results : [{"_id":"58f8b3b7421aa9544ed889a6","createdAt":"2017-04-20T21:12:23.526Z","desc":"支持搜索的spinner","images":["http://img.gank.io/07318b56-1902-4b4d-878e-c9bbcf9babbb"],"publishedAt":"2017-04-21T11:30:29.323Z","source":"chrome","type":"Android","url":"https://github.com/miteshpithadiya/SearchableSpinner","used":true,"who":"galois"}]
     * 数据来源于 干货集中营  www.gank.io
     */

    private boolean            error;
    private List<ResultsBeanX> results;

    public static AndroidDesBean objectFromData(String str) {

        return new Gson().fromJson(str, AndroidDesBean.class);
    }

    public boolean isError() { return error;}

    public void setError(boolean error) { this.error = error;}

    public List<ResultsBeanX> getResults() { return results;}

    public void setResults(List<ResultsBeanX> results) { this.results = results;}
}
