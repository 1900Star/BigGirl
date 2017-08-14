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


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mList = new ArrayList<>();
        mFab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }




}
