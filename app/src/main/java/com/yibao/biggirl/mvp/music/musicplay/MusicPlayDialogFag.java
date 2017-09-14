package com.yibao.biggirl.mvp.music.musicplay;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
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
import com.yibao.biggirl.util.StringUtil;
import com.yibao.biggirl.view.CircleImageView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.yibao.biggirl.util.StringUtil.getSongName;

/**
 * Author：Sid
 * Des：${音乐播放界面}
 * Time:2017/5/30 13:27
 */
public class MusicPlayDialogFag
        extends DialogFragment
        implements View.OnClickListener

{


    private View                         root;
    private ImageView                    mTitlebarDown;
    private TextView                     mSongName;
    private TextView                     mArtistName;
    private ImageView                    mTitlebarPlayList;
    private TextView                     mStartTime;
    private TextView                     mEndTime;
    private RelativeLayout               mRotateRl;
    private CircleImageView              mPlayingSongAlbum;
    private ImageView                    mMusicPlayerMode;
    private ImageView                    mMusicPlayerPre;
    private ImageView                    mMusicPlay;
    private ImageView                    mMusicPlayerNext;
    private ImageView                    mIvMusicFavorite;
    private AudioPlayService.AudioBinder audioBinder;
    private CompositeDisposable          disposables;
    private ObjectAnimator               mAnimator;
    private MyAnimatorUpdateListener     mAnimatorListener;
    private boolean                      isFavorite;
    private int mProgress = 0;
    private Disposable         mSubscribe;
    private SeekBar            mSbProgress;
    private SeekBar            mSbVolume;
    private AudioManager       mAudioManager;
    private int                mDuration;
    private RxBus              mBus;
    private String             mAlbumUrl;
    private MusicInfo          mMusicInfo;
    private MusicInfoDao       mInfoDao;
    private MusicPlayPresenter mPresenter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = new MusicPlayPresenter();
        audioBinder = MusicListActivity.getAudioBinder();
        mBus = MyApplication.getIntstance()
                            .bus();
        disposables = new CompositeDisposable();
        mInfoDao = MyApplication.getIntstance()
                                .getDaoSession()
                                .getMusicInfoDao();
        registerReceiver();     //注册监听的音量广播
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initSongInfo();
        checkCurrentIsFavorite();


    }

    private boolean checkCurrentIsFavorite() {
        List<MusicInfo> list = mInfoDao.queryBuilder()
                                       .where(MusicInfoDao.Properties.Title.eq(mMusicInfo.getTitle()))
                                       .build()
                                       .list();
        if (list.size() == 0) {
            LogUtil.d("Null");
            mIvMusicFavorite.setImageResource(R.drawable.music_favorite_selector);
            isFavorite = false;
        } else {

            mIvMusicFavorite.setImageResource(R.mipmap.favorite_yes);
            isFavorite = true;
        }
        return isFavorite;
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

    //不停的更新进度 和 时间
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
        mStartTime.setText(StringUtil.parseDuration(progress));    //时间进度
        mSbProgress.setProgress(progress);  //时时播放进度
        mEndTime.setText(StringUtil.parseDuration(mDuration - progress));       //歌曲总时长递减
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
        mMusicInfo = info;
        //只要歌曲发生变化 ，就将isFavorite标记重置为false, 保证收藏正常。
        //        isFavorite = false;

        checkCurrentIsFavorite();
        initAnimation();
        //更新音乐标题
        mSongName.setText(getSongName(info.getTitle()));
        //更新歌手名称
        mArtistName.setText(info.getArtist());
        //歌曲Url
        //设置专辑图片
        //        mAlbumUrl = RandomUtil.getRandomUrl();
        mAlbumUrl = StringUtil.getAlbulm(info.getAlbumId())
                              .toString();
        setAlbulm(mAlbumUrl);
        //设置歌曲时长
        setSongDuration();
        //更新播放状态按钮
        updatePlayBtnStatus();


    }

    private void setAlbulm(String url) {
        Glide.with(this)
             .load(url)
             .asBitmap()
//             .placeholder(R.mipmap.playing_cover_lp)
             //             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_down:
                dismiss();
                break;
            case R.id.titlebar_play_list:       //快速列表

                List<MusicInfo> lists = mInfoDao.queryBuilder()
                                                .build()
                                                .list();
                for (int i = 0; i < lists.size(); i++) {
                    LogUtil.d(" SystemTime   :" + lists.get(i)
                                                       .getTime());

                }
                MusicBottomSheetDialog.newInstance()
                                      .getBottomDialog(getActivity(), lists);

                break;
            case R.id.playing_song_album:      //显示专辑大图
                TopBigPicDialogFragment.newInstance(mAlbumUrl)
                                       .show(getFragmentManager(), "album");
                break;
            case R.id.music_player_mode:    //切换播放模式
                switchPlayMode();
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
            //TODO
            case R.id.iv_favorite_music:   //收藏
                favoritMusic();
                break;
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
        mTitlebarDown.setOnClickListener(this);
        mTitlebarPlayList.setOnClickListener(this);
        mPlayingSongAlbum.setOnClickListener(this);
        mMusicPlayerMode.setOnClickListener(this);
        mMusicPlayerPre.setOnClickListener(this);
        mMusicPlay.setOnClickListener(this);
        mMusicPlayerNext.setOnClickListener(this);
        mIvMusicFavorite.setOnClickListener(this);
        mSbProgress.setOnSeekBarChangeListener(new SeekBarListener());
        mSbVolume.setOnSeekBarChangeListener(new SeekBarListener());


    }

    private void initView() {
        mTitlebarDown = (ImageView) root.findViewById(R.id.titlebar_down);
        mSongName = (TextView) root.findViewById(R.id.play_song_name);
        mArtistName = (TextView) root.findViewById(R.id.play_artist_name);
        mTitlebarPlayList = (ImageView) root.findViewById(R.id.titlebar_play_list);
        mStartTime = (TextView) root.findViewById(R.id.start_time);
        mEndTime = (TextView) root.findViewById(R.id.end_time);
        mRotateRl = (RelativeLayout) root.findViewById(R.id.rotate_rl);
        mPlayingSongAlbum = (CircleImageView) root.findViewById(R.id.playing_song_album);
        mMusicPlayerMode = (ImageView) root.findViewById(R.id.music_player_mode);
        mMusicPlayerPre = (ImageView) root.findViewById(R.id.music_player_pre);
        mMusicPlay = (ImageView) root.findViewById(R.id.music_play);
        mMusicPlayerNext = (ImageView) root.findViewById(R.id.music_player_next);
        mIvMusicFavorite = (ImageView) root.findViewById(R.id.iv_favorite_music);
        mSbProgress = (SeekBar) root.findViewById(R.id.sb_progress);
        mSbVolume = (SeekBar) root.findViewById(R.id.sb_volume);
    }


    public static MusicPlayDialogFag newInstance(MusicDialogInfo info)
    {
        MusicPlayDialogFag fragment = new MusicPlayDialogFag();
        Bundle             bundle   = new Bundle();
        bundle.putParcelable("info", info);
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
        if (mAnimator != null && mAnimatorListener != null && disposables != null && mSubscribe != null) {
            //            mAnimatorListener.pause();
            //            mAnimator.cancel();
            mSubscribe.dispose();
            disposables.clear();
        }
    }

    private class SeekBarListener
            extends SeekBarChangeListtener

    {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
            super.onProgressChanged(seekBar, progress, b);

            switch (seekBar.getId()) {
                case R.id.sb_progress:
                    if (!b) { return; }
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

    private void registerReceiver() {
        VolumeReceiver mVolumeReceiver = new VolumeReceiver();
        IntentFilter   filter          = new IntentFilter();
        filter.addAction("android.media.VOLUME_CHANGED_ACTION");
        getActivity().registerReceiver(mVolumeReceiver, filter);
    }

    //音量监听广播
    private class VolumeReceiver
            extends BroadcastReceiver
    {
        @Override
        public void onReceive(Context context, Intent intent) {
            //如果系统音量发生变化就更新Seekbar
            if (intent.getAction()
                      .equals("android.media.VOLUME_CHANGED_ACTION"))
            {
                AudioManager mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                int          currVolume    = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);// 当前的媒体音量
                mSbVolume.setProgress(currVolume);
            }
        }
    }

}
