package com.yibao.biggirl.base;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.yibao.biggirl.factory.RecyclerFactory;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.base
 * @文件名: BaseRecyclerActivity
 * @author: Stran
 * @email: strangermy@outlook.com
 * @创建时间: 2017/3/29 15:25
 * @描述： 凡是页面包含RecyclerView的, 都要继承这个BaseRecyclerActivity
 */

public abstract class BaseRecyclerActivity
        extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener

{


    public int size = 20;
    public int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onRefresh() {
        mDisposable.add(Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    refreshData();
                    page = 1;
                }));
    }

    protected abstract void refreshData();


    /**
     * 长按预览图片
     *
     * @param url
     */
    @Override
    public void onLongTouchPreview(String url) {

    }

    /**
     * 打开Web页面
     *
     * @param bean
     * @param id
     */
    @Override
    public void showWebDetail(FavoriteWebBean bean, Long id) {

    }

    /**
     * @param position 点击的位置  ，  只有GirlsFragment需要传入
     * @param list     打开GirlActivity需要的数据 ，  只有GirlsFragment需要传入
     * @param type     position  0:表示从GirlsFragment打开、1：表示从MeizituFag打开、
     *                 2:表示从DuotuFag打开、 3:表示从SisanFag打开
     * @param link     只有从MeizituFag和DuotuFag打开时需要传入
     */
    @Override
    public void showBigGirl(int position, List<String> list, int type, String link) {

    }

    /**
     * 得到一个RecyclerView   实现了加载更多
     *
     * @param fab
     * @param rvType
     * @param adapter
     * @return
     */
    public RecyclerView getRecyclerView(ImageView fab, int rvType, RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {
        RecyclerView recyclerView = RecyclerFactory.creatRecyclerView(rvType, adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                int lastPosition = -1;
                switch (newState) {
                    case RecyclerView.SCROLL_STATE_IDLE:
                        fab.setVisibility(android.view.View.VISIBLE);
                        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                        if (layoutManager instanceof GridLayoutManager) {
                            //通过LayoutManager找到当前显示的最后的item的position
                            lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof LinearLayoutManager) {
                            lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                            int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                            ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(
                                    lastPositions);
                            lastPosition = findMax(lastPositions);
                        }
                        if (lastPosition == recyclerView.getLayoutManager()
                                .getItemCount() - 1) {
                            page++;
                            loadMoreData();

                        }
                        break;
                    case RecyclerView.SCROLL_STATE_DRAGGING:
                        fab.setVisibility(android.view.View.INVISIBLE);
                        break;
                    case RecyclerView.SCROLL_STATE_SETTLING:
                        fab.setVisibility(android.view.View.INVISIBLE);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                //得到当前显示的最后一个item的view
                View lastChildView = recyclerView.getLayoutManager().getChildAt(recyclerView.getLayoutManager().getChildCount() - 1);
                //得到lastChildView的bottom坐标值
                int lastChildBottom = lastChildView.getBottom();
                //得到Recyclerview的底部坐标减去底部padding值，也就是显示内容最底部的坐标
                int recyclerBottom = recyclerView.getBottom() - recyclerView.getPaddingBottom();
                //通过这个lastChildView得到这个view当前的position值
                int lastPosition = recyclerView.getLayoutManager().getPosition(lastChildView);

                //判断lastChildView的bottom值跟recyclerBottom
                //判断lastPosition是不是最后一个position
                //如果两个条件都满足则说明是真正的滑动到了底部,这时候就可以去加载更多了。
                if (lastChildBottom == recyclerBottom && lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
//                    page++;
//                    loadMoreData();
                    LogUtil.d("");


                }
            }

        });


        return recyclerView;
    }

    protected abstract void loadMoreData();


    /**
     * 找到数组中的最大值
     *
     * @param lastPositions
     * @return
     */
    public int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }


}
