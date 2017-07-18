package com.yibao.biggirl.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.io.IOException;


/**
 * Created by ThinkPad on 2016/7/27.
 */
public class AudioPlayService
        extends Service
{

    private MediaPlayer mPlayer;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        playMusic();
    }

    private void playMusic() {
        if (mPlayer == null) {

            mPlayer = new MediaPlayer();
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            String path = Environment.getExternalStorageDirectory()
                                     .getAbsolutePath() + "/Music/Song/1773377275_AZ.mp3";
            Uri u = Uri.parse(path);
            try {
                mPlayer.setDataSource(this, u);
                mPlayer.prepare();
                mPlayer.setLooping(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
            mPlayer.start();


        } else if (mPlayer.isPlaying()) {
            mPlayer.pause();
        } else {
            mPlayer.start();
        }


    }
}
