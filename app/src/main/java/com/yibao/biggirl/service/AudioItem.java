package com.yibao.biggirl.service;

import android.database.Cursor;
import android.provider.MediaStore;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ThinkPad on 2016/7/27.
 */
public class AudioItem implements Serializable{
    private String path;
    private String title;
    private String artist;
    //根据当前条目的cursor获取所有音乐播放列表
    public static ArrayList<AudioItem> getAudioItems(Cursor cursor){
        //创建列表
        ArrayList<AudioItem> audioItems = new ArrayList<AudioItem>();
        //判断cursor是否为空
        if(cursor==null||cursor.getCount()==0) return audioItems;
        //解析cursor添加到集合中
            //移动游标到-1位
        cursor.moveToPosition(-1);
        //返回集合
        while (cursor.moveToNext()){
            AudioItem audioItem = getAudioItem(cursor);
            audioItems.add(audioItem);
        }
        return audioItems;
    }
    //根据当前条目的cursor获取
    public static AudioItem getAudioItem(Cursor cursor){
        //创建audioitem
        AudioItem audioItem  =new AudioItem();
        //判断cursor是否为空
        if(cursor==null||cursor.getCount()==0) return audioItem;
        //解析cursor
        audioItem.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
        audioItem.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
        audioItem.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
        audioItem.title = audioItem.title.substring(0,audioItem.title.lastIndexOf("."));
        //返回audioitem
        return audioItem;
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
}
