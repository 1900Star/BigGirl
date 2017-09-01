package com.yibao.biggirl.mvp.app;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.util.PackagingDataUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Android列表数据}
 * Time:2017/4/23 07:08
 */
public class AppAdapter
        extends BaseRvAdapter<ResultsBeanX>
{


    private Context mContext;

    public AppAdapter(Context context, List<ResultsBeanX> list) {
        super(list);
        mContext = context;

    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, ResultsBeanX bean) {

        if (holder instanceof MyViewHolder) {

            MyViewHolder viewHolder = (MyViewHolder) holder;

            String url = Api.picUrlArr[holder.getAdapterPosition()];
            Glide.with(mContext)
                 .load(url)
                 .asBitmap()
                 .placeholder(R.mipmap.xuan)
                 .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                 .into(viewHolder.mIvIcon);
            String who = bean.getWho();
            String name = who == null
                          ? "Samaritan"
                          : who;

            viewHolder.mTvAndroidName.setText(name);

            String time = bean.getCreatedAt();
            viewHolder.mTvAndroidTime.setText(time.substring(0, time.lastIndexOf("T")));
            viewHolder.mTvAndroidDes.setText(bean.getDesc());

            viewHolder.mIvIcon.setOnLongClickListener(view -> {
                if (mContext instanceof OnRvItemLongClickListener) {
                    ((OnRvItemLongClickListener) mContext).showPreview(url);
                }
                return true;
            });
            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemClickListener) {
                    if (mContext instanceof OnRvItemClickListener) {
                        FavoriteBean data = PackagingDataUtil.objectToFavorite(bean);
                        ((OnRvItemClickListener) mContext).showDetail(data, data.getId());
                    }
                }
            });


        }
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new MyViewHolder(view);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.item_android_frag;
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

}
