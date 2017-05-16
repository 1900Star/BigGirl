package com.yibao.biggirl.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.OnRvItemWebClickListener;
import com.yibao.biggirl.model.video.VideoResultsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Android列表数据}
 * Time:2017/4/23 07:08
 */
public class VideoAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{


    private Context                mContext;
    private List<VideoResultsBean> mList;
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


    public VideoAdapter(Context context, List<VideoResultsBean> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.item_video_frag, parent, false);

            return new ViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.load_more_footview, parent, false);
            return new LoadMoreHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            VideoResultsBean item = mList.get(position);


            String who = item.getWho();
            String name = who == null
                          ? "Smartisan"
                          : who;

            viewHolder.mTvAndroidName.setText(name);

            String time = item.getCreatedAt();
            viewHolder.mTvAndroidTime.setText(time.substring(0, time.lastIndexOf("T")));
            viewHolder.mTvAndroidDes.setText(item.getDesc());
            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemWebClickListener) {

                    ((OnRvItemWebClickListener) mContext).showDesDetall(item.getUrl());
                }
            });
        } else if (holder instanceof LoadMoreHolder) {
            LoadMoreHolder viewHolder = (LoadMoreHolder) holder;
            viewHolder.mLoadLayout.setVisibility(View.VISIBLE);
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

    void clear() {
        mList.clear();
    }


    void AddHeader(List<VideoResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();


    }

    void AddFooter(List<VideoResultsBean> list) {
        mList.addAll(list);
        notifyDataSetChanged();
        //        notifyItemRemoved(getItemCount());

    }

    void changeMoreStatus(int status) {
        LOAD_MORE_STATUS = status;
        notifyDataSetChanged();

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


    static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_android_name)
        TextView       mTvAndroidName;
        @BindView(R.id.tv_android_des)
        TextView       mTvAndroidDes;
        @BindView(R.id.tv_android_time)
        TextView       mTvAndroidTime;
        @BindView(R.id.rl_video)
        RelativeLayout mRlVideo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
