package com.yibao.biggirl.mvp.music;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
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
import com.yibao.biggirl.mvp.dialogfragment.BottomSheetListDialog;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.ColorUtil;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.RandomUtil;
import com.yibao.biggirl.view.CircleImageView;
import com.yibao.biggirl.view.ProgressBtn;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.yibao.biggirl.util.StringUtil.getSongName;
import static com.yibao.biggirl.util.StringUtil.parseDuration;

/**
 * Author：Sid
 * Des：${音乐播放界面}
 * Time:2017/5/30 13:27
 */
public class MusicPlayDialogFag
        extends DialogFragment
        implements View.OnClickListener

{

    private View                                   root;
    private android.widget.ImageView               mTitlebarDown;
    private android.widget.TextView                mSongName;
    private android.widget.TextView                mArtistName;
    private android.widget.ImageView               mTitlebarPlayList;
    private android.widget.TextView                mStartTime;
    private com.yibao.biggirl.view.ProgressBtn     mProgressBtn;
    private android.widget.TextView                mEndTime;
    private android.widget.RelativeLayout          mRotateRl;
    private com.yibao.biggirl.view.CircleImageView mPlayingSongAlbum;
    private android.widget.ImageView               mMusicPlayerMode;
    private android.widget.ImageView               mMusicPlayerPre;
    private android.widget.ImageView               mMusicPlay;
    private android.widget.ImageView               mMusicPlayerNext;
    private android.widget.ImageView               mMusicPlayerRandom;
    private AudioPlayService.AudioBinder           audioBinder;
    private CompositeDisposable                    disposables;
    private ObjectAnimator                         mAnimator;
    private MyAnimatorUpdateListener               mAnimatorListener;
    private int                                    duration;
    private boolean isRandomMode = false;
    private String               mUrl;
    private ArrayList<MusicItem> mList;
    private int mProgress = 0;
    private Disposable mSubscribe;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().getWindow()
                     .setStatusBarColor(Color.WHITE);
        audioBinder = MusicListActivity.getAudioBinder();
        disposables = new CompositeDisposable();


    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mSongName.setText(getArguments().getString("songName"));
        mArtistName.setText(getArguments().getString("artistName"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.activity_music_play, null);
        initView();
        initRxBusData();
        initData();
        initListener();

        return DialogUtil.getDialogFag(getActivity(), root);
    }


    private void initData() {
        mList = getArguments().getParcelableArrayList("musicItem");
        if (audioBinder.isPlaying()) {
            initAnimation();
            updatePlayStateBtn();
            startUpdateProgress();
        }


    }

    //更新进度 和 时间
    private void startUpdateProgress() {
        duration = audioBinder.getDuration();
        String time = parseDuration(duration);
        mProgressBtn.setMax(duration);
        mEndTime.setText(time);

        if (mSubscribe == null) {

            mSubscribe = Observable.interval(0, 2800, TimeUnit.MICROSECONDS)
                                   .subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread())
                                   .subscribe(aLong -> {
                                       mProgress = audioBinder.getProgress();
                                       String progressBefor = parseDuration(mProgress);
                                       mStartTime.setText(progressBefor);    //时间进度
                                       mProgressBtn.setProgress(mProgress);  //时时播放进度
                                   });
        }


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_down:
                dismiss();
                break;
            case R.id.titlebar_play_list:       //快速列表
                BottomSheetListDialog.newInstance()
                                     .getBottomDialog(getActivity(), mList);

                break;
            case R.id.playing_song_album:
                TopBigPicDialogFragment.newInstance(mUrl)
                                       .show(getFragmentManager(), "album");
                break;
            case R.id.music_player_mode:
                switchPlayMode();       //切换播放模式
                break;
            case R.id.music_player_pre:     //上一曲
                audioBinder.playPre();
                break;
            case R.id.music_play:      //播放
                switchPlayState();
                break;
            case R.id.music_player_next:       //下一曲
                audioBinder.playNext();
                break;
            case R.id.music_player_random:   //随机切换
                randomMode();
                break;


        }

    }


    private void randomMode() {
        if (isRandomMode) {
            mMusicPlayerRandom.setImageResource(R.drawable.btn_playing_shuffle_on);
            audioBinder.setPalyMode(AudioPlayService.PLAY_MODE_RANDOM);
            isRandomMode = false;

        } else {
            mMusicPlayerRandom.setImageResource(R.drawable.btn_playing_shuffle_off);
            audioBinder.setPalyMode(AudioPlayService.PLAY_MODE_ALL);
            isRandomMode = true;

        }
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
        //更新音乐标题
        String songName = musicItem.getTitle();
        songName = getSongName(songName);
        mSongName.setText(songName);
        //更新歌手名称
        mArtistName.setText(musicItem.getArtist());
        LogUtil.d("BBBBBB  === " + songName + "      ********     " + musicItem.getArtist());
        mUrl = RandomUtil.getRandomUrl();
        Glide.with(this)
             .load(mUrl)
             .asBitmap()
             .placeholder(R.mipmap.xuan)
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(mPlayingSongAlbum);
        //获取并记录总时长
        duration = audioBinder.getDuration();
        String time = parseDuration(duration);
        mEndTime.setText(time);
        //设置进度条的总进度
        mProgressBtn.setMax(duration);
        //更新播放状态按钮
        updatePlayStateBtn();

    }


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

    private void updatePlayStateBtn() {
        //根据当前播放状态设置图片
        if (audioBinder.isPlaying()) {

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
    private void initAnimation() {
        mRotateRl.setBackgroundColor(ColorUtil.transparentColor);
        if (mAnimator == null || mAnimatorListener == null) {
            mAnimator = AnimationUtil.getRotation(mRotateRl);
            mAnimatorListener = new MyAnimatorUpdateListener(mAnimator);
            mAnimator.start();
            mMusicPlay.setImageResource(R.drawable.btn_playing_pause);
        }
        mAnimator.resume();

    }
    private void initListener() {
        mTitlebarDown.setOnClickListener(this);
        mTitlebarPlayList.setOnClickListener(this);
        mPlayingSongAlbum.setOnClickListener(this);
        mMusicPlayerMode.setOnClickListener(this);
        mMusicPlayerPre.setOnClickListener(this);
        mMusicPlay.setOnClickListener(this);
        mMusicPlayerNext.setOnClickListener(this);
        mMusicPlayerRandom.setOnClickListener(this);


    }

    private void initView() {
        mTitlebarDown = (ImageView) root.findViewById(R.id.titlebar_down);
        mSongName = (TextView) root.findViewById(R.id.play_song_name);
        mArtistName = (TextView) root.findViewById(R.id.play_artist_name);
        mTitlebarPlayList = (ImageView) root.findViewById(R.id.titlebar_play_list);
        mStartTime = (TextView) root.findViewById(R.id.start_time);
        mProgressBtn = (ProgressBtn) root.findViewById(R.id.progress_time);
        mEndTime = (TextView) root.findViewById(R.id.end_time);
        mRotateRl = (RelativeLayout) root.findViewById(R.id.rotate_rl);
        mPlayingSongAlbum = (CircleImageView) root.findViewById(R.id.playing_song_album);
        mMusicPlayerMode = (ImageView) root.findViewById(R.id.music_player_mode);
        mMusicPlayerPre = (ImageView) root.findViewById(R.id.music_player_pre);
        mMusicPlay = (ImageView) root.findViewById(R.id.music_play);
        mMusicPlayerNext = (ImageView) root.findViewById(R.id.music_player_next);
        mMusicPlayerRandom = (ImageView) root.findViewById(R.id.music_player_random);


    }

    /**
     *
     * @param musicItems    快速列表数据
     * @param songName      设置歌名
     * @param artistName    设置歌手
     */
    public static MusicPlayDialogFag newInstance(ArrayList<MusicItem> musicItems,
                                                 String songName,
                                                 String artistName)
    {
        MusicPlayDialogFag fragment = new MusicPlayDialogFag();
        Bundle             bundle   = new Bundle();
        bundle.putParcelableArrayList("musicItem", musicItems);
        bundle.putString("songName", songName);
        bundle.putString("artistName", artistName);

        fragment.setArguments(bundle);//把参数设置给自己

        return fragment;
    }


    @Override
    public void onPause() {
        super.onPause();
        //        disposables.clear();
        //        mAnimator.pause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //        mAnimatorListener.pause();
        mAnimator.cancel();
        mSubscribe.dispose();
        disposables.clear();
    }
}
