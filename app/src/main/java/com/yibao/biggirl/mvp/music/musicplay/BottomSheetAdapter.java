package com.yibao.biggirl.mvp.music.musicplay;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseRvAdapter;
import com.yibao.biggirl.model.music.BottomSheetStatus;
import com.yibao.biggirl.model.music.MusicBean;
import com.yibao.biggirl.util.RxBus;
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
        extends BaseRvAdapter<MusicBean>
        implements SectionIndexer {


    public BottomSheetAdapter(List<MusicBean> list) {
        super(list);

    }

    @Override
    protected void bindView(RecyclerView.ViewHolder holder, MusicBean musicItem) {
        if (holder instanceof MusicHolder) {
            MusicHolder musicHolder = (MusicHolder) holder;
            musicHolder.mMusicName.setText(StringUtil.getSongName(musicItem.getTitle()));
            musicHolder.mMusicSinger.setText(musicItem.getArtist());
            musicHolder.mFavoriteTime.setText(musicItem.getTime());
            int position = musicHolder.getAdapterPosition();
            RxBus bus = MyApplication.getIntstance()
                    .bus();
//            MusicBottomSheetDialog页面接收,用于播放收藏列表中点击Position的音乐
            musicHolder.mRootBottomSheet.setOnClickListener(view -> bus.post(new BottomSheetStatus(
                    position)));
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
            extends RecyclerView.ViewHolder {
        @BindView(R.id.favorite_music_name)
        TextView mMusicName;
        @BindView(R.id.favorite_artist_name)
        TextView mMusicSinger;
        @BindView(R.id.favorite_time)
        TextView mFavoriteTime;
        @BindView(R.id.bottom_list_itme_clear)
        ImageView mBottomListItmeClear;
        @BindView(R.id.root_bottom_sheet)
        LinearLayout mRootBottomSheet;

        MusicHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }


}
