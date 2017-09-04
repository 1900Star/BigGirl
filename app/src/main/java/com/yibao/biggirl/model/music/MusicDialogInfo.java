package com.yibao.biggirl.model.music;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/4 03:04
 */
public class MusicDialogInfo implements Parcelable{
    private ArrayList<MusicInfo> mInfos;
    private String    songName;
    private String    artist;
    private String    url;

    public MusicDialogInfo(ArrayList<MusicInfo> infos, String songName, String artist, String url) {
        mInfos = infos;
        this.songName = songName;
        this.artist = artist;
        this.url = url;
    }

    protected MusicDialogInfo(Parcel in) {
        mInfos = in.createTypedArrayList(MusicInfo.CREATOR);
        songName = in.readString();
        artist = in.readString();
        url = in.readString();
    }

    public static final Creator<MusicDialogInfo> CREATOR = new Creator<MusicDialogInfo>() {
        @Override
        public MusicDialogInfo createFromParcel(Parcel in) {
            return new MusicDialogInfo(in);
        }

        @Override
        public MusicDialogInfo[] newArray(int size) {
            return new MusicDialogInfo[size];
        }
    };

    public ArrayList<MusicInfo> getInfos() {
        return mInfos;
    }

    public String getSongName() {
        return songName;
    }

    public String getArtist() {
        return artist;
    }

    public String getUrl() {
        return url;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mInfos);
        parcel.writeString(songName);
        parcel.writeString(artist);
        parcel.writeString(url);
    }
}
