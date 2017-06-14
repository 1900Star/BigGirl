package com.yibao.biggirl.mvp.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.OnRvItemWebClickListener;
import com.yibao.biggirl.model.video.VideoResultsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Video列表数据}
 * Time:2017/4/23 07:08
 */
class VideoAdapter
        extends BaseRvAdapter<VideoResultsBean>
{


    private Context mContext;

    VideoAdapter(Context context, List<VideoResultsBean> list) {
        super(list);
        mContext = context;
    }

    //对应的ViewHolder
    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    //item布局
    @Override
    protected int getLayoutId() {
        return R.layout.item_video_frag;
    }


    @Override
    protected void bindView(RecyclerView.ViewHolder holder, VideoResultsBean item) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;

            String who = item.getWho();
            String name = who == null
                          ? "Smartisan"
                          : who;

            viewHolder.mTvVideoName.setText(name);

            String time = item.getCreatedAt();
            viewHolder.mTvVideoTime.setText(time.substring(0, time.lastIndexOf("T")));
            viewHolder.mTvVideoType.setText(item.getType());
            viewHolder.mTvVideoDes.setText(item.getDesc());
            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemWebClickListener) {
                    ((OnRvItemWebClickListener) mContext).showDesDetall(item.getUrl());
                }
            });
        }

    }


    static class ViewHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.tv_video_name)
        TextView       mTvVideoName;
        @BindView(R.id.tv_video_des)
        TextView       mTvVideoDes;
        @BindView(R.id.ll)
        LinearLayout   mLl;
        @BindView(R.id.tv_video_type)
        TextView       mTvVideoType;
        @BindView(R.id.tv_video_time)
        TextView       mTvVideoTime;
        @BindView(R.id.rl_video)
        RelativeLayout mRlVideo;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
