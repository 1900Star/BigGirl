package com.yibao.biggirl.base;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/7 21:50
 */
public abstract class MyBaseAdapter<ITEMBEANTYPE> extends RecyclerView.Adapter{
    public List<ITEMBEANTYPE> mList=null;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
