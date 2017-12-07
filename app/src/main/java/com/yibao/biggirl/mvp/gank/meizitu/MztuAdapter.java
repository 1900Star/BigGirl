package com.yibao.biggirl.mvp.gank.meizitu;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvITutemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.girls.Girl;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作者：Stran on 2017/3/29 06:11
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class MztuAdapter
        extends BaseRvAdapter<Girl>


{
    private Context mContext;

    public MztuAdapter(Context context, List<Girl> list) {
        super(list);
        LogUtil.d("**************  AdapterSize  " + list.size());
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
    protected void bindView(RecyclerView.ViewHolder holder, Girl girl) {
        holder.getAdapterPosition();
        if (holder instanceof ViewHolder) {
            String url = girl.getUrl();
            ViewHolder viewHolder = (ViewHolder) holder;
            ImageUitl.loadPicHolder(mContext, url, viewHolder.mGrilImageView);

            holder.itemView.setOnClickListener(view -> {

                boolean b = mContext.getClass().getName().toString().equals(Constants.CLASS_MAIN);
                if (mContext instanceof OnRvITutemClickListener && b) {
                    LogUtil.d(" BBBBBBBB ***  " + girl.getLink());
                    LogUtil.d("  ClassName  ****" + mContext.getClass().getName().toString());
                    ((OnRvITutemClickListener) mContext).openMeiziList(girl.getLink());
                } else {
                    LogUtil.d("  ClassName Tu ****" + mContext.getClass().getName().toString());
                    ((OnRvItemClickListener) mContext).showBigGirl(holder.getAdapterPosition(), mList);

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
