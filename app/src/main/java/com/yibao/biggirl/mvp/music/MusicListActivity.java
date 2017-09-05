package com.yibao.biggirl.mvp.music;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.MyAnimatorUpdateListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.music.MusicDialogInfo;
import com.yibao.biggirl.model.music.MusicInfo;
import com.yibao.biggirl.model.music.MusicStatusBean;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.ColorUtil;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.MusicListUtil;
import com.yibao.biggirl.util.RxBus;
import com.yibao.biggirl.util.StringUtil;
import com.yibao.biggirl.util.ToastUtil;
import com.yibao.biggirl.view.CircleImageView;
import com.yibao.biggirl.view.ProgressBtn;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${音乐列表界面}
 * Time:2017/5/30 13:27
 */
public class MusicListActivity
        extends AppCompatActivity
        implements OnRvItemLongClickListener
{

    @BindView(R.id.music_lv)
    ListView mMusicList;

    @BindView(R.id.music_float_song_name)
    TextView        mMusicFloatSongName;
    @BindView(R.id.music_float_singer_name)
    TextView        mMusicFloatSingerName;
    @BindView(R.id.music_floating_pre)
    ImageView       mMusicFloatingPre;
    @BindView(R.id.music_floating_next)
    ImageView       mMusicFloatingNext;
    @BindView(R.id.music_floating_block)
    CardView        mCardFloatBlock;
    @BindView(R.id.music_toolbar_back)
    ImageView       mMusicToolbarBack;
    @BindView(R.id.music_floating_play)
    ImageView       mMusicFloatingPlay;
    @BindView(R.id.music_float_block_albulm)
    CircleImageView mMusicFloatBlockAlbulm;
    @BindView(R.id.music_float_pb)
    ProgressBtn     mPb;


    private static AudioPlayService.AudioBinder audioBinder;
    private        CompositeDisposable          disposables;
    private        ArrayList<MusicInfo>         mMusicItems;
    private        Unbinder                     mBind;
    private boolean isInitList = false;
    private ObjectAnimator           mAnimator;
    private MyAnimatorUpdateListener mAnimatorListener;
    private AudioServiceConnection   mConnection;
    private String                   mSongName;
    private String                   mArtistName;
    private Disposable               mDisposable;
    private Uri                      mAlbumUri;
    private MusicPlayDialogFag       mPlayDialogFag;
    private RxBus                    mBus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        LogUtil.d("   ***********************  onCreate");
        mBind = ButterKnife.bind(this);
        mBus = MyApplication.getIntstance()
                            .bus();
        disposables = new CompositeDisposable();

        initData();
        initListener();
        initRxBusData();

    }

    private void initRxBusData() {
        //接收service发出的数据，更新歌曲信息
        disposables.add(mBus.toObserverable(MusicInfo.class)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(this::perpareMusic));
        //   type 用来判断触发消息的源头，0 表示从 MusicPlayDialogFag， 1 表示从通知栏的音乐控制面板发出(Services中的广播)。
        disposables.add(mBus.toObserverable(MusicStatusBean.class)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(MusicListActivity.this::refreshBtnAndNotif));

    }

    private void refreshBtnAndNotif(MusicStatusBean bean) {
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
            case 1:
                showMusicDialog();
                break;
            case 2:
                finish();
                break;
            default:
                break;
        }
    }

    //设置歌曲名和歌手名
    private void perpareMusic(MusicInfo musicItem) {
        isInitList = true;
        //更新音乐标题
        mSongName = musicItem.getTitle();
        if (mSongName.contains("_")) {
            mSongName = mSongName.substring(mSongName.indexOf("_") + 1, mSongName.length());
        }
        mMusicFloatSongName.setText(mSongName);
        //更新歌手名称
        mArtistName = musicItem.getArtist();
        mMusicFloatSingerName.setText(mArtistName);
        //设置专辑
        mAlbumUri = StringUtil.getAlbulm(musicItem.getAlbumId());
        Glide.with(this)
             .load(mAlbumUri.toString())
             .asBitmap()
             .placeholder(R.drawable.dropdown_menu_noalbumcover)
             //             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(mMusicFloatBlockAlbulm);
        //更新播放状态按钮
        updatePlayBtnStatus();
        //初始化动画
        initAnimation();

        updataProgress();
        //打开音乐通知栏
        MusicNoification.openMusicNotification(this,
                                               audioBinder.isPlaying(),
                                               mAlbumUri,
                                               mSongName,
                                               mArtistName);

    }

    private void updataProgress() {
        int duration = audioBinder.getDuration();
        mPb.setMax(duration);
        if (mDisposable == null) {

            mDisposable = Observable.interval(0, 2800, TimeUnit.MICROSECONDS)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(aLong -> mPb.setProgress(audioBinder.getProgress()));
        }

    }

    private void initAnimation() {
        if (mAnimator == null || mAnimatorListener == null) {
            mAnimator = AnimationUtil.getRotation(mMusicFloatBlockAlbulm);
            mAnimatorListener = new MyAnimatorUpdateListener(mAnimator);
            mAnimator.start();
        }
        mAnimator.resume();
    }


    private void updatePlayBtnStatus() {
        //根据当前播放状态设置图片
        if (audioBinder.isPlaying()) {
            mMusicFloatingPlay.setImageResource(R.drawable.btn_playing_pause);
        } else {

            mMusicFloatingPlay.setImageResource(R.drawable.btn_playing_play);
        }
    }

    //切换当前播放状态
    private void switchPlayState() {
        if (audioBinder == null) {
            Toast.makeText(this, "当前没有歌曲播放-_-", Toast.LENGTH_SHORT);
            mAnimator.cancel();
        } else if (audioBinder.isPlaying()) {
            audioBinder.pause();     //当前播放  暂停
            mAnimator.pause();

        } else if (!audioBinder.isPlaying()) {
            audioBinder.start();     //当前暂停  播放
            mAnimator.resume();

        }
        //更新播放状态按钮
        updatePlayBtnStatus();
    }


    private void initData() {
        mPb.setColor(ColorUtil.errorColor);
        MusicListAdapter adapters = new MusicListAdapter(MusicListUtil.getMusicList(this), this);

        mMusicList.setAdapter(adapters);
    }

    private void initListener() {
        mMusicList.setOnItemClickListener((parent, view, position, id) -> {
            //获取音乐列表
            //            mMusicItems = MusicItem.getAudioItems((Cursor) parent.getItemAtPosition(position));
            mMusicItems = MusicListUtil.getMusicList(this);
            //开启服务，播放音乐并且将数据传送过去
            Intent intent = new Intent();
            intent.setClass(this, AudioPlayService.class);
            intent.putParcelableArrayListExtra("musicItem", mMusicItems);
            intent.putExtra("position", position);
            mConnection = new AudioServiceConnection();
            bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
            startService(intent);


        });
    }


    @OnClick(R.id.music_toolbar_back)
    public void onViewClicked() {finish();}

    @OnClick({R.id.music_floating_pre,
              R.id.music_floating_play,
              R.id.music_floating_next,
              R.id.music_floating_block})
    public void onViewClicked(View view) {
        if (isInitList) {
            switch (view.getId()) {
                case R.id.music_floating_pre:
                    audioBinder.playPre();
                    break;
                case R.id.music_floating_play:
                    switchPlayState();
                    break;
                case R.id.music_floating_next:
                    audioBinder.playNext();
                    break;
                case R.id.music_floating_block:     //音乐控制浮块
                    showMusicDialog();
                    break;
            }
        } else {
            ToastUtil.showLong(this, "当前没有可以播放的音乐_-_");
        }
    }

    private void showMusicDialog() {
        MusicDialogInfo info = new MusicDialogInfo(mMusicItems,
                                                   mSongName,
                                                   mArtistName,
                                                   mAlbumUri.toString());

        //        if (mPlayDialogFag.isVisible()) {
        //            mPlayDialogFag = MusicPlayDialogFag.newInstance(info);
        //            mPlayDialogFag.show(getSupportFragmentManager(), "music");
        //        }
        mPlayDialogFag = MusicPlayDialogFag.newInstance(info);
        mPlayDialogFag.show(getSupportFragmentManager(), "music");
        //        mDisposable.dispose();
    }

    public static AudioPlayService.AudioBinder getAudioBinder() {

        return audioBinder;
    }

    @Override
    public void showPreview(String url) {
        TopBigPicDialogFragment.newInstance(url)
                               .show(getSupportFragmentManager(), "dialog_big_girl");
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
    protected void onPause() {
        super.onPause();
        //        disposables.clear();
        if (mAnimator != null) {
            mAnimator.pause();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mAnimator != null && audioBinder.isPlaying()) {
            mAnimator.resume();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mAnimator != null && mAnimatorListener != null && mConnection != null && mDisposable != null) {
            mAnimator.cancel();
            mAnimatorListener.pause();
            mDisposable.dispose();
            unbindService(mConnection);
        }
        disposables.clear();
        mBind.unbind();
    }
}
