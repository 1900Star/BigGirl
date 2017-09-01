package com.yibao.biggirl.factory;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.yibao.biggirl.MyApplication;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 15:02
 */
public class RecyclerViewFactory {
    private static final int RECYCLERVIEW_NORMAL = 1;


    public static RecyclerView creatRecyclerView(int type,
                                                 RecyclerView.Adapter<RecyclerView.ViewHolder> adapter)
    {

        RecyclerView recyclerView = new RecyclerView(MyApplication.getIntstance());
        if (type == RECYCLERVIEW_NORMAL) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                                                                             RecyclerView.LayoutParams.MATCH_PARENT);

            LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getIntstance());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setVerticalScrollBarEnabled(true);
            recyclerView.setLayoutManager(manager);
            recyclerView.setLayoutParams(params);

        } else {
            StaggeredGridLayoutManager.LayoutParams params = new StaggeredGridLayoutManager.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.MATCH_PARENT);
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(type,
                                                                                StaggeredGridLayoutManager.VERTICAL);
            manager.setOrientation(StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(manager);
            recyclerView.setLayoutParams(params);
        }
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return recyclerView;
    }

}
