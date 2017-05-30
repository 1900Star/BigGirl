package com.yibao.biggirl.mvp.girls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.util.ImageUitl;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作者：Stran on 2017/3/29 06:11
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class GirlsAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>


{


    private Context mContext;

    //    private ArrayList<ResultsBean> mList;
    private List<String> mList;

    private static final int TYPE_ITEM        = 0;
    private static final int TYPE_FOOTER      = 1;
    //上拉加载更多
    private static final int PULLUP_LOAD_MORE = 2;
    //没有加载更多 隐藏
    private static final int NO_LOAD_MORE     = 3;
    //正在加载中
    private static final int LOADING_MORE     = 4;

    //上拉加载更多状态-默认为0
    private int LOAD_MORE_STATUS = 0;


    private final LayoutInflater mInflater;

    void clear() {
        mList.clear();
    }

    //回调接口

    public interface OnRvItemClickListener {
        void showPagerFragment(int position, List<String> list);

    }


    GirlsAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
        mInflater = LayoutInflater.from(context);

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == TYPE_ITEM) {
            view = mInflater.inflate(R.layout.item_girls, parent, false);

            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            view = mInflater.inflate(R.layout.load_more_footview, parent, false);
            return new LoadMoreViewHolder(view);
        }
        return null;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            ImageUitl.loadPicHolder(mContext, mList.get(position), viewHolder.mGrilImageView);

            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemClickListener) {
                    ((OnRvItemClickListener) mContext).showPagerFragment(position, mList);
                }
            });
        } else if (holder instanceof LoadMoreViewHolder) {
            LoadMoreViewHolder viewHolder = (LoadMoreViewHolder) holder;
            switch (LOAD_MORE_STATUS) {

                case PULLUP_LOAD_MORE:
                    if (mList.size() == 0) {
                        viewHolder.mLoadLayout.setVisibility(View.GONE);
                    }
                    viewHolder.mTvLoadText.setText("上拉加载更多妹子");
                    break;
                case LOADING_MORE:
                    viewHolder.mTvLoadText.setText("正在加载更多妹子");
                    break;
                case NO_LOAD_MORE:
                    viewHolder.mLoadLayout.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }


    }


    @Override
    public int getItemCount() {
        return mList == null
               ? 0
               : mList.size();
    }


    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }


    //**************************************************************************************

    static class ViewHolder
            extends RecyclerView.ViewHolder

    {
        @BindView(R.id.gril_image_view)
        ImageView mGrilImageView;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }


    }


    //加载更多的Holder
    static class LoadMoreViewHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.pbLoad)
        ProgressBar  mPbLoad;
        @BindView(R.id.tvLoadText)
        TextView     mTvLoadText;
        @BindView(R.id.loadLayout)
        LinearLayout mLoadLayout;

        LoadMoreViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);

        }
    }

    void AddHeader(List<String> list) {
        mList.addAll(list);
        notifyDataSetChanged();


    }

    void AddFooter(List<String> items) {
        mList.addAll(items);
        notifyDataSetChanged();
        notifyItemRemoved(getItemCount());

    }

    void changeMoreStatus(int status) {
        LOAD_MORE_STATUS = status;
        notifyDataSetChanged();

    }
}
