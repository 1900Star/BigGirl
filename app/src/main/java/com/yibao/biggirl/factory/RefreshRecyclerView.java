package com.yibao.biggirl.factory;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.yibao.biggirl.R;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/26 03:49
 */

/**
 * Created by Apple on 2016/9/28.
 * 上拉下拉加载数据的RecyclerView
 */
public class RefreshRecyclerView
        extends RecyclerView
{

    private static final String TAG = "RefreshRecyclerView";



    private int mFooterMeasuredHeight;




    private View                mFooterView;
    //布局管理器
    private LinearLayoutManager lm;
    private boolean hasLoadMoreData = false;

    public RefreshRecyclerView(Context context) {
        this(context, null);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    //初始化
    private void init() {
        initFooterView();
    }


    //初始化脚
    private void initFooterView() {
        mFooterView = inflate(getContext(), R.layout.footer, null);
        //测量
        mFooterView.measure(0, 0);
        mFooterMeasuredHeight = mFooterView.getMeasuredHeight();
        //隐藏
        mFooterView.setPadding(0, -mFooterMeasuredHeight, 0, 0);
    }

    @Override
    public void setAdapter(Adapter adapter) {
        //包装成XWrapAdapter
        adapter = new XWrapAdapter(mFooterView, adapter);
        super.setAdapter(adapter);
    }


    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        lm = (LinearLayoutManager) layout;
    }


    //隐藏脚
    public void hideFooterView() {
        hasLoadMoreData = false;
        mFooterView.setPadding(0, -mFooterMeasuredHeight, 0, 0);
        //刷新数据
        getAdapter().notifyDataSetChanged();
    }

    //滑动状态改变
    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);


        //在静止的状态下   && 必须是最后显示的条目就是RecyclerView的最后一个条目 && 没有在加载更多的数据
        boolean isState = state == RecyclerView.SCROLL_STATE_IDLE;
        //最后一个条目显示的下标
        int     lastVisibleItemPosition = lm.findLastVisibleItemPosition();
        boolean isLastVisibleItem       = lastVisibleItemPosition == getAdapter().getItemCount() - 1;
        if (isState && isLastVisibleItem && !hasLoadMoreData && mOnLoadMoreListener != null) {
            hasLoadMoreData = true;
            //显示脚
            mFooterView.setPadding(0, 0, 0, 0);
            //滑动到显示的脚的位置
            smoothScrollToPosition(lastVisibleItemPosition);
            //加载数据
            mOnLoadMoreListener.onLoadMore();
        }
    }


    //加载更多的接口
    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    private OnLoadMoreListener mOnLoadMoreListener;

    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.mOnLoadMoreListener = mOnLoadMoreListener;
    }
}

