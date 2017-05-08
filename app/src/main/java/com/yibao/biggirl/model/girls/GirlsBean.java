package com.yibao.biggirl.model.girls;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

import java.util.List;

/**
 * 作者：Stran on 2017/3/29 07:25
 * 描述：${漂亮妹子}
 * 邮箱：strangermy@outlook.com
 */
public class GirlsBean implements Parcelable{

    /**
     * error : false
     * results : [{"_id":"58d49bad421aa93abf5d3b76","createdAt":"2017-03-24T12:08:13.590Z","desc":"3-24","publishedAt":"2017-03-24T12:12:34.753Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-24-17438359_1470934682925012_1066984844010979328_n.jpg","used":true,"who":"dmj"}]
     * 数据来源于 干货集中营  www.gank.io
     */

    private boolean           error;
    private List<ResultsBean> results;

    protected GirlsBean(Parcel in) {
        error = in.readByte() != 0;
    }

    public static final Creator<GirlsBean> CREATOR = new Creator<GirlsBean>() {
        @Override
        public GirlsBean createFromParcel(Parcel in) {
            return new GirlsBean(in);
        }

        @Override
        public GirlsBean[] newArray(int size) {
            return new GirlsBean[size];
        }
    };

    public static GirlsBean objectFromData(String str) {

        return new Gson().fromJson(str, GirlsBean.class);
    }

    public boolean isError() { return error;}

    public void setError(boolean error) { this.error = error;}

    public List<ResultsBean> getResults() { return results;}

    public void setResults(List<ResultsBean> results) { this.results = results;}


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte((byte) (error
                                 ? 1
                                 : 0));
    }
}
