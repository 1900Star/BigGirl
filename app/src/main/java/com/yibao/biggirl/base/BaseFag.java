package com.yibao.biggirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.yibao.biggirl.mvp.girls.GirlsContract;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/4 21:55
 */
public abstract class BaseFag<T>
        extends Fragment
        implements GirlsContract.View<T>


{

    public int page = 1;
    public int size = 20;


    protected boolean isViewInitiated; //控件是否初始化完成
    protected boolean isVisibleToUser; //页面是否可见
    protected boolean isDataInitiated; //数据是否加载
    public    List<T> mList;





    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();

    }


    //    @Override
    //    public void setUserVisibleHint(boolean isVisibleToUser) {
    //        super.setUserVisibleHint(isVisibleToUser);
    //        //        this.isVisibleToUser = isVisibleToUser;
    //        //        prepareFetchData(false);
    //        if (isVisibleToUser) {
    //            loadDatas();
    //        }
    //    }

    ////
    //@Override
    //public void onActivityCreated(Bundle savedInstanceState) {
    //    super.onActivityCreated(savedInstanceState);
    //    isViewInitiated = true;
    //    prepareFetchData(false);
    //}
    ////
    public abstract void loadDatas();
    ////
    ////
    //    protected void prepareFetchData(boolean forceUpdate) {
    //        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
    //            loadDatas();
    //            isDataInitiated = true;
    //        }
    //    }
    //
    //
    //    @Override
    //    public void onDestroy() {
    //        super.onDestroy();
    //    }


}
