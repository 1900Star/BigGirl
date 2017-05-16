package com.yibao.biggirl.factory;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.yibao.biggirl.MyApplication;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 15:02
 */
public class RecyclerViewFactory {
    private static final int RECYCLERVIEW_TYPE = 1;

    public static RecyclerView creatRecyclerView(int type) {
        RecyclerView recyclerView = new RecyclerView(MyApplication.getIntstance());
        if (type == RECYCLERVIEW_TYPE) {

            LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getIntstance());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
        } else {
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(type,
                                                                                StaggeredGridLayoutManager.VERTICAL);
            manager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setHasFixedSize(true);
        }

        return recyclerView;
    }

}
