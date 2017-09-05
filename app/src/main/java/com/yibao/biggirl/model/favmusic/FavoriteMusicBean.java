package com.yibao.biggirl.model.favmusic;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.NotNull;


/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/5 18:25
 */
//@Entity
public class FavoriteMusicBean
        implements Parcelable
{

//    @Id(autoincrement = true)
    private Long   id;
//    @NotNull
    private String songUrl;
    private String songName;
    private String artist;
    private String favTime;
    private String albumUrl;

    protected FavoriteMusicBean(Parcel in) {
        songUrl = in.readString();
        songName = in.readString();
        artist = in.readString();
        favTime = in.readString();
        albumUrl = in.readString();
    }

    @Generated(hash = 1439211102)
    public FavoriteMusicBean(Long id, @NotNull String songUrl, String songName, String artist,
            String favTime, String albumUrl) {
        this.id = id;
        this.songUrl = songUrl;
        this.songName = songName;
        this.artist = artist;
        this.favTime = favTime;
        this.albumUrl = albumUrl;
    }

    @Generated(hash = 1236532361)
    public FavoriteMusicBean() {
    }

    public static final Creator<FavoriteMusicBean> CREATOR = new Creator<FavoriteMusicBean>() {
        @Override
        public FavoriteMusicBean createFromParcel(Parcel in) {
            return new FavoriteMusicBean(in);
        }

        @Override
        public FavoriteMusicBean[] newArray(int size) {
            return new FavoriteMusicBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(songUrl);
        parcel.writeString(songName);
        parcel.writeString(artist);
        parcel.writeString(favTime);
        parcel.writeString(albumUrl);
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongUrl() {
        return this.songUrl;
    }

    public void setSongUrl(String songUrl) {
        this.songUrl = songUrl;
    }

    public String getSongName() {
        return this.songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return this.artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getFavTime() {
        return this.favTime;
    }

    public void setFavTime(String favTime) {
        this.favTime = favTime;
    }

    public String getAlbumUrl() {
        return this.albumUrl;
    }

    public void setAlbumUrl(String albumUrl) {
        this.albumUrl = albumUrl;
    }
}
