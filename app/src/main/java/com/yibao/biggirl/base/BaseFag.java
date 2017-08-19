package com.yibao.biggirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import com.yibao.biggirl.R;

import java.util.ArrayList;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/4 21:55
 */
public abstract class BaseFag<T>
        extends Fragment


{

    public FloatingActionButton mFab;
    public int page = 1;
    public int size = 20;
    public ArrayList<T> mList;


    protected boolean isViewInitiated; //控件是否初始化完成
    protected boolean isVisibleToUser; //页面是否可见
    protected boolean isDataInitiated; //数据是否加载

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }


//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        //        this.isVisibleToUser = isVisibleToUser;
//        //        prepareFetchData(false);
//        if (isVisibleToUser) {
//            loadData();
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
    public abstract void loadData();
    ////
    ////
    //    protected void prepareFetchData(boolean forceUpdate) {
    //        if (isVisibleToUser && isViewInitiated && (!isDataInitiated || forceUpdate)) {
    //            loadData();
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
