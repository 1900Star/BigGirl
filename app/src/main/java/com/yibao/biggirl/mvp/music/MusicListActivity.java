package com.yibao.biggirl.mvp.music;

import android.animation.ObjectAnimator;
import android.app.Service;
import android.content.AsyncQueryHandler;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.ServiceConnection;
import android.database.Cursor;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.MyAnimatorUpdateListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.music.MusicItem;
import com.yibao.biggirl.model.music.MusicStatusBean;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.service.AudioPlayService;
import com.yibao.biggirl.util.AnimationUtil;
import com.yibao.biggirl.util.RandomUtil;
import com.yibao.biggirl.util.ToastUtil;
import com.yibao.biggirl.view.CircleImageView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

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


    private        MusicListAdapter             mAdapter;
    private static AudioPlayService.AudioBinder audioBinder;
    private        CompositeDisposable          disposables;
    private        ArrayList<MusicItem>         mMusicItems;
    private        int                          mCureentPosition;
    private        Unbinder                     mBind;
    private boolean isInitList = false;
    private ObjectAnimator           mAnimator;
    private MyAnimatorUpdateListener mAnimatorListener;
    private AudioServiceConnection   mConnection;
    private String                   mSongName;
    private String                   mArtistName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);
        mBind = ButterKnife.bind(this);
        disposables = new CompositeDisposable();

        initData();
        initListener();
        initRxBusData();
        initMusicData();

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
        mSongName = musicItem.getTitle();
        if (mSongName.contains("_")) {
            mSongName = mSongName.substring(mSongName.indexOf("_") + 1, mSongName.length());
        }
        mMusicFloatSongName.setText(mSongName);
        //更新歌手名称
        mArtistName = musicItem.getArtist();
        mMusicFloatSingerName.setText(mArtistName);
        //设置专辑
        Glide.with(this)
             .load(RandomUtil.getRandomUrl())
             .asBitmap()
             .placeholder(R.mipmap.xuan)
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(mMusicFloatBlockAlbulm);
        //更新播放状态按钮
        updatePlayStateBtn();
        //初始化动画
        initAnimation();


    }

    private void initAnimation() {
        if (mAnimator == null || mAnimatorListener == null) {
            mAnimator = AnimationUtil.getRotation(mMusicFloatBlockAlbulm);
            mAnimatorListener = new MyAnimatorUpdateListener(mAnimator);
            mAnimator.start();
        }
        mAnimator.resume();
    }


    private void updatePlayStateBtn() {
        //根据当前播放状态设置图片
        if (audioBinder.isPlaying()) {
            mMusicFloatingPlay.setImageResource(R.drawable.btn_playing_pause);
            //            mAnimator.resume();
        } else {
            //正在播放
            MyApplication.getIntstance()
                         .bus()
                         .post(new MusicStatusBean(0));
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
        updatePlayStateBtn();
    }


    private void initData() {
        mAdapter = new MusicListAdapter(this, null);
        mMusicList.setAdapter(mAdapter);


    }

    private void initListener() {
        mMusicList.setOnItemClickListener((parent, view, position, id) -> {
            //获取音乐列表
            mMusicItems = MusicItem.getAudioItems((Cursor) parent.getItemAtPosition(position));
            mCureentPosition = position;
            //开启服务，播放音乐并且将数据传送过去
            Intent intent = new Intent();
            intent.setClass(this, AudioPlayService.class);
            intent.putParcelableArrayListExtra("musicItem", mMusicItems);
            intent.putExtra("position", position);
            mConnection = new AudioServiceConnection();
            bindService(intent, mConnection, Service.BIND_AUTO_CREATE);
            startService(intent);
            isInitList = true;
            //            initRxBusData();

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
                    MusicPlayDialogFag.newInstance(mMusicItems, mSongName, mArtistName)
                                      .show(getSupportFragmentManager(), "music");
                    break;
            }
        } else {
            ToastUtil.showLong(this, "当前没有可以播放的音乐_-_");
        }
    }

    public static AudioPlayService.AudioBinder getAudioBinder() {

        return audioBinder;
    }

    //获取音乐列表
    private void initMusicData() {
        ContentResolver resolver = getContentResolver();
        AsyncQueryHandler handler = new AsyncQueryHandler(resolver) {
            @Override
            protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
                super.onQueryComplete(token, cookie, cursor);
                ((MusicListAdapter) cookie).swapCursor(cursor);

            }
        };

        handler.startQuery(0,
                           mAdapter,
                           MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                           new String[]{MediaStore.Audio.Media._ID,
                                        MediaStore.Audio.Media.DATA,
                                        MediaStore.Audio.Media.DISPLAY_NAME,
                                        MediaStore.Audio.Media.ARTIST},
                           null,
                           null,
                           null);

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
        if (mAnimator != null && mAnimatorListener != null && mConnection != null) {
            mAnimator.cancel();
            mAnimatorListener.pause();
            unbindService(mConnection);
        }
        disposables.clear();
        mBind.unbind();
    }
}
