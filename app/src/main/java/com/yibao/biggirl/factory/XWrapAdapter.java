package com.yibao.biggirl.factory;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/26 03:52
 * 包装的适配器：区分头、脚、正常的数据(返回值是0)
 */
public class XWrapAdapter
        extends RecyclerView.Adapter
{
    //脚布局
    private View                 mFooterView;
    //正常的适配器
    private RecyclerView.Adapter mAdapter;

    public XWrapAdapter( View mFooterView, RecyclerView.Adapter mAdapter) {
        this.mFooterView = mFooterView;
        this.mAdapter = mAdapter;
    }

    //处理条目的类型
    @Override
    public int getItemViewType(int position) {
        //正常的布局
//        int adjPoistion  = position - 1;
        int adapterCount = mAdapter.getItemCount();
        if (position < adapterCount) {
            return mAdapter.getItemViewType(position);
        }
        //脚
        return RecyclerView.INVALID_TYPE - 1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if (viewType == RecyclerView.INVALID_TYPE - 1) {
            //脚
            return new FooterViewHolder(mFooterView);
        }
        //正常
        return mAdapter.onCreateViewHolder(parent, viewType);
    }

    //绑定数据
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        //正常布局

        int adapterCount = mAdapter.getItemCount();
        if (position < adapterCount) {
            mAdapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return mAdapter.getItemCount() + 2;
    }


    //脚的ViewHolder
    static class FooterViewHolder
            extends RecyclerView.ViewHolder
    {

        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }


}
