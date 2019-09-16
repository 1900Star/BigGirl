package com.yibao.biggirl.mvp.gank.all;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.app.ResultsBeanX;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
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
public class AllAdapter
        extends BaseRvAdapter<ResultsBeanX> {


    private Context mContext;

    public AllAdapter(Context context, List<ResultsBeanX> list) {
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
                    .into(viewHolder.mImageAll);
            viewHolder.mTvAll.setLinkTextColor(mContext.getTheme().getResources().getColor(R.color.colorAccent));
            viewHolder.mTvAll.setText(Html.fromHtml("<a href=\""
                    + bean.getUrl() + "\">"
                    + bean.getDesc() + "</a>"
                    + "[" + bean.getWho() + "]"));
            viewHolder.mTvAll.setMovementMethod(LinkMovementMethod.getInstance());


            viewHolder.mImageAll.setOnLongClickListener(view -> {
                if (mContext instanceof OnRvItemLongClickListener) {
                    ((OnRvItemLongClickListener) mContext).onLongTouchPreview(url);
                }
                return true;
            });
            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemClickListener) {
                    if (mContext instanceof OnRvItemClickListener) {
                        FavoriteWebBean data = PackagingDataUtil.objectToFavorite(bean);
                        ((OnRvItemClickListener) mContext).showWebDetail(data, data.getId());
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
        return R.layout.item_all_frag;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_all)
        TextView mTvAll;
        @BindView(R.id.image_all)
        ImageView mImageAll;

        MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
