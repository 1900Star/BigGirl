package com.yibao.biggirl.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.model.girls.ResultsBean;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/28 07:40
 */
public class TestAdapter
        extends RecyclerView.Adapter<TestAdapter.TestViewHolder>
{
    private List<ResultsBean> mList;
    private Context mContext;

    public TestAdapter(Context context,List<ResultsBean> list) {
        mContext=context;
        mList = list;
    }

    @Override
    public TestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                                  .inflate(R.layout.item_girls, parent, false);

        return new TestViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TestViewHolder holder, int position) {

        String url = mList.get(position)
                          .getUrl();
        Glide.with(mContext)
             .load(mList.get(position)
                        .getUrl())
             .asBitmap()
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(holder.mGrilImageView);


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    static class TestViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.gril_image_view)
        ImageView mGrilImageView;

        TestViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        mGrilImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtil.d("Test   ===================    4444444");
            }
        });
        }

    }
}
