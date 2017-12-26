package com.yibao.biggirl.model.girl;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.model.girls
 *  @文件名:   Girl
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 2:15
 *  @描述：    用于封装从网页中解析出的图片地址
 */

import android.os.Parcel;
import android.os.Parcelable;

public class Girl implements Parcelable {
    private String url;
    private int width = 0;
    private int height = 0;
    private String link;//仅妹子图网站需要

    public Girl(String url) {
        this.url = url;
    }

    protected Girl(Parcel in) {
        url = in.readString();
        width = in.readInt();
        height = in.readInt();
        link = in.readString();
    }

    public static final Creator<Girl> CREATOR = new Creator<Girl>() {
        @Override
        public Girl createFromParcel(Parcel in) {
            return new Girl(in);
        }

        @Override
        public Girl[] newArray(int size) {
            return new Girl[size];
        }
    };

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeInt(width);
        parcel.writeInt(height);
        parcel.writeString(link);
    }
}
