package com.yibao.biggirl.mvp.music.musiclist;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnMusicListItemClickListener;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.StringUtil;

import java.util.ArrayList;

/**
 * 作者：Stran on 2017/3/23 03:31
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public class MusicPagerAdapter
        extends android.support.v4.view.PagerAdapter {
    private Context mContext;
    private ArrayList<MusicInfo> mList;
    private int mcurrentPostition;

    MusicPagerAdapter(Context context, ArrayList<MusicInfo> list, int currentPosition) {
        this.mContext = context;
        this.mList = list;
        this.mcurrentPostition = currentPosition;
    }


    @Override
    public int getCount() {

        return mList != null ? mList.size() : 0;

    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_music_pager, container, false);
        MusicInfo info = mList.get(position);
        initView(info, view);
        view.setTag(position);

        initListener(view, info);
        container.addView(view);
        return view;
    }
    private void initListener(View view, MusicInfo info) {
        view.setOnClickListener(view1 -> {
            LogUtil.d("PagerAdapter  = =========== ");
            if (mContext instanceof OnMusicListItemClickListener) {
                ((OnMusicListItemClickListener) mContext).onpenMusicPlayDialogFag();
            }
        });
    }

    public void setMusicInfo(MusicInfo musicInfo,int position) {
    }

    private void initView(MusicInfo musicInfo, View view) {
        ImageView albulm = view.findViewById(R.id.iv_pager_albulm);
        TextView songName = view.findViewById(R.id.tv_pager_song_name);
        TextView artName = view.findViewById(R.id.tv_pager_art_name);
        Uri albumUri = StringUtil.getAlbulm(musicInfo.getAlbumId());
        Glide.with(mContext)
                .load(albumUri.toString())
                .asBitmap()
                .error(R.drawable.dropdown_menu_noalbumcover)
                .into(albulm);
        songName.setText(musicInfo.getTitle());
        artName.setText(musicInfo.getArtist());

    }


}
