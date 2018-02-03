package com.yibao.biggirl.mvp.gank.meizitu;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.girl.Girl;
import com.yibao.biggirl.mvp.gank.girl.GirlActivity;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.PackagingDataUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 作者：Stran on 2017/3/29 06:11
 * 描述：${MeizituAdapter}
 * 邮箱：strangermy@outlook.com
 */
public class MztuAdapter
        extends BaseRvAdapter<Girl>


{
    private Context mContext;
    private int mType;

    /**
     * @param context
     * @param list
     * @param type    1表示 MeizituRecyclerActivity , 2表示 DuotuPicActivty , 3 表示 SisanActivity
     */
    public  MztuAdapter(Context context, List<Girl> list, int type) {
        super(list);
        mContext = context;
        mType = type;
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
        List<String> list = PackagingDataUtil.objectToList(mList);

        if (holder instanceof ViewHolder) {
            String url = girl.getUrl();
            ViewHolder viewHolder = (ViewHolder) holder;
            ImageUitl.loadPicHolder(mContext, url, viewHolder.mGrilImageView);
            holder.itemView.setOnClickListener(view -> {
                if (TextUtils.isEmpty(girl.getLink())) {
                    if (list != null && list.size() != 0) {
                        Intent intent = new Intent(mContext, GirlActivity.class);
                        intent.putStringArrayListExtra("girlList", (ArrayList<String>) list);
                        intent.putExtra("position", holder.getAdapterPosition());
                        mContext.startActivity(intent);

                    }
                } else {
                    if (mContext instanceof OnRvItemClickListener) {

                        ((OnRvItemClickListener) mContext).showBigGirl(0, null, mType, girl.getLink());
                    }
                }
            });


            holder.itemView.setOnLongClickListener(view -> {
                if (mContext instanceof OnRvItemLongClickListener) {
                    ((OnRvItemLongClickListener) mContext).onLongTouchPreview(url);
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
