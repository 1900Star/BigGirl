package com.yibao.biggirl.mvp.music.musiclist;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.yibao.biggirl.R;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.StringUtil;

/**
 * Author：Sid
 * Des：${音乐通知栏}
 * Time:2017/9/3 02:12
 */
public class MusicNoification {


    private static RemoteViews remoteView;

    public static Notification getNotification(Context context, boolean isPlaying, MusicInfo info)
    {

        remoteView = getRemotViews(context,
                                   isPlaying,
                                   StringUtil.getAlbulm(info.getAlbumId()),
                                   info.getTitle(),
                                   info.getArtist());


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setSmallIcon(R.mipmap.biggirl)
                                           .setOngoing(true)
                                           .setContent(remoteView)
                                           .build();
        notification.bigContentView = remoteView;
        return notification;
    }

    private static RemoteViews getRemotViews(Context context,
                                             boolean isPlaying,
                                             Uri uri,
                                             String songName,
                                             String artistName)
    {
        remoteView = new RemoteViews(context.getPackageName(), R.layout.music_notify);
        remoteView.setTextViewText(R.id.widget_title, songName);
        remoteView.setTextViewText(R.id.widget_artist, artistName);
        remoteView.setImageViewUri(R.id.widget_album, uri);     //通知栏的专辑图片
        remoteView.setImageViewResource(R.id.widget_close, R.mipmap.notifycation_close);
        remoteView.setImageViewResource(R.id.widget_prev, R.mipmap.notifycation_prev);
        remoteView.setImageViewResource(R.id.widget_next, R.mipmap.notifycation_next);
        updatePlayBtn(isPlaying); //通知栏的监听
        remoteViewListenr(context, remoteView);
        return remoteView;

    }

    private static void updatePlayBtn(boolean isPlaying) {
        if (isPlaying) {
            remoteView.setImageViewResource(R.id.widget_play, R.mipmap.notifycation_pause);
        } else {
            remoteView.setImageViewResource(R.id.widget_play, R.mipmap.notifycation_play);

        }
    }

    private static void remoteViewListenr(Context context, RemoteViews remoteViews) {
        Intent intent = new Intent(AudioPlayService.ACTION_MUSIC);

        //Root
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.ROOT);
        PendingIntent intentRoot = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.ROOT,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.notice, intentRoot);

        //Pre
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.PREV);
        PendingIntent intentPrev = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.PREV,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_prev, intentPrev);

        //Play
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.PLAY);
        PendingIntent intentPlay = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.PLAY,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_play, intentPlay);
        //Next
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.NEXT);
        PendingIntent intentNext = PendingIntent.getBroadcast(context,
                                                              AudioPlayService.NEXT,
                                                              intent,
                                                              PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_next, intentNext);
        //Close
        intent.putExtra(AudioPlayService.BUTTON_ID, AudioPlayService.CLOSE);
        PendingIntent intentClose = PendingIntent.getBroadcast(context,
                                                               AudioPlayService.CLOSE,
                                                               intent,
                                                               PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.widget_close, intentClose);


    }


}
