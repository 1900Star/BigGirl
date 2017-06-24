package com.yibao.biggirl.model.android;

import android.os.Parcel;
import android.os.Parcelable;

import com.yibao.biggirl.model.girls.ResultsBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${用于将 "福利" 和 "Android" 的数据组合在一起}
 * Time:2017/4/24 06:45
 */
public class AndroidAndGirl implements Parcelable{

    public List<ResultsBeanX> mAndroidData;
    public List<ResultsBean>  mGirlData;

    public AndroidAndGirl() {}

    public AndroidAndGirl(List<ResultsBeanX> resultsBeanXes, List<ResultsBean> resultsBeen) {
        mAndroidData = resultsBeanXes;
        mGirlData = resultsBeen;
    }

    protected AndroidAndGirl(Parcel in) {
        mGirlData = in.createTypedArrayList(ResultsBean.CREATOR);
    }

    public static final Creator<AndroidAndGirl> CREATOR = new Creator<AndroidAndGirl>() {
        @Override
        public AndroidAndGirl createFromParcel(Parcel in) {
            return new AndroidAndGirl(in);
        }

        @Override
        public AndroidAndGirl[] newArray(int size) {
            return new AndroidAndGirl[size];
        }
    };

    public List<ResultsBeanX> getAndroidData() {
        return mAndroidData;
    }

    public List<ResultsBean> getGirlData() {
        return mGirlData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {parcel.writeTypedList(mGirlData);}
}
