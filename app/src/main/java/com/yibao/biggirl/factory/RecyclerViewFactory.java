package com.yibao.biggirl.factory;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yibao.biggirl.MyApplication;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 15:02
 */
public class RecyclerViewFactory {
    private static final int RECYCLERVIEW_NORMAL = 1;
    private static RecyclerView recyclerView;
    public static final int IDLE = 0;

    public static final int DRAGGING = 1;


    public static final int SETTLING = 2;

    public static RecyclerView creatRecyclerView(int type,
                                                 ImageView fab,
                                                 RecyclerView.Adapter<RecyclerView.ViewHolder> adapter)
    {

        recyclerView = new RecyclerView(MyApplication.getIntstance());
        if (type == RECYCLERVIEW_NORMAL) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                                                                             RecyclerView.LayoutParams.MATCH_PARENT);

            LinearLayoutManager manager = new LinearLayoutManager(MyApplication.getIntstance());
            manager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setVerticalScrollBarEnabled(true);
            recyclerView.setScrollBarSize(3);
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
        addScrollListener(fab, adapter, type);
        return recyclerView;
    }

    private static void addScrollListener(ImageView fab,
                                          RecyclerView.Adapter<RecyclerView.ViewHolder> adapter,
                                          int type)
    {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            private int[] mBottom;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
//                int lastCount = adapter.getItemCount() - 1;
                if (newState == IDLE /*&& mBottom[0] == lastCount || mBottom[1] == lastCount*/) {
                    //                                        boolean isRefresh = mSwipeRefresh.isRefreshing();
                    //                                        if (isRefresh) {
                    //                        adapter.notifyItemRemoved(adapter.getItemCount());
                    //                    } else {
                    //                        adapter.changeMoreStatus(Constants.LOADING_DATA);
                    //                        mPresenter.loadData(size, page, Constants.PULLUP_LOAD_MORE_DATA);
                    //
                    //                    }
                    fab.setVisibility(View.VISIBLE);

                } else if (newState == DRAGGING | newState == SETTLING) {
                    fab.setVisibility(View.INVISIBLE);

                } else {
                    fab.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //                LogUtil.d("====  RecyclerView   ==" + recyclerView.getChildCount());

                //                StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
                //
                //                mBottom = manager.findFirstCompletelyVisibleItemPositions(new int[2]);
            }
        });


    }


}
