package com.yibao.biggirl.mvp.dialogfragment;

import android.animation.ObjectAnimator;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
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
import com.yibao.biggirl.mvp.music.XianduCategory;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.ColorUtil;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.RandomUtil;
import com.yibao.biggirl.view.CircleImageView;
import com.yibao.biggirl.view.ProgressBtn;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

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
    private android.widget.TextView                mPlaySongName;
    private android.widget.TextView                mPlayArtistName;
    private android.widget.ImageView               mTitlebarPlayList;
    private android.widget.TextView                mStartTime;
    private com.yibao.biggirl.view.ProgressBtn     mProgressTime;
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
    private MusicItem                              musicItem;
    private Subscription                           subscription;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        disposables = new CompositeDisposable();
    }

    public static MusicPlayDialogFag newInstance(ArrayList<MusicItem> musicItem, int position) {
        MusicPlayDialogFag fragment = new MusicPlayDialogFag();
        Bundle             bundle   = new Bundle();
        bundle.putParcelableArrayList("musicItem", musicItem);
        bundle.putInt("position", position);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        root = inflater.inflate(R.layout.activity_music_play, null);
        initView();
        initAnimation();
        initRxBusData();
        initData();
        initListener();

        return DialogUtil.getDialogFag(getActivity(), root);
    }


    private void initData() {
        //        Bundle bundle = getArguments();

        Intent intent = new Intent();
        intent.putExtra("bundle", getArguments()); //将数据传递给AudioPlayService
        intent.setClass(getActivity(), AudioPlayService.class);
        AudioServiceConnection connection = new AudioServiceConnection();
        getActivity().bindService(intent, connection, Service.BIND_AUTO_CREATE);
        getActivity().startService(intent);
    }

    private void initAnimation() {

        mRotateRl.setBackgroundColor(ColorUtil.transparentColor);

        mAnimator = AnimationUtil.getRotation(mRotateRl);
        mAnimatorListener = new MyAnimatorUpdateListener(mAnimator);
        mAnimator.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.titlebar_down:
                dismiss();
                break;
            case R.id.titlebar_play_list:
                LogUtil.d("VVVVVVV");
                break;
            case R.id.playing_song_album:
                LogUtil.d("CCCCCCCCC");
                break;
            case R.id.music_player_mode:
//                switchPlayMode();
                lazyFetchData();
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


    protected void lazyFetchData() {

        String host = "http://gank.io/xiandu";
        Observable.just(host)
                  .subscribeOn(Schedulers.io())
                  .map(s -> {
                      List<XianduCategory> list = new ArrayList<>();
                      Document doc = Jsoup.connect(host)
                                          .timeout(10000)
                                          .get();

                      Element cate = doc.select("div#xiandu_cat")
                                        .first();

                      Elements links = cate.select("a[href]");

                      for (Element element : links) {
                          XianduCategory xiandu = new XianduCategory();
                          xiandu.setName(element.text());
                          xiandu.setUrl(element.attr("abs:href"));
                          list.add(xiandu);
                      }

                      if (list.size() > 0) {
                          list.get(0)
                              .setUrl(host + "/wow");
                      }

                      return list;
                  })
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribe(new Observer<List<XianduCategory>>() {
                      @Override
                      public void onSubscribe(@io.reactivex.annotations.NonNull Disposable d) {

                      }

                      @Override
                      public void onNext(@io.reactivex.annotations.NonNull List<XianduCategory> list) {
                          LogUtil.d("网页数据 ：   " + list.size());
                          for (int i = 0; i < list.size(); i++) {
                          LogUtil.d("打印 数据 ：" + list.get(i)
                                                    .getUrl() + "   AAAA" + list.get(i)
                                                                               .getName());

                          }

                      }

                      @Override
                      public void onError(@io.reactivex.annotations.NonNull Throwable e) {

                      }

                      @Override
                      public void onComplete() {

                      }
                  });


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
             .into(mPlayingSongAlbum);
        mPlaySongName.setText(songName);
        //更新歌手名称
        mPlayArtistName.setText(musicItem.getArtist());
        //更新进度
        startUpdateProgress();
        //获取并记录总时长
        duration = audioBinder.getDuration();
        String time = parseDuration(duration);
        mEndTime.setText(time);
        //设置进度条的总进度
        mProgressTime.setMax(duration);
        //更新播放状态按钮
        updatePlayStateBtn();

    }

    //切换当前播放状态
    private void startUpdateProgress() {
        int progress = audioBinder.getProgress();
        LogUtil.d("时时进度  :  " + progress);
        String progressBefor = parseDuration(progress);
        LogUtil.d("格式播放时间化后的进度   :  " + progressBefor);
        mStartTime.setText(progressBefor);

        mProgressTime.setProgress(progress);
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
        //获取当前播放状态
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
        mPlaySongName = (TextView) root.findViewById(R.id.play_song_name);
        mPlayArtistName = (TextView) root.findViewById(R.id.play_artist_name);
        mTitlebarPlayList = (ImageView) root.findViewById(R.id.titlebar_play_list);
        mStartTime = (TextView) root.findViewById(R.id.start_time);
        mProgressTime = (ProgressBtn) root.findViewById(R.id.progress_time);
        mEndTime = (TextView) root.findViewById(R.id.end_time);
        mRotateRl = (RelativeLayout) root.findViewById(R.id.rotate_rl);
        mPlayingSongAlbum = (CircleImageView) root.findViewById(R.id.playing_song_album);
        mMusicPlayerMode = (ImageView) root.findViewById(R.id.music_player_mode);
        mMusicPlayerPre = (ImageView) root.findViewById(R.id.music_player_pre);
        mMusicPlay = (ImageView) root.findViewById(R.id.music_play);
        mMusicPlayerNext = (ImageView) root.findViewById(R.id.music_player_next);
        mMusicPlayerRandom = (ImageView) root.findViewById(R.id.music_player_random);
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
    public void onDestroyView() {
        super.onDestroyView();
        mAnimatorListener.pause();
        disposables.clear();
    }
}
