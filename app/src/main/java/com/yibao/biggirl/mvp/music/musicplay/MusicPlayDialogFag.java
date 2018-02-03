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
import android.os.PowerManager;
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
import com.yibao.biggirl.model.greendao.MusicBeanDao;
import com.yibao.biggirl.model.music.MusicBean;
import com.yibao.biggirl.model.music.MusicDialogInfo;
import com.yibao.biggirl.model.music.MusicStatusBean;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.music.musiclist.MusicListActivity;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.ColorUtil;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.RxBus;
import com.yibao.biggirl.util.SharePrefrencesUtil;
import com.yibao.biggirl.util.StringUtil;
import com.yibao.biggirl.util.ToastUtil;
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
 *
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
    private MusicBean mCurrenMusicInfo;
    private MusicBeanDao mInfoDao;
    private ImageView mIvLyrSwitch;
    boolean isShowLyrics = false;
    private LyricsView mLyricsView;
    private Disposable mDisposableLyrics;
    private VolumeReceiver mVolumeReceiver;
    private ImageView mIvScreenSunSwitch;
    private boolean isScreenAlwaysOn;
    private PowerManager.WakeLock mWakeLock;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        audioBinder = MusicListActivity.getAudioBinder();
        mBus = MyApplication.getIntstance()
                .bus();
        disposables = new CompositeDisposable();
        mInfoDao = MyApplication.getIntstance()
                .getDaoSession()
                .getMusicBeanDao();
        //注册音量监听广播
        registerVolumeReceiver();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PowerManager powerManager = (PowerManager) getActivity().getSystemService(Context.POWER_SERVICE);
        mWakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Music Lock");
        initSongInfo();
        checkCurrentIsFavorite();


    }

    private void checkCurrentIsFavorite() {
        List<MusicBean> list = mInfoDao.queryBuilder()
                .where(MusicBeanDao.Properties.Title.eq(mCurrenMusicInfo.getTitle()))
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
        mCurrenMusicInfo = info.getInfo();
        mSongName.setText(StringUtil.getSongName(mCurrenMusicInfo.getTitle()));
        mArtistName.setText(mCurrenMusicInfo.getArtist());

        String url = StringUtil.getAlbulm(mCurrenMusicInfo.getAlbumId())
                .toString();
        setAlbulm(url);
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.music_play_dialogfag, null);
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
        int mode = SharePrefrencesUtil.getMusicMode(getActivity());
        updatePlayModeImage(mode);
        //音乐设置
        mAudioManager = (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        int maxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mSbVolume.setMax(maxVolume);
        int volume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        updateMusicVolume(volume);
    }

    private void updateMusicVolume(int volume) {
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
                        updataMusicProgress(mProgress);
                    });
        }

    }


    private void setSongDuration() {
        //获取并记录总时长
        mDuration = audioBinder.getDuration();
        //设置进度条的总进度
        mSbProgress.setMax(mDuration);
        //  设置歌曲总时长
        mEndTime.setText(StringUtil.parseDuration(mDuration));
    }

    private void updataMusicProgress(int progress) {
        //时间进度
        mStartTime.setText(StringUtil.parseDuration(progress));
        //时时播放进度
        mSbProgress.setProgress(progress);
        //歌曲总时长递减
        mEndTime.setText(StringUtil.parseDuration(mDuration - progress));
    }


    //接收service中的数据,更新UI。
    private void initRxBusData() {
        disposables.add(mBus.toObserverable(MusicBean.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::perpareMusic));
        //   position 用来判断触发消息的源头，0 表示从 MusicPlayDialogFag发出，
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
    private void perpareMusic(MusicBean info) {
        mCurrenMusicInfo = info;
        checkCurrentIsFavorite();
        initAnimation();
        mSongName.setText(getSongName(info.getTitle()));
        mArtistName.setText(info.getArtist());
        mAlbumUrl = StringUtil.getAlbulm(info.getAlbumId())
                .toString();
        setAlbulm(mAlbumUrl);
        setSongDuration();
        updatePlayBtnStatus();
//        初始化歌词
        mLyricsView.setLrcFile(info.getTitle(), info.getArtist());

    }


    private void setAlbulm(String url) {
        Glide.with(MyApplication.getIntstance())
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
            mMusicPlay.setImageResource(R.drawable.btn_playing_pause_selector);
        } else {
            mMusicPlay.setImageResource(R.drawable.btn_playing_play_selector);
        }
    }


    /**
     * 音乐的播放模式 全部循环-单曲循环-随机播放
     */
    private void switchPlayMode() {
        //获取当前的播放模式
        int playMode = audioBinder.getPalyMode();
        //根据当前播放模式进行其它模式切换
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
            case R.id.playing_song_album:
                break;
            //TODO
            case R.id.iv_lyrics_switch:
                showLyrics();
                break;
            case R.id.iv_secreen_sun_switch:
                screenAlwaysOnSwitch();
                break;
            case R.id.music_player_mode:
                switchPlayMode();
                break;
            case R.id.music_player_pre:
                audioBinder.playPre();
                break;
            case R.id.music_play:
                switchPlayState();
                break;
            case R.id.music_player_next:
                audioBinder.playNext();
                break;
            case R.id.iv_favorite_music:
                favoritMusic();
                break;
            default:
                break;
        }

    }

    /**
     * 打开歌词时，可以保持屏幕常亮
     * 屏幕常亮设置为30分钟
     */
    private void screenAlwaysOnSwitch() {
        if (isScreenAlwaysOn) {
            mWakeLock.release();
            mIvScreenSunSwitch.setImageResource(R.drawable.sun_always_of_selector);
            ToastUtil.showScreenOf(getActivity());
            isScreenAlwaysOn = false;
        } else {
            long screenTime = 30 * 60 * 1000L;
            mWakeLock.acquire(screenTime);
            mIvScreenSunSwitch.setImageResource(R.drawable.sun_always_on_selector);
            ToastUtil.showScreenOn(getActivity());
            isScreenAlwaysOn = true;
        }
    }


    //    显示歌词

    private void showLyrics() {

        if (isShowLyrics) {
//            mDisposableLyrics.dispose();
            mIvLyrSwitch.setBackgroundResource(R.drawable.music_lrc_close);
            AnimationDrawable animation = (AnimationDrawable) mIvLyrSwitch.getBackground();
            animation.start();
            mIvScreenSunSwitch.setVisibility(View.INVISIBLE);
            mLyricsView.setVisibility(View.INVISIBLE);
            isShowLyrics = false;
        } else {
            mIvLyrSwitch.setBackgroundResource(R.drawable.music_lrc_open);
            AnimationDrawable animation = (AnimationDrawable) mIvLyrSwitch.getBackground();
            animation.start();
            mIvScreenSunSwitch.setVisibility(View.VISIBLE);
            // 开始滚动歌词
            startPlayLyrics();
            mLyricsView.setVisibility(View.VISIBLE);
            isShowLyrics = true;
        }

    }

    private void startPlayLyrics() {
        if (mDisposableLyrics == null) {

            mDisposableLyrics = Observable.interval(100, TimeUnit.MILLISECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aLong -> mLyricsView.rollText(audioBinder.getProgress(), audioBinder.getDuration()));
        }

    }

    private void favoritMusic() {
        if (isFavorite) {
            mInfoDao.delete(mCurrenMusicInfo);
            mIvMusicFavorite.setImageResource(R.drawable.music_favorite_selector);
            isFavorite = false;

        } else {
            String time = StringUtil.getCurrentTime();
            mCurrenMusicInfo.setTime(time);
            mInfoDao.insert(mCurrenMusicInfo);
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
        mIvScreenSunSwitch.setOnClickListener(this);
        rxViewClick();
        mLyricsView.setOnClickListener(view -> showLyrics());
        mPlayingSongAlbum.setOnClickListener(view -> showLyrics());
        mPlayingSongAlbum.setOnLongClickListener(view -> {
            TopBigPicDialogFragment.newInstance(mAlbumUrl)
                    .show(getFragmentManager(), "album");
            return true;
        });

    }

    private void rxViewClick() {

        RxView.clicks(mTitlebarPlayList)
                .throttleFirst(1, TimeUnit.SECONDS)
                .subscribe(o -> {
                    List<MusicBean> list = mInfoDao.queryBuilder()
                            .build()
                            .list();
                    MusicBottomSheetDialog.newInstance()
                            .getBottomDialog(getActivity(), list);

                });


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
        mIvScreenSunSwitch = root.findViewById(R.id.iv_secreen_sun_switch);
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
        boolean allSwitch = mAnimator != null && mAnimatorListener != null && mDisposableLyrics != null && disposables != null && mSubscribe != null;
        if (allSwitch) {
            mAnimatorListener.pause();
            mAnimator.cancel();
            mSubscribe.dispose();
            disposables.clear();
            mDisposableLyrics.dispose();
        }
        getActivity().unregisterReceiver(mVolumeReceiver);
    }

    private class SeekBarListener
            extends SeekBarChangeListtener {
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
                    updataMusicProgress(progress);
                    break;
                case R.id.sb_volume:    //更新音乐  SeekBar
                    updateMusicVolume(progress);
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
