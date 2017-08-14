package com.yibao.biggirl.mvp.music;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.MyAnimatorUpdateListener;
import com.yibao.biggirl.model.music.MusicItem;
import com.yibao.biggirl.model.music.MusicStatusBean;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.ColorUtil;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.RandomUtil;
import com.yibao.biggirl.util.StringUtil;
import com.yibao.biggirl.util.ToastUtil;
import com.yibao.biggirl.view.CircleImageView;
import com.yibao.biggirl.view.ProgressBtn;

import java.util.ArrayList;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yibao.biggirl.util.StringUtil.parseDuration;

public class MusicPlayActivity
        extends AppCompatActivity
{

    @BindView(R.id.music_play)
    ImageView      mMusicPlay;
    @BindView(R.id.rotate_rl)
    RelativeLayout mRotateRl;
    @BindView(R.id.titlebar_down)
    ImageView      mTitleBarDissmis;
    @BindView(R.id.play_artist_name)
    TextView       mPlayArtistName;
    @BindView(R.id.play_song_name)
    TextView       mPlaySongName;
    @BindView(R.id.titlebar_play_list)
    ImageView      mTitleBarList;
    @BindView(R.id.start_time)
    TextView       mStartTime;

    @BindView(R.id.end_time)
    TextView mEndTime;

    @BindView(R.id.progress_time)
    ProgressBtn     mProgress;
    @BindView(R.id.music_player_mode)
    ImageView       mMusicPlayerMode;
    @BindView(R.id.music_player_pre)
    ImageView       mMusicPlayerPre;
    @BindView(R.id.music_player_next)
    ImageView       mMusicPlayerNext;
    @BindView(R.id.music_player_random)
    ImageView       mMusicPlayerRandom;
    @BindView(R.id.playing_song_album)
    CircleImageView mSongAlbum;
    private AudioPlayService.AudioBinder audioBinder;
    private ArrayList<MusicItem>         itemList;
    private CompositeDisposable          disposables;
    private MusicItem                    musicItem;
    private int                          duration;
    private ObjectAnimator               mAnimator;
    private MyAnimatorUpdateListener     mAnimatorListener;
    private Unbinder                     mBind;
    private int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_play);
        mBind = ButterKnife.bind(this);
        disposables = new CompositeDisposable();
        initAnimation();
        initRxBusData();
        initData();
        stopAnimation();


    }

    private void stopAnimation() {
        disposables.add(MyApplication.getIntstance()
                                     .bus()
                                     .toObserverable(MusicStatusBean.class)
                                     .subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(musicStatusBean -> {
                                         LogUtil.d("MusicStatusBean  " + musicStatusBean.getStatus());
                                         if (musicStatusBean.getStatus() == 0) {
                                             mAnimator.pause();
                                         } else {
                                             mAnimator.resume();
                                         }
                                     }));
    }

    private void initAnimation() {

        mRotateRl.setBackgroundColor(ColorUtil.transparentColor);

        mAnimator = AnimationUtil.getRotation(mRotateRl);
        mAnimatorListener = new MyAnimatorUpdateListener(mAnimator);
        mAnimator.start();
    }

    //接收service中更新数据,
    private void initRxBusData() {
        disposables.add(MyApplication.getIntstance()
                                     .bus()
                                     .toObserverable(MusicItem.class)
                                     .subscribeOn(Schedulers.io())
                                     .observeOn(AndroidSchedulers.mainThread())
                                     .subscribe(this::perpareMusic));
    }

    //设置歌曲名和歌手名
    private void perpareMusic(MusicItem musicItem) {
        this.musicItem = musicItem;

        //更新音乐标题
        String songName = musicItem.getTitle();
        if (songName.contains("_")) {
            songName = songName.substring(songName.indexOf("_") + 1, songName.length());
        }

        Glide.with(this)
             .load(RandomUtil.getRandomUrl())
             .asBitmap()
             .placeholder(R.mipmap.xuan)
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(mSongAlbum);
        mPlaySongName.setText(songName);
        //更新歌手名称
        mPlayArtistName.setText(musicItem.getArtist());
        //更新进度
        startUpdateProgress();
        //获取并记录总时长
        duration = audioBinder.getDuration();
        String time = StringUtil.parseDuration(duration);
        mEndTime.setText(time);
        //设置进度条的总进度
        mProgress.setMax(duration);
        //更新播放状态按钮
        updatePlayStateBtn();

    }

    private int getRandom() {
        Random random = new Random();
        return random.nextInt(Api.picUrlArr.length) + 1;
    }

    private void startUpdateProgress() {
        int progress = audioBinder.getProgress();
        LogUtil.d("时时进度  :  " + progress);
        String progressBefor = parseDuration(progress);
        LogUtil.d("格式播放时间化后的进度   :  " + progressBefor);
        mStartTime.setText(progressBefor);

        mProgress.setProgress(progress);
    }

    private void updatePlayStateBtn() {
        //获取当前播放状态
        //根据当前播放状态设置图片
        if (audioBinder.isPlaying()) {

            //暂停播放
            //                        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation_rotate);
            //                        animation.setInterpolator(new LinearInterpolator());
            //                        mRotateRl.startAnimation(animation);
            //                        animation.cancel();


            mMusicPlay.setImageResource(R.drawable.btn_playing_pause);


        } else {
            //正在播放

            mMusicPlay.setImageResource(R.drawable.btn_playing_play);
        }
    }


    //切换当前播放模式 全部循环-单曲循环-随机播放
    private void switchPlayMode() {
        //获取当前的播放模式
        int play_mode = audioBinder.getPalyMode();
        //根据当前播放模式进行切换
        switch (play_mode) {
            case AudioPlayService.PLAY_MODE_ALL:
                audioBinder.setPalyMode(AudioPlayService.PLAY_MODE_SINGLE);
                break;
            case AudioPlayService.PLAY_MODE_SINGLE:
                audioBinder.setPalyMode(AudioPlayService.PLAY_MODE_RANDOM);
                break;
            case AudioPlayService.PLAY_MODE_RANDOM:
                audioBinder.setPalyMode(AudioPlayService.PLAY_MODE_ALL);
                break;
            default:
                break;
        }
        //更新播放模式图片
        updatePlayModeImage();
    }

    //更新播放模式图片
    private void updatePlayModeImage() {
        //获取当前的播放模式
        int play_mode = audioBinder.getPalyMode();
        //根据当前模式设置图片
        switch (play_mode) {
            case AudioPlayService.PLAY_MODE_ALL:
                mMusicPlayerMode.setImageResource(R.drawable.audio_playmode_allrepeat_selector);
                break;
            case AudioPlayService.PLAY_MODE_SINGLE:
                mMusicPlayerMode.setImageResource(R.drawable.audio_playmode_single_selector);
                break;
            case AudioPlayService.PLAY_MODE_RANDOM:
                mMusicPlayerMode.setImageResource(R.drawable.audio_playmode_random_selector);
                break;
            default:
                break;
        }
    }

    private void initData() {

        itemList = getIntent().getParcelableArrayListExtra("itemList");
        //背景音乐
        Intent intent = new Intent(getIntent());
        intent.setClass(this, AudioPlayService.class);
        AudioServiceConnection connection = new AudioServiceConnection();
        bindService(intent, connection, Service.BIND_AUTO_CREATE);
        startService(intent);
        if (1 < audioBinder.getDuration()) {

        }
        // 旋转专辑图片

        //        mRotateRl.setBackgroundColor(ColorUtil.transparentColor);
        //        mAnimator = AnimationUtil.getRotation(mRotateRl);
        //        mAnimatorListener = new MyAnimatorUpdateListener(mAnimator);


    }


    //切换当前播放状态
    private void switchPlayState() {

        if (audioBinder.isPlaying()) {
            //当前播放  暂停
            audioBinder.pause();
            mAnimator.pause();
        } else {
            //当前暂停  播放
            audioBinder.start();
            mAnimator.resume();
        }


        //更新播放状态按钮
        updatePlayStateBtn();


    }

    @OnClick({R.id.music_play,
              R.id.music_player_mode,
              R.id.music_player_pre,
              R.id.music_player_next,
              R.id.music_player_random,
              R.id.titlebar_down,
              R.id.titlebar_play_list})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.titlebar_down:
                ToastUtil.showLong(this, "AAAA");
                break;
            case R.id.titlebar_play_list:
                ToastUtil.showLong(this, "BBBB");
                break;
            case R.id.music_player_mode:
                switchPlayMode();
                break;
            case R.id.music_player_pre:
                audioBinder.playPre();
                mAnimator.start();
                break;
            case R.id.music_play:      //播放
                switchPlayState();
                break;
            case R.id.music_player_next:
                audioBinder.playNext();
                mAnimator.start();
                break;
            case R.id.music_player_random:

                break;


        }
    }

    private class AudioServiceConnection
            implements ServiceConnection
    {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            audioBinder = (AudioPlayService.AudioBinder) service;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAnimatorListener.pause();
        disposables.clear();
        mBind.unbind();
    }
}
