package com.yibao.biggirl.base;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yibao.biggirl.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${Base}
 * Time:2017/6/2 13:07
 */
public abstract class BaseRvAdapter<ITEMBEANTYPE>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>

{
    public               List<ITEMBEANTYPE> mList       = null;
    private static final int                TYPE_ITEM   = 0;
    private static final int                TYPE_FOOTER = 1;

    public BaseRvAdapter(List<ITEMBEANTYPE> list) {
        mList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(getLayoutId(), parent, false);
            return getViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.load_more_footview, parent, false);
            return new LoadMoreHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        //将绑定视图交给子类去做
        bindView(holder, mList.get(position));
    }

    protected abstract void bindView(RecyclerView.ViewHolder holder, ITEMBEANTYPE itembeantype);

    protected abstract RecyclerView.ViewHolder getViewHolder(View view);


    @Override
    public int getItemCount() {

        return mList == null
               ? 0
               : mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position ==getItemCount()-1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
        if (params != null && params instanceof StaggeredGridLayoutManager.LayoutParams) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) params;
            p.setFullSpan(holder.getLayoutPosition()==getItemCount()-1);
        }
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if(manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return getItemViewType(position) ==TYPE_FOOTER
                           ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    protected abstract int getLayoutId();

    public void clear() {
        mList.clear();
    }



    public void AddHeader(List<ITEMBEANTYPE> list) {
        mList.addAll(list);


    }

    public void AddFooter(List<ITEMBEANTYPE> list) {
        mList.addAll(list);

    }

    static class LoadMoreHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.pbLoad)
        ProgressBar  mPbLoad;
        @BindView(R.id.tvLoadText)
        TextView     mTvLoadText;
        @BindView(R.id.loadLayout)
        LinearLayout mLoadLayout;

        LoadMoreHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
