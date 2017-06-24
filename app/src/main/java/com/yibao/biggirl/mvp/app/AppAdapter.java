package com.yibao.biggirl.mvp.app;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
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
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
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
        extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{


    private Context              mContext;
    private List<AndroidAndGirl> mList;
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


    public AppAdapter(Context context, List<AndroidAndGirl> list) {
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


            ResultsBean girlData = item.getGirlData()
                                       .get(position);
            ResultsBeanX androidData = item.getAndroidData()
                                           .get(position);

            Glide.with(mContext)
                 .load(girlData.getUrl())
                 .asBitmap()
                 .placeholder(R.mipmap.xuan)
                 .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                 .into(viewHolder.mIvIcon);
            viewHolder.mIvIcon.setOnClickListener(view -> TopBigPicDialogFragment.newInstance(
                    girlData.getUrl())
                                                                                 .show(((AppCompatActivity) mContext).getSupportFragmentManager(),
                                                                                       "dialog_big_girl"));


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
                    if (mContext instanceof OnRvItemClickListener) {
                        FavoriteBean bean = PackagingDataUtil.objectToFavorite(androidData);
                        ((OnRvItemClickListener) mContext).showDetail(bean, bean.getId());
                    }
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

    public void clear() {
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


    public void AddHeader(List<AndroidAndGirl> list) {
        mList.addAll(list);
        notifyDataSetChanged();


    }

    public void AddFooter(List<AndroidAndGirl> list) {
        mList.addAll(list);


    }

    public void changeMoreStatus(int status) {
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
}
