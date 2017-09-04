package com.yibao.biggirl.model.music;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/3 14:31
 */
public class MusicInfo
        implements Parcelable
{

    private long   id;
    private String title;
    private String artist;
    private String album;
    private long   albumId;
    private long   duration;
    private long   size;
    private String url;

    private String songId;
    private String songName;
    private String picUrl;
    private String audio;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(long albumId) {
        this.albumId = albumId;
    }

    public long getDuration() {
        return duration;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public static Creator<MusicInfo> getCREATOR() {
        return CREATOR;
    }

    public MusicInfo() {

    }

    protected MusicInfo(Parcel in) {
        id = in.readLong();
        title = in.readString();
        artist = in.readString();
        album = in.readString();
        albumId = in.readLong();
        duration = in.readLong();
        size = in.readLong();
        url = in.readString();
        songId = in.readString();
        songName = in.readString();
        picUrl = in.readString();
        audio = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(title);
        dest.writeString(artist);
        dest.writeString(album);
        dest.writeLong(albumId);
        dest.writeLong(duration);
        dest.writeLong(size);
        dest.writeString(url);
        dest.writeString(songId);
        dest.writeString(songName);
        dest.writeString(picUrl);
        dest.writeString(audio);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<MusicInfo> CREATOR = new Creator<MusicInfo>() {
        @Override
        public MusicInfo createFromParcel(Parcel in) {
            return new MusicInfo(in);
        }

        @Override
        public MusicInfo[] newArray(int size) {
            return new MusicInfo[size];
        }
    };

    @Override
    public String toString() {
        return "MusicInfo{" + "id=" + id + ", title='" + title + '\'' + ", artist='" + artist + '\'' + ", album='" + album + '\'' + ", albumId=" + albumId + ", duration=" + duration + ", size=" + size + ", url='" + url + '\'' + ", songId='" + songId + '\'' + ", songName='" + songName + '\'' + ", picUrl='" + picUrl + '\'' + ", audio='" + audio + '\'' + '}';
    }


}
