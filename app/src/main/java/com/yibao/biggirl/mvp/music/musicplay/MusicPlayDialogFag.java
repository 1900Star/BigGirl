package com.yibao.biggirl.mvp.music.musicplay;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.jakewharton.rxbinding2.view.RxView;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.MyAnimatorUpdateListener;
import com.yibao.biggirl.base.listener.SeekBarChangeListtener;
import com.yibao.biggirl.model.greendao.MusicInfoDao;
import com.yibao.biggirl.model.music.MusicDialogInfo;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.model.music.MusicStatusBean;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.music.musiclist.MusicListActivity;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.ColorUtil;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.RxBus;
import com.yibao.biggirl.util.SharePrefrencesUtil;
import com.yibao.biggirl.util.StringUtil;
import com.yibao.biggirl.view.CircleImageView;
import com.yibao.biggirl.view.music.LyricsView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.yibao.biggirl.util.StringUtil.getSongName;

/**
 * Des：${音乐播放界面}
 * Time:2017/5/30 13:27
 * @author Stran
 */
public class MusicPlayDialogFag
        extends DialogFragment
        implements View.OnClickListener

{


    private View root;
    private ImageView mTitlebarDown;
    private TextView mSongName;
    private TextView mArtistName;
    private ImageView mTitlebarPlayList;
    private TextView mStartTime;
    private TextView mEndTime;
    private RelativeLayout mRotateRl;
    private CircleImageView mPlayingSongAlbum;
    private ImageView mMusicPlayerMode;
    private ImageView mMusicPlayerPre;
    private ImageView mMusicPlay;
    private ImageView mMusicPlayerNext;
    private ImageView mIvMusicFavorite;
    private AudioPlayService.AudioBinder audioBinder;
    private CompositeDisposable disposables;
    private ObjectAnimator mAnimator;
    private MyAnimatorUpdateListener mAnimatorListener;
    private boolean isFavorite;
    private int mProgress = 0;
    private Disposable mSubscribe;
    private SeekBar mSbProgress;
    private SeekBar mSbVolume;
    private AudioManager mAudioManager;
    private int mDuration;
    private RxBus mBus;
    private String mAlbumUrl;
    private MusicInfo mMusicInfo;
    private MusicInfoDao mInfoDao;
    private ImageView mIvLyrSwitch;
    boolean isShowLyrics = false;
    private LyricsView mLyricsView;
    private Disposable mDisposableLyr;
    private VolumeReceiver mVolumeReceiver;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioBinder = MusicListActivity.getAudioBinder();
        mBus = MyApplication.getIntstance()
                .bus();
        disposables = new CompositeDisposable();
        mInfoDao = MyApplication.getIntstance()
                .getDaoSession()
                .getMusicInfoDao();
        registerVolumeReceiver();     //注册音量监听广播
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSongInfo();
        checkCurrentIsFavorite();


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void checkCurrentIsFavorite() {
        List<MusicInfo> list = mInfoDao.queryBuilder()
                .where(MusicInfoDao.Properties.Title.eq(mMusicInfo.getTitle()))
                .build()
                .list();
        if (list.size() == 0) {
            mIvMusicFavorite.setImageResource(R.drawable.music_favorite_selector);
            isFavorite = false;
        } else {

            mIvMusicFavorite.setImageResource(R.mipmap.favorite_yes);
            isFavorite = true;
        }
    }

    private void initSongInfo() {
        MusicDialogInfo info = getArguments().getParcelable("info");
        mMusicInfo = info.getInfo();

        mSongName.setText(StringUtil.getSongName(mMusicInfo.getTitle()));
        mArtistName.setText(mMusicInfo.getArtist());

        String url = StringUtil.getAlbulm(mMusicInfo.getAlbumId())
                .toString();
        setAlbulm(url);
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
        if (audioBinder.isPlaying()) {
            initAnimation();
            updatePlayBtnStatus();
            startUpdateProgress();
        }
        startUpdateProgress();
        //设置播放模式图片
//        int mode = mPreferences.getMusicMode("play_mode", MODE_PRIVATE);
        int mode = SharePrefrencesUtil.getMusicMode(getActivity());
        updatePlayModeImage(mode);
        //音乐设置
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSbVolume.setMax(maxVolume);
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateVolume(volume);
    }

    private void updateVolume(int volume) {
        mSbVolume.setProgress(volume);
        //更新音量值  flag 0 默认不显示系统控制栏  1 显示系统音量控制
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volume, 0);


    }


    /**
     * Rxbus接收歌曲时时的进度 和 时间，并更新UI
     */
    private void startUpdateProgress() {
        setSongDuration();
        if (mSubscribe == null) {
            mSubscribe = Observable.interval(0, 2800, TimeUnit.MICROSECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> {
                        mProgress = audioBinder.getProgress();
                        updataProgress(mProgress);
                    });
        }

    }


    private void setSongDuration() {
        //获取并记录总时长
        mDuration = audioBinder.getDuration();
        //设置进度条的总进度
        mSbProgress.setMax(mDuration);
        //        //设置歌曲总时长
        mEndTime.setText(StringUtil.parseDuration(mDuration));
    }

    private void updataProgress(int progress) {
        //时间进度
        mStartTime.setText(StringUtil.parseDuration(progress));
        //时时播放进度
        mSbProgress.setProgress(progress);
        //歌曲总时长递减
        mEndTime.setText(StringUtil.parseDuration(mDuration - progress));
    }


    //接收service中更新数据,
    private void initRxBusData() {
        disposables.add(mBus.toObserverable(MusicInfo.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::perpareMusic));
        //   type 用来判断触发消息的源头，0 表示从 MusicPlayDialogFag发出，
        // 1 表示从通知栏的音乐控制面板发出(Services中的广播)。
        disposables.add(mBus.toObserverable(MusicStatusBean.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::refreshBtnAndAnim));


    }

    private void refreshBtnAndAnim(MusicStatusBean bean) {

        switch (bean.getType()) {
            case 0:
                if (bean.isPlay()) {
                    audioBinder.pause();
                    mAnimator.pause();
                } else {
                    audioBinder.start();
                    mAnimator.resume();
                }
                updatePlayBtnStatus();
                break;
            case 2:
                dismiss();
                break;
            default:
                break;
        }
    }

    //设置歌曲名和歌手名
    private void perpareMusic(MusicInfo info) {
        LogUtil.d("Song Url " + info.getSongUrl());
        mMusicInfo = info;
        checkCurrentIsFavorite();
        initAnimation();
        //更新音乐标题
        mSongName.setText(getSongName(info.getTitle()));
        //更新歌手名称
        mArtistName.setText(info.getArtist());
        //        设置专辑图片
        mAlbumUrl = StringUtil.getAlbulm(info.getAlbumId())
                .toString();
        setAlbulm(mAlbumUrl);
        //设置歌曲时长
        setSongDuration();
        //        更新播放状态按钮
        updatePlayBtnStatus();


    }


    private void setAlbulm(String url) {
        Glide.with(this)
                .load(url)
                .asBitmap()
                .into(mPlayingSongAlbum);
    }


    private void switchPlayState() {

        if (audioBinder.isPlaying()) {
            //当前播放  暂停
            audioBinder.pause();
            mAnimator.pause();
            MyApplication.getIntstance()
                    .bus()
                    .post(new MusicStatusBean(0, true));
        } else {
            //当前暂停  播放
            audioBinder.start();
            initAnimation();
            MyApplication.getIntstance()
                    .bus()
                    .post(new MusicStatusBean(0, false));
        }


        //更新播放状态按钮
        updatePlayBtnStatus();


    }

    //根据当前播放状态设置图片
    private void updatePlayBtnStatus() {
        if (audioBinder.isPlaying()) {
            //正在播放    设置为暂停
            mMusicPlay.setImageResource(R.drawable.btn_playing_pause);
        } else {
            mMusicPlay.setImageResource(R.drawable.btn_playing_play);
        }
    }


    /**
     * 音乐的播放模式 全部循环-单曲循环-随机播放
     */
    private void switchPlayMode() {
        //获取当前的播放模式
        int playMode = audioBinder.getPalyMode();
        //根据当前播放模式进行切换
        switch (playMode) {
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
        //根据当前模式,更新播放模式图片
        updatePlayModeImage(audioBinder.getPalyMode());
    }


    /**
     * 更新播放模式图片
     *
     * @param playMode
     */
    private void updatePlayModeImage(int playMode) {
        switch (playMode) {
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_down:
                dismiss();
                break;

            //显示专辑大图
            case R.id.playing_song_album:
                break;
            //TODO
            //显示歌词
            case R.id.iv_lyrics_switch:
                showLyrics();
                break;

            //切换播放模式
            case R.id.music_player_mode:
                switchPlayMode();
                break;
            //上一曲
            case R.id.music_player_pre:
                audioBinder.playPre();
                break;
            //播放
            case R.id.music_play:
                switchPlayState();
                break;
            //下一曲
            case R.id.music_player_next:
                audioBinder.playNext();
                break;
            //收藏
            case R.id.iv_favorite_music:
                favoritMusic();
                break;
            default:
                break;
        }

    }


    //    显示歌词

    private void showLyrics() {

        if (isShowLyrics) {
            mIvLyrSwitch.setBackgroundResource(R.drawable.music_lrc_close);
            AnimationDrawable animation = (AnimationDrawable) mIvLyrSwitch.getBackground();
            animation.start();
//            mDisposableLyr.dispose();
//            mLyricsView.setVisibility(View.INVISIBLE);
            isShowLyrics = false;
        } else {
            mIvLyrSwitch.setBackgroundResource(R.drawable.music_lrc_open);
            AnimationDrawable animation = (AnimationDrawable) mIvLyrSwitch.getBackground();
            animation.start();

            //              初始化歌词
//            mLyricsView.setLrcFile(mMusicInfo.getSongUrl());
            LogUtil.d("SongUrl : " + mMusicInfo.getSongUrl());
//            initLyrics();

//            mLyricsView.setVisibility(View.VISIBLE);
            isShowLyrics = true;
        }

    }

    //          初始化歌词
    private void initLyrics() {
        mLyricsView.rollText(audioBinder.getProgress(), audioBinder.getDuration());
        if (mDisposableLyr == null) {
            mDisposableLyr = Observable.interval(0, 5000, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> initLyrics());

        }


    }

    private void favoritMusic() {
        if (isFavorite) {
            mInfoDao.delete(mMusicInfo);
            mIvMusicFavorite.setImageResource(R.drawable.music_favorite_selector);
            isFavorite = false;

        } else {
            String time = StringUtil.getCurrentTime();
            LogUtil.d("currentTime  : " + time);
            mMusicInfo.setTime(time);

            mInfoDao.insert(mMusicInfo);
            mIvMusicFavorite.setImageResource(R.mipmap.favorite_yes);
            isFavorite = true;

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
        mIvLyrSwitch.setOnClickListener(this);
        mTitlebarDown.setOnClickListener(this);
        mMusicPlayerMode.setOnClickListener(this);
        mMusicPlayerPre.setOnClickListener(this);
        mMusicPlay.setOnClickListener(this);
        mMusicPlayerNext.setOnClickListener(this);
        mIvMusicFavorite.setOnClickListener(this);
        mSbProgress.setOnSeekBarChangeListener(new SeekBarListener());
        mSbVolume.setOnSeekBarChangeListener(new SeekBarListener());
        rxViewClick();


    }

    private void rxViewClick() {

        RxView.clicks(mTitlebarPlayList)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    List<MusicInfo> list = mInfoDao.queryBuilder()
                            .build()
                            .list();
                    MusicBottomSheetDialog.newInstance()
                            .getBottomDialog(getActivity(), list);

                });

        RxView.clicks(mPlayingSongAlbum)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> TopBigPicDialogFragment.newInstance(mAlbumUrl)
                        .show(getFragmentManager(), "album"));

    }

    private void initView() {
        mTitlebarDown = root.findViewById(R.id.titlebar_down);
        mSongName = root.findViewById(R.id.play_song_name);
        mArtistName = root.findViewById(R.id.play_artist_name);
        mTitlebarPlayList = root.findViewById(R.id.titlebar_play_list);
        mStartTime = root.findViewById(R.id.start_time);
        mEndTime = root.findViewById(R.id.end_time);
        mIvLyrSwitch = root.findViewById(R.id.iv_lyrics_switch);
        mRotateRl = root.findViewById(R.id.rotate_rl);
        mPlayingSongAlbum = root.findViewById(R.id.playing_song_album);
        mMusicPlayerMode = root.findViewById(R.id.music_player_mode);
        mMusicPlayerPre = root.findViewById(R.id.music_player_pre);
        mMusicPlay = root.findViewById(R.id.music_play);
        mMusicPlayerNext = root.findViewById(R.id.music_player_next);
        mIvMusicFavorite = root.findViewById(R.id.iv_favorite_music);
        mSbProgress = root.findViewById(R.id.sb_progress);
        mSbVolume = root.findViewById(R.id.sb_volume);
        mLyricsView = root.findViewById(R.id.tv_lyrics);
    }


    public static MusicPlayDialogFag newInstance(MusicDialogInfo info) {
        MusicPlayDialogFag fragment = new MusicPlayDialogFag();
        Bundle bundle = new Bundle();
        bundle.putParcelable("info", info);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mAnimator != null && mAnimatorListener != null && disposables != null && mSubscribe != null) {
            //            mAnimatorListener.pause();
            //            mAnimator.cancel();
            mSubscribe.dispose();
            disposables.clear();
        }
        getActivity().unregisterReceiver(mVolumeReceiver);
    }

    private class SeekBarListener
            extends SeekBarChangeListtener

    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

            switch (seekBar.getId()) {
                case R.id.sb_progress:
                    if (!b) {
                        return;
                    }
                    //更新音乐播放进度
                    audioBinder.seekTo(progress);
                    //更新音乐进度数值
                    updataProgress(progress);
                    break;
                case R.id.sb_volume:    //更新音乐  SeekBar
                    updateVolume(progress);
                    break;
                default:
                    break;
            }
        }
    }

    private void registerVolumeReceiver() {
        mVolumeReceiver = new VolumeReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        getActivity().registerReceiver(mVolumeReceiver, filter);
    }

    //音量监听广播
    private class VolumeReceiver
            extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果系统音量发生变化就更新Seekbar
            if (intent.getAction()
                    .equals("android.media.VOLUME_CHANGED_ACTION")) {
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int currVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                mSbVolume.setProgress(currVolume);
            }
        }
    }

}
