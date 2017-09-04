package com.yibao.biggirl.mvp.music;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yibao.biggirl.R;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.util.StringUtil;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/3 14:46
 */
public class MusicListAdapters
        extends BaseAdapter
{
    private List<MusicInfo> mList;
    private Context         mContext;

    public MusicListAdapters(List<MusicInfo> list, Context context) {
        mList = list;
        mContext = context;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = View.inflate(mContext, R.layout.music_list_item, null);
            holder.songAlbum = (ImageView) convertView.findViewById(R.id.song_album);
            holder.songName = (TextView) convertView.findViewById(R.id.song_name);
            holder.songArtistName = (TextView) convertView.findViewById(R.id.song_artist_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MusicInfo info = mList.get(position);
        holder.songName.setText(StringUtil.getSongName(info.getTitle()));
        holder.songArtistName.setText(info.getArtist());

        //        Uri url = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"),
        //                                              info.getAlbumId());

        //        Uri url = Uri.parse("content://media/external/audio/media/" + info.getId() + "/albumart");
        Glide.with(mContext)
             .load(StringUtil.getAlbulm(info.getAlbumId()))
             .placeholder(R.mipmap.playing_cover_lp)
             .into(holder.songAlbum);
        return convertView;
    }

    static class ViewHolder {
        TextView  songName;
        TextView  songArtistName;
        ImageView songAlbum;


    }


}
