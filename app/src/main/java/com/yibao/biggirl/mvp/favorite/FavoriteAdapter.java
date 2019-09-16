package com.yibao.biggirl.mvp.favorite;

import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvItemSlideListener;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${适配Video列表数据}
 * Time:2017/4/23 07:08
 *
 * @author Stran
 */
public class FavoriteAdapter
        extends BaseRvAdapter<FavoriteWebBean> {


    private Context mContext;

    FavoriteAdapter(Context context, List<FavoriteWebBean> list) {
        super(list);
        mContext = context;
    }

    @Override
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
            //侧滑删除的监听
            viewHolder.mDeleteItem.setOnClickListener(view -> {
                int position = holder.getAdapterPosition();
                if (mContext instanceof OnRvItemSlideListener) {
                    ((OnRvItemSlideListener) mContext).deleteFavorite(bean.getId());
                }
                mList.remove(position);
                FavoriteAdapter.this.notifyItemRemoved(position);
            });
            //Item点击的监听
            viewHolder.mFavoriteItem.setOnClickListener(view -> {
                Intent intent = new Intent(mContext, WebActivity.class);
                intent.putExtra("id", bean.getId());
                LogUtil.d("跳转 ID :" + bean.getId());
                intent.putExtra("favoriteBean", bean);
                mContext.startActivity(intent);

//                if (mContext instanceof OnRvItemClickListener) {
//                    ((OnRvItemClickListener) mContext).showWebDetail(bean, bean.getId());
//                }
            });


        }
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

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
