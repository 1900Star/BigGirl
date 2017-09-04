package com.yibao.biggirl.mvp.music;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.music.MusicItem;
import com.yibao.biggirl.network.Api;

/**
 * Created by ThinkPad on 2016/7/27.
 */
public class MusicListAdapter
        extends CursorAdapter
{
    public MusicListAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View       view   = View.inflate(context, R.layout.music_list_item, null);
        ViewHolder holder = new ViewHolder(view);
        view.setTag(holder);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ViewHolder holder = (ViewHolder) view.getTag();
        String     url      = Api.picUrlArr[cursor.getPosition()];
        Glide.with(context)
             .load(url)
             .asBitmap()
             .placeholder(R.mipmap.xuan)
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(holder.singerPhoto);

        //获取当前条目bean
        MusicItem audioItem = MusicItem.getAudioItem(cursor);
        String    title     = audioItem.getTitle();
        if (title.contains("_")) {
            title = title.substring(title.indexOf("_") + 1, title.length());
        }
        holder.songName.setText(title);
        holder.songArtistName.setText(audioItem.getArtist());
        holder.singerPhoto.setOnLongClickListener(view1 -> {

            if (context instanceof OnRvItemLongClickListener) {
                ((OnRvItemLongClickListener) context).showPreview(url);
            }
            return true;
        });
    }

    class ViewHolder {
        TextView songName, songArtistName;
        ImageView singerPhoto;

        public ViewHolder(View view) {

            songName = (TextView) view.findViewById(R.id.song_name);
            singerPhoto = (ImageView) view.findViewById(R.id.song_album);
            songArtistName = (TextView) view.findViewById(R.id.song_artist_name);
        }
    }
}
