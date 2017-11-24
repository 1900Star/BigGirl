package com.yibao.biggirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/4 21:55
 */
public abstract class BaseFag<T>
        extends Fragment


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
        loadDatas();
    }


    public abstract void loadDatas();


}
