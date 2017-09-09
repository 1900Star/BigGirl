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
    private ArrayList<MusicInfo> mList;
    private MusicInfo mInfo;

    public MusicDialogInfo(ArrayList<MusicInfo> list, MusicInfo info) {
        mList = list;
        mInfo = info;
    }

    public ArrayList<MusicInfo> getList() {
        return mList;
    }

    public void setList(ArrayList<MusicInfo> list) {
        mList = list;
    }

    public MusicInfo getInfo() {
        return mInfo;
    }

    public void setInfo(MusicInfo info) {
        mInfo = info;
    }

    protected MusicDialogInfo(Parcel in) {
        mList = in.createTypedArrayList(MusicInfo.CREATOR);
        mInfo = in.readParcelable(MusicInfo.class.getClassLoader());
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedList(mList);
        parcel.writeParcelable(mInfo, i);
    }
}
