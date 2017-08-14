package com.yibao.biggirl.mvp.android;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.model.android.AndroidAndGirl;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.model.girls.ResultsBean;
import com.yibao.biggirl.util.PackagingDataUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Android列表数据}
 * Time:2017/4/23 07:08
 */
public class AndroidAdapter
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{


    private Context              mContext;
    private List<AndroidAndGirl> mList;
    private static final int TYPE_ITEM   = 0;
    private static final int TYPE_FOOTER = 1;


    public AndroidAdapter(Context context, List<AndroidAndGirl> list) {
        mContext = context;
        mList = list;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {

            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.item_android_frag, parent, false);

            return new MyViewHolder(view);
        } else if (viewType == TYPE_FOOTER) {
            View view = LayoutInflater.from(parent.getContext())
                                      .inflate(R.layout.load_more_footview, parent, false);
            return new LoadMoreHolder(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder) {
            MyViewHolder viewHolder = (MyViewHolder) holder;


            AndroidAndGirl item = mList.get(position);
            if (item != null) {


                ResultsBeanX androidData = item.getAndroidData()
                                               .get(position);
                ResultsBean girlData = item.getGirlData()
                                           .get(position);
                Glide.with(mContext)
                     .load(girlData.getUrl())
                     .asBitmap()
                     .placeholder(R.mipmap.xuan)
                     .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                     .into(viewHolder.mIvIcon);


                String who = androidData.getWho();
                String name = who == null
                              ? "Samaritan"
                              : who;

                viewHolder.mTvAndroidName.setText(name);

                String time = androidData.getCreatedAt();
                                viewHolder.mTvAndroidTime.setText(time.substring(0, time.lastIndexOf("T")));
                                viewHolder.mTvAndroidDes.setText(androidData.getDesc());
                holder.itemView.setOnClickListener(view -> {
                    if (mContext instanceof OnRvItemClickListener) {
                        FavoriteBean bean = PackagingDataUtil.objectToFavorite(androidData);
                        ((OnRvItemClickListener) mContext).showDetail(bean, bean.getId());
                    }
                });
            }
        } else if (holder instanceof LoadMoreHolder) {

        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        }
        return TYPE_ITEM;
    }

    @Override
    public int getItemCount() {

        return mList.size();
    }

    public void AddFooter(List<AndroidAndGirl> list) {

        mList.addAll(list);

    }

    void clear() {
        mList.clear();
    }


    static class MyViewHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv_icon)
        ImageView    mIvIcon;
        @BindView(R.id.tv_video_name)
        TextView     mTvAndroidName;
        @BindView(R.id.tv_video_des)
        TextView     mTvAndroidDes;
        @BindView(R.id.ll)
        LinearLayout mLl;
        @BindView(R.id.tv_video_time)
        TextView     mTvAndroidTime;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
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
