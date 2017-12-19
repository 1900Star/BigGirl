package com.yibao.biggirl.mvp.favorite;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnDeleteItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Video列表数据}
 * Time:2017/4/23 07:08
 */
public class FavoriteAdapter
        extends BaseRvAdapter<FavoriteWebBean> {


    private Context mContext;

    public FavoriteAdapter(Context context, List<FavoriteWebBean> list) {
        super(list);
        mContext = context;
        LogUtil.d("Size  " + list.size());
    }

    //    @Override
    protected void bindView(RecyclerView.ViewHolder holder, FavoriteWebBean bean) {
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;


            Glide.with(mContext)
                    .load(bean.getImagUrl())
                    .asBitmap()
                    .placeholder(R.mipmap.xuan)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .into(viewHolder.mIvFavorite);
            String who = bean.getName();
            String name = who == null
                    ? "Smartisan"
                    : who;

            viewHolder.mTvFavoriteName.setText(name);

            viewHolder.mTvFavoriteTime.setText(bean.getTime());
            viewHolder.mTvFavoriteType.setText(bean.getType());
            viewHolder.mTvFavoriteDes.setText(bean.getDes());
            viewHolder.mDeleteItem.setOnClickListener(view -> { //侧滑删除的监听
                int position = holder.getAdapterPosition();
                if (mContext instanceof OnDeleteItemClickListener) {
                    ((OnDeleteItemClickListener) mContext).deleteFavorite(bean.getId());
                }
                mList.remove(position);
                FavoriteAdapter.this.notifyItemRemoved(position);
            });
            viewHolder.mFavoriteItem.setOnClickListener(view -> { //Item点击的监听

                if (mContext instanceof OnRvItemClickListener) {
                    ((OnRvItemClickListener) mContext).showDetail(bean, bean.getId());
                }
            });


        }
    }

    public void refreshItem() {
        notifyDataSetChanged();
    }

    //对应的ViewHolder
    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    //item布局
    @Override
    protected int getLayoutId() {
        return R.layout.item_favorite_frag;
    }


    static class ViewHolder
            extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_favorite)
        ImageView mIvFavorite;
        @BindView(R.id.tv_favorite_name)
        TextView mTvFavoriteName;
        @BindView(R.id.tv_favorite_type)
        TextView mTvFavoriteType;
        @BindView(R.id.tv_favorite_des)
        TextView mTvFavoriteDes;
        @BindView(R.id.favorite_time)
        TextView mTvFavoriteTime;
        @BindView(R.id.favorite_item)
        RelativeLayout mFavoriteItem;

        @BindView(R.id.delete_item)
        LinearLayout mDeleteItem;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
