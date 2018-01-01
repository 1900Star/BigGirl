package com.yibao.biggirl.factory;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.base.BaseRvAdapter;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 15:02
 */
public class RecyclerFactory {
    private static final int RECYCLERVIEW_NORMAL = 1;
    private static final int RECYCLERVIEW_STAGGERED = 2;

    public static RecyclerView creatRecyclerView(int type,
                                                 RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
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

    public static void backTop(RecyclerView recyclerView, int type) {
        BaseRvAdapter adapter = (BaseRvAdapter) recyclerView.getAdapter();
        int position = adapter.getPositionForSection(0);
        if (type == RECYCLERVIEW_NORMAL) {
            LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager();
            manager.scrollToPositionWithOffset(position, 0);
        } else if (type==RECYCLERVIEW_STAGGERED){
            StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(type,
                    StaggeredGridLayoutManager.VERTICAL);

            manager.scrollToPositionWithOffset(position,0);
        }

    }
}
