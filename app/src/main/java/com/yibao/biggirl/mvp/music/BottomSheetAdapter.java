package com.yibao.biggirl.mvp.music;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.model.music.MusicItem;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.StringUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${快速列表的Adapter}
 * Time:2017/8/22 14:31
 */
public class BottomSheetAdapter
        extends BaseRvAdapter<MusicItem>
{
    public BottomSheetAdapter(List<MusicItem> list) {
        super(list);
    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, MusicItem musicItem) {
        if (holder instanceof MusicHolder) {
            MusicHolder musicHolder = (MusicHolder) holder;
            musicHolder.mMusicName.setText(StringUtil.getSongName(musicItem.getTitle()));
            musicHolder.mMusicSinger.setText(musicItem.getArtist());
            musicHolder.mBottomListItmeClose.setOnClickListener(view -> LogUtil.d(
                    "*************  删除当前歌曲"));

        }
    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new MusicHolder(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bottom_sheet_music_item;
    }

    static class MusicHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.music_name)
        TextView  mMusicName;
        @BindView(R.id.music_singer)
        TextView  mMusicSinger;
        @BindView(R.id.bottom_list_itme_close)
        ImageView mBottomListItmeClose;

        MusicHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
