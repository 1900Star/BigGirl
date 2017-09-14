package com.yibao.biggirl.mvp.music.musiclist;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseIntArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnMusicRvItemClickListener;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.yibao.biggirl.util.StringUtil.getPinYin;

/**
 * Created by Stran on 2016/11/5.
 */

public class MusicRvAdapter
        extends RecyclerView.Adapter<MusicRvAdapter.MusicViewHolder>
        implements SectionIndexer


{
    private String TAG = "MusicRvAdapter";
    private Context         mContext;
    private List<MusicInfo> mList;
    private SparseIntArray intArrayA = new SparseIntArray();
    private SparseIntArray intArrayB = new SparseIntArray();
    private String mName;


    public MusicRvAdapter(Context context, List<MusicInfo> list) {
        this.mContext = context;
        this.mList = list;
    }


    @Override
    public MusicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                  .inflate(R.layout.item_music_rv, parent, false);

        return new MusicViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MusicViewHolder holder, int position) {
        MusicInfo info = mList.get(position);
        holder.mSongArtistName.setText(info.getArtist());
        Glide.with(mContext)
             .load(StringUtil.getAlbulm(info.getAlbumId()))
             .placeholder(R.mipmap.playing_cover_lp)
             .into(holder.mSongAlbum);

        mName = StringUtil.getSongName(info.getTitle());
        holder.mSongName.setText(mName);


        String firstTv = getPinYin(mName);
        holder.mTvStickyView.setText(firstTv);
        if (position == 0) {
            holder.mTvStickyView.setVisibility(View.VISIBLE);
        } else if (position != 0 && firstTv.equals(getPinYin(mList.get(position - 1)
                                                                  .getTitle())))
        {
            holder.mTvStickyView.setVisibility(View.GONE);

        } else {
            holder.mTvStickyView.setVisibility(View.VISIBLE);
        }
        holder.mLlMusicItem.setOnClickListener(view -> {
            if (mContext instanceof OnMusicRvItemClickListener) {
                ((OnMusicRvItemClickListener) mContext).startMusicService(position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public String[] getSections() {
        //清空intArray
        intArrayA.clear();
        intArrayB.clear();
        //定义集合存放sections
        ArrayList<String> sections = new ArrayList<>();
        //遍历联系人集合添加section
        for (int i = 0; i < mList.size(); i++) {
            //获取联系人首字母
            String firstC = getPinYin(mList.get(i)
                                           .getTitle());
            if (!sections.contains(firstC)) {
                sections.add(firstC);
                //添加section和position对应的值
                intArrayA.put(sections.size() - 1, i);
            }
            intArrayB.put(i, sections.size() - 1);
        }
        return sections.toArray(new String[sections.size()]);

    }

    @Override
    public int getPositionForSection(int position) {

        return intArrayA.get(position);
    }

    @Override
    public int getSectionForPosition(int i) {

        return intArrayB.get(i);
    }


    static class MusicViewHolder
            extends RecyclerView.ViewHolder
    {
        @BindView(R.id.item_sticky_view)
        TextView     mTvStickyView;
        @BindView(R.id.song_album)
        ImageView    mSongAlbum;
        @BindView(R.id.song_name)
        TextView     mSongName;
        @BindView(R.id.song_artist_name)
        TextView     mSongArtistName;
        @BindView(R.id.ll_music_item)
        LinearLayout mLlMusicItem;

        MusicViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
