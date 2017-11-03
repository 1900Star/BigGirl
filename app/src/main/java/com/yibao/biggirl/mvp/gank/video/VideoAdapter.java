package com.yibao.biggirl.mvp.gank.video;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.model.android.ResultsBeanX;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.util.PackagingDataUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Video列表数据}
 * Time:2017/4/23 07:08
 */
public class VideoAdapter
        extends BaseRvAdapter<ResultsBeanX>
{
    private Context mContext;

    public VideoAdapter(Context context, List<ResultsBeanX> list) {
        super(list);
        mContext = context;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, ResultsBeanX bean) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            String     who        = bean.getWho();
            String name = who == null
                          ? "Smartisan"
                          : who;

            viewHolder.mTvVideoName.setText(name);

            String time = bean.getCreatedAt();
            viewHolder.mTvVideoTime.setText(time.substring(0, time.lastIndexOf("T")));
            viewHolder.mTvVideoType.setText(bean.getType());
            viewHolder.mTvVideoDes.setText(bean.getDesc());
            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemClickListener) {
                    FavoriteWebBean data     = PackagingDataUtil.objectToFavorite(bean);
                    int             position = viewHolder.getAdapterPosition();
                    ((OnRvItemClickListener) mContext).showDetail(data, (long) position);
                }
            });
        }
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
