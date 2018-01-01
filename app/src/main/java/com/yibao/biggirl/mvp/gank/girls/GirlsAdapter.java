package com.yibao.biggirl.mvp.gank.girls;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
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
        extends BaseRvAdapter<String>


{
    private Context mContext;

    public GirlsAdapter(Context context, List<String> list) {
        super(list);
        mContext = context;
    }


    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new ViewHolder(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_girls;
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, String url) {
        holder.getAdapterPosition();
        if (holder instanceof ViewHolder) {
            ViewHolder viewHolder = (ViewHolder) holder;
            ImageUitl.loadPicHolder(mContext, url, viewHolder.mGrilImageView);

            holder.itemView.setOnClickListener(view -> {
                if (mContext instanceof OnRvItemClickListener) {
                    ((OnRvItemClickListener) mContext).showBigGirl(holder.getAdapterPosition(), mList, 1, null);

                }
            });

            holder.itemView.setOnLongClickListener(view -> {
                if (mContext instanceof OnRvItemLongClickListener) {

                    ((OnRvItemLongClickListener) mContext).showPreview(url);
                }


                return true;
            });
        }


    }


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


}
