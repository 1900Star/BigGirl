package com.yibao.biggirl.model.app;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * Author：Sid
 * Des：${Android 页面}
 * Time:2017/4/23 07:29
 */
public class GankDesBean
        implements Parcelable
{

    /**
     * error : false
     * results : [{"_id":"58f8b3b7421aa9544ed889a6","createdAt":"2017-04-20T21:12:23.526Z","desc":"支持搜索的spinner","images":["http://img.gank.io/07318b56-1902-4b4d-878e-c9bbcf9babbb"],"publishedAt":"2017-04-21T11:30:29.323Z","source":"chrome","position":"Android","url":"https://github.com/miteshpithadiya/SearchableSpinner","used":true,"who":"galois"}]
     * 数据来源于 干货集中营  www.gank.io
     */

    private boolean            error;
    private List<ResultsBeanX> results;

    protected GankDesBean(Parcel in) {
        error = in.readByte() != 0;
        results = in.createTypedArrayList(ResultsBeanX.CREATOR);
    }

    public static final Creator<GankDesBean> CREATOR = new Creator<GankDesBean>() {
        @Override
        public GankDesBean createFromParcel(Parcel in) {
            return new GankDesBean(in);
        }

        @Override
        public GankDesBean[] newArray(int size) {
            return new GankDesBean[size];
        }
    };

    public static GankDesBean objectFromData(String str) {

        return new Gson().fromJson(str, GankDesBean.class);
    }

    public boolean isError() { return error;}

    public void setError(boolean error) { this.error = error;}

    public List<ResultsBeanX> getResults() { return results;}

    public void setResults(List<ResultsBeanX> results) { this.results = results;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (error
                                 ? 1
                                 : 0));
        parcel.writeTypedList(results);
    }
}
