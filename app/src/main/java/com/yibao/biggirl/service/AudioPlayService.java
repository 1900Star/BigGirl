package com.yibao.biggirl.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.model.music.MusicStatusBean;
import com.yibao.biggirl.util.LogUtil;

import java.util.ArrayList;
import java.util.Random;


public class AudioPlayService
        extends Service
{

    private        MediaPlayer mediaPlayer;
    private        AudioBinder mAudioBinder;
    private static int         PLAY_MODE;
    public static final int    PLAY_MODE_ALL    = 0;
    public static final int    PLAY_MODE_SINGLE = 1;
    public static final int    PLAY_MODE_RANDOM = 2;
    //音乐通知栏
    public static final int    ROOT             = 0;
    public static final int    PREV             = 1;
    public static final int    PLAY             = 2;
    public static final int    NEXT             = 3;
    public static final int    CLOSE            = 4;
    public final static String BUTTON_ID        = "ButtonId";
    public static final String ACTION_MUSIC     = "MUSIC";

    String path = Environment.getExternalStorageDirectory()
                             .getAbsolutePath() + "/Music/Song/1773377275_AZ.mp3";
    private int position = -2;
    //    private ArrayList<MusicItem>  mMusicItem;
    private ArrayList<MusicInfo>  mMusicItem;
    private SharedPreferences     sp;
    private MusicBroacastReceiver mReceiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mAudioBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAudioBinder = new AudioBinder();
        initBroadcast();
        sp = getSharedPreferences("config", MODE_PRIVATE);
        //初始化播放模式
        PLAY_MODE = sp.getInt("play_mode", 0);

    }

    private void initBroadcast() {
        mReceiver = new MusicBroacastReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_MUSIC);

        registerReceiver(mReceiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        mMusicItem = intent.getParcelableArrayListExtra("musicItem");
        int enterPosition = intent.getIntExtra("position", -1);
        if (enterPosition != position && enterPosition != -1) {
            position = enterPosition;
            //执行播放
            mAudioBinder.play();
        } else if (enterPosition != -1 && enterPosition == position) {
            //通知播放界面更新
            MyApplication.getIntstance()
                         .bus()
                         .post(mMusicItem.get(position));
        }
        return START_NOT_STICKY;
    }


    public class AudioBinder
            extends Binder
            implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener

    {
        private void play() {

            if (mediaPlayer != null) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(AudioPlayService.this,
                                             Uri.parse(mMusicItem.get(position)
                                                                 .getUrl()));

            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
        }


        //准备完成回调
        @Override
        public void onPrepared(MediaPlayer mediaPlayer) {
            //开启播放
            mediaPlayer.start();
            //通知播放界面更新
            MyApplication.getIntstance()
                         .bus()
                         .post(mMusicItem.get(position));
        }

        //获取当前播放进度

        public int getProgress() {
            return mediaPlayer.getCurrentPosition();
        }

        //获取音乐总时长
        public int getDuration() {
            return mediaPlayer.getDuration();
        }

        //音乐播放完成监听
        @Override
        public void onCompletion(MediaPlayer mediaPlayer) {
            //自动播放下一首歌曲
            autoPlayNext();
        }


        //自动播放下一曲
        private void autoPlayNext() {
            switch (PLAY_MODE) {
                case PLAY_MODE_ALL:
                    position = (position + 1) % mMusicItem.size();
                    break;
                case PLAY_MODE_SINGLE:

                    break;
                case PLAY_MODE_RANDOM:
                    position = new Random().nextInt(mMusicItem.size());
                    break;
                default:
                    break;
            }
            play();
        }

        //获取当前的播放模式
        public int getPalyMode() {
            return PLAY_MODE;
        }

        //设置播放模式
        public void setPalyMode(int playmode) {
            PLAY_MODE = playmode;
            //保存播放模式
            sp.edit()
              .putInt("play_mode", PLAY_MODE)
              .commit();
        }

        //手动播放上一曲
        public void playPre() {
            switch (PLAY_MODE) {
                case PLAY_MODE_RANDOM:
                    position = new Random().nextInt(mMusicItem.size());
                    break;
                default:
                    if (position == 0) {
                        position = mMusicItem.size() - 1;
                    } else {
                        position--;
                    }

                    break;
            }
            play();
        }

        //手动播放下一曲
        public void playNext() {
            switch (PLAY_MODE) {
                case PLAY_MODE_RANDOM:
                    position = new Random().nextInt(mMusicItem.size());
                    break;
                default:
                    position = (position + 1) % mMusicItem.size();
                    break;
            }
            play();
        }

        //true 当前正在播放
        public boolean isPlaying() {
            return mediaPlayer.isPlaying();
        }

        public void start() {
            mediaPlayer.start();
        }

        //暂停播放
        public void pause() {
            mediaPlayer.pause();
        }

        //跳转到指定位置进行播放
        public void seekTo(int progress) {
            mediaPlayer.seekTo(progress);
        }

        //播放小列表中当前位置的歌曲
        public void playPosition(int position) {
            AudioPlayService.this.position = position;
            play();
        }

        private void playStatus(int type) {
            switch (type) {
                case 0:
                    if (mAudioBinder.isPlaying()) {
                        mAudioBinder.pause();
                        MyApplication.getIntstance()
                                     .bus()
                                     .post(new MusicStatusBean(type, true));
                        LogUtil.d("PAUSE");
                    } else {
                        mAudioBinder.start();
                        MyApplication.getIntstance()
                                     .bus()
                                     .post(new MusicStatusBean(type, false));
                        LogUtil.d("PLAY");
                    }
                    break;
                case 1:
                    if (mAudioBinder.isPlaying()) {
                        MyApplication.getIntstance()
                                     .bus()
                                     .post(new MusicStatusBean(type, true));
                        LogUtil.d("PAUSE");
                    } else {
                        MyApplication.getIntstance()
                                     .bus()
                                     .post(new MusicStatusBean(type, false));
                        LogUtil.d("PLAY");
                    }

                    break;
                default:
                    break;
            }

        }

    }

    //控制通知栏的广播
    class MusicBroacastReceiver
            extends BroadcastReceiver

    {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_MUSIC)) {
                int id = intent.getIntExtra(BUTTON_ID, 0);
                switch (id) {
                    case ROOT:
                        LogUtil.d("Root");
                        mAudioBinder.playStatus(1);
                        break;
                    case CLOSE:
                        LogUtil.d("CLOSE");
                        onDestroy();
                        break;
                    case PREV:
                        mAudioBinder.playPre();
                        LogUtil.d("PREV");
                        break;
                    case PLAY:
                        mAudioBinder.playStatus(0);
                        break;
                    case NEXT:
                        mAudioBinder.playNext();
                        LogUtil.d("NEXT");
                        break;


                    default:
                        break;
                }
            }
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        if (mReceiver != null) {
            unregisterReceiver(mReceiver);
        }
//        MyApplication.getIntstance()
//                     .bus()
//                     .post(new MusicStatusBean(2, false));
        stopSelf();
    }
}
