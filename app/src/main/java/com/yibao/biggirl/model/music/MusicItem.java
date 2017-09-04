package com.yibao.biggirl.model.music;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.MediaStore;

import java.util.ArrayList;

/**
 * Created by ThinkPad on 2016/7/27.
 */
public class MusicItem
        implements Parcelable

{
    private String path;
    private String title;
    private String artist;


    private MusicItem() {
    }

    private MusicItem(Parcel in) {
        path = in.readString();
        title = in.readString();
        artist = in.readString();
    }

    public static final Creator<MusicItem> CREATOR = new Creator<MusicItem>() {
        @Override
        public MusicItem createFromParcel(Parcel in) {
            return new MusicItem(in);
        }

        @Override
        public MusicItem[] newArray(int size) {
            return new MusicItem[size];
        }
    };

    //根据当前条目的cursor获取所有音乐播放列表
    public static ArrayList<MusicItem> getAudioItems(Cursor cursor) {
        //创建列表
        ArrayList<MusicItem> musicItems = new ArrayList<MusicItem>();
        //判断cursor是否为空
        if (cursor == null || cursor.getCount() == 0) { return musicItems; }
        //解析cursor添加到集合中
        //移动游标到-1位
        cursor.moveToPosition(-1);
        //返回集合
        while (cursor.moveToNext()) {
            MusicItem musicItem = getAudioItem(cursor);
            musicItems.add(musicItem);
        }
        return musicItems;
    }

    //    根据当前条目的cursor获取
    public static MusicItem getAudioItem(Cursor cursor) {
        //创建audioitem
        MusicItem musicItem = new MusicItem();
        //判断cursor是否为空
        if (cursor == null || cursor.getCount() == 0) { return musicItem; }
        //解析cursor
        musicItem.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        musicItem.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        musicItem.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));

        musicItem.title = musicItem.title.substring(0, musicItem.title.lastIndexOf("."));
        //返回audioitem
        return musicItem;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(path);
        parcel.writeString(title);
        parcel.writeString(artist);
    }
}
