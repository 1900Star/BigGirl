package com.yibao.biggirl.model.favoriteweb;


import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.NotNull;

/**
 * Author：Sid
 * Des：${收藏数据实体}
 * Time:2017/6/16 01:30
 */
@Entity
public class FavoriteWebBean
        implements Parcelable {

    @Id(autoincrement = true)
    private Long   id;
    @NotNull
    private String url;
    private String gankId;
    private String imagUrl;
    private String des;
    private String name;
    private String type;
    private String time;


    protected FavoriteWebBean(Parcel in) {
        url = in.readString();
        gankId = in.readString();
        imagUrl = in.readString();
        des = in.readString();
        name = in.readString();
        type = in.readString();
        time = in.readString();
    }

    @Generated(hash = 1048979491)
    public FavoriteWebBean(Long id, @NotNull String url, String gankId, String imagUrl,
            String des, String name, String type, String time) {
        this.id = id;
        this.url = url;
        this.gankId = gankId;
        this.imagUrl = imagUrl;
        this.des = des;
        this.name = name;
        this.type = type;
        this.time = time;
    }

    @Generated(hash = 344432436)
    public FavoriteWebBean() {
    }

    public static final Creator<FavoriteWebBean> CREATOR = new Creator<FavoriteWebBean>() {
        @Override
        public FavoriteWebBean createFromParcel(Parcel in) {
            return new FavoriteWebBean(in);
        }

        @Override
        public FavoriteWebBean[] newArray(int size) {
            return new FavoriteWebBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(gankId);
        parcel.writeString(imagUrl);
        parcel.writeString(des);
        parcel.writeString(name);
        parcel.writeString(type);
        parcel.writeString(time);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getGankId() {
        return this.gankId;
    }

    public void setGankId(String gankId) {
        this.gankId = gankId;
    }

    public String getImagUrl() {
        return this.imagUrl;
    }

    public void setImagUrl(String imagUrl) {
        this.imagUrl = imagUrl;
    }

    public String getDes() {
        return this.des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return this.time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

