package com.yibao.biggirl.model.girls;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.Gson;

/**
 * 作者：Stran on 2017/3/29 07:25
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class ResultsBean implements Parcelable {
    /**
     * _id : 58d49bad421aa93abf5d3b76
     * createdAt : 2017-03-24T12:08:13.590Z
     * desc : 3-24
     * publishedAt : 2017-03-24T12:12:34.753Z
     * source : chrome
     * type : 福利
     * url : http://7xi8d6.com1.z0.glb.clouddn.com/2017-03-24-17438359_1470934682925012_1066984844010979328_n.jpg
     * used : true
     * who : dmj
     * 数据来源于 干货集中营  www.gank.io
     */

    private String _id;
    private String  createdAt;
    private String  desc;
    private String  publishedAt;
    private String  source;
    private String  type;
    private String  url;
    private boolean used;
    private String  who;

    protected ResultsBean(Parcel in) {
        _id = in.readString();
        createdAt = in.readString();
        desc = in.readString();
        publishedAt = in.readString();
        source = in.readString();
        type = in.readString();
        url = in.readString();
        used = in.readByte() != 0;
        who = in.readString();
    }

    public static final Creator<ResultsBean> CREATOR = new Creator<ResultsBean>() {
        @Override
        public ResultsBean createFromParcel(Parcel in) {
            return new ResultsBean(in);
        }

        @Override
        public ResultsBean[] newArray(int size) {
            return new ResultsBean[size];
        }
    };

    public static ResultsBean objectFromData(String str) {

        return new Gson().fromJson(str, ResultsBean.class);
    }

    public String get_id() { return _id;}

    public void set_id(String _id) { this._id = _id;}

    public String getCreatedAt() { return createdAt;}

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}

    public String getDesc() { return desc;}

    public void setDesc(String desc) { this.desc = desc;}

    public String getPublishedAt() { return publishedAt;}

    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt;}

    public String getSource() { return source;}

    public void setSource(String source) { this.source = source;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    public boolean isUsed() { return used;}

    public void setUsed(boolean used) { this.used = used;}

    public String getWho() { return who;}

    public void setWho(String who) { this.who = who;}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(_id);
        parcel.writeString(createdAt);
        parcel.writeString(desc);
        parcel.writeString(publishedAt);
        parcel.writeString(source);
        parcel.writeString(type);
        parcel.writeString(url);
        parcel.writeByte((byte) (used
                                 ? 1
                                 : 0));
        parcel.writeString(who);
    }
}
