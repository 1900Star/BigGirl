package com.yibao.biggirl.mvp.music.musicplay;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author：Sid
 * Des：${快速列表的Adapter}
 * Time:2017/8/22 14:31
 */
public class BottomSheetAdapter
        extends BaseRvAdapter<MusicInfo>
        implements SectionIndexer
{

    private List<MusicInfo> mList;
    private Context         mContext;

    public BottomSheetAdapter(Context context, List<MusicInfo> list) {
        super(list);
        this.mContext = context;
        this.mList = list;

    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, MusicInfo musicItem) {
        if (holder instanceof MusicHolder) {
            MusicHolder musicHolder = (MusicHolder) holder;
            musicHolder.mMusicName.setText(StringUtil.getSongName(musicItem.getTitle()));
            musicHolder.mMusicSinger.setText(musicItem.getArtist());
            musicHolder.mFavoriteTime.setText(musicItem.getTime());
            int position = musicHolder.getAdapterPosition();
            musicHolder.mRootBottomSheet.setOnClickListener(view -> playMusic(position));
        }
    }

    private void playMusic(int position) {
        Intent intent = new Intent();
        intent.setClass(mContext, AudioPlayService.class);
        intent.putParcelableArrayListExtra("musicItem", (ArrayList<? extends Parcelable>) mList);
        intent.putExtra("position", position);
        AudioServiceConnection connection = new AudioServiceConnection();
        mContext.bindService(intent, connection, Service.BIND_AUTO_CREATE);
        mContext.startService(intent);

    }

    @Override
    protected RecyclerView.ViewHolder getViewHolder(View view) {
        return new MusicHolder(view);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.bottom_sheet_music_item;
    }


    @Override
    public Object[] getSections() {
        return new Object[0];
    }

    @Override
    public int getPositionForSection(int i) {
        return 0;
    }

    @Override
    public int getSectionForPosition(int i) {
        return 0;
    }

    static class MusicHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.favorite_music_name)
        TextView     mMusicName;
        @BindView(R.id.favorite_artist_name)
        TextView     mMusicSinger;
        @BindView(R.id.favorite_time)
        TextView     mFavoriteTime;
        @BindView(R.id.bottom_list_itme_clear)
        ImageView    mBottomListItmeClear;
        @BindView(R.id.root_bottom_sheet)
        LinearLayout mRootBottomSheet;

        MusicHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
