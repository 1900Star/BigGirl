package com.yibao.biggirl.mvp.gank.girl;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.HideToolbarListener;
import com.yibao.biggirl.model.girl.DownGrilProgressData;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.FileUtil;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.util.WallPaperUtil;
import com.yibao.biggirl.view.ProgressView;

import java.io.File;
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

import static com.yibao.biggirl.R.id.vp;


/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/1 05:29
 * @author Stran
 */
public class GirlFragment
        extends Fragment
        implements ViewPager.OnPageChangeListener {

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(vp)
    ViewPager mVp;
    @BindView(R.id.iv_down)
    ProgressView mPbDown;
    Unbinder unbinder;
    //传递过来的position
    private int mPosition;
    //默认下载进度
    public static final int DEFULT_DOWN_PREGRESS = 0;
    //下载进度最大值
    public static int MAX_DOWN_PREGRESS = 100;

    //PergerView滑动的状态的最值
    public static final int STATUS_MAX_NUM = 3;
    private GirlAdapter mAdapter;
    private View mView = null;
    private String mUrl = null;
    private ArrayList<String> mList;
    private CompositeDisposable disposables;
    private Disposable mDisposable;
    private MyApplication mApplication;
    private MenuItem mMenuItem;
    private PagerScroller mScroller;
    private boolean isPlay = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mList = bundle.getStringArrayList("girlList");
        mPosition = bundle.getInt("position");
        mUrl = mList.get(mPosition);
        mApplication = (MyApplication) getActivity().getApplication();
        disposables = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState == null) {

            mView = inflater.inflate(R.layout.girl_frag, container, false);
            unbinder = ButterKnife.bind(this, mView);
            getProgress();
            initData();
        }

        return mView;
    }


    private void initData() {
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar()
                .setDisplayHomeAsUpEnabled(true);

        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        setHasOptionsMenu(true);
        mScroller = new PagerScroller(getActivity());
        if (mList.size() != 0) {
            mAdapter = new GirlAdapter(getActivity(), mList);
        }
        boolean animationSwitch = true;
        mVp.setPageTransformer(animationSwitch, new GirlPageTransformer());
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(mPosition);
        mVp.addOnPageChangeListener(this);
    }


    private void setProgress(int progress, int type) {
        mPbDown.setProgress(progress);
        if (type == 1 && progress == MAX_DOWN_PREGRESS) {
            SnakbarUtil.showSuccessView(mPbDown);
            return;
        }
        if (progress == MAX_DOWN_PREGRESS) {
            //将下载的图片插入到系统相册
            Observable.just(ImageUitl.insertImageToPhoto())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(aBoolean -> {
                        if (aBoolean) {
                            SnakbarUtil.showSuccessView(mPbDown);
                        } else {
                            SnakbarUtil.showDownPicFail(mPbDown);
                        }
                    });
        }
    }

    //Rxbus接收下载进度 ，设置progress进度
    public void getProgress() {
        disposables.add(mApplication.bus()
                .toObserverable(DownGrilProgressData.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> GirlFragment.this.setProgress(data.getProgress(),
                        data.getType())));
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mUrl = mList.get(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

        if (state < STATUS_MAX_NUM) {
            mPbDown.setProgress(DEFULT_DOWN_PREGRESS);
        }
    }

    //toolbar菜单
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.girl_main, menu);
        mMenuItem = menu.findItem(R.id.action_auto_play);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            //设置壁纸
            case R.id.action_setwallpaer:
                WallPaperUtil.setWallPaper(getActivity(), mAdapter);
                SnakbarUtil.setWallpaer(mPbDown);
                break;

            //自动播放图册
            case R.id.action_auto_play:
                autoPreview();
                break;
            //分享美女
            case R.id.action_share_mz:
                shareGirl();
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    private void autoPreview() {
        if (isPlay) {
            mMenuItem.setIcon(R.drawable.btn_playing_play_selector);
            mPbDown.setVisibility(View.VISIBLE);
            //停止自动播放
            stopLoop();
            isPlay = false;
        } else {
            mMenuItem.setIcon(R.drawable.btn_playing_pause_selector);
            ((HideToolbarListener) getActivity()).hideToolbar();
            mPbDown.setVisibility(View.INVISIBLE);
            //开始自动播放
            startLoop();
            isPlay = true;
        }
    }

    //ViewPager开始自动轮播
    private void startLoop() {

        mScroller.initViewPagerScroll(mVp, 2000);
        mDisposable = Observable.interval(2000, 3000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> {
                    int item = mVp.getCurrentItem();
                    if (item == mAdapter.getCount()) {
                        mVp.setCurrentItem(0);
                    }
                    mVp.setCurrentItem(++item, true);

                });

    }

    //ViewPager停止轮播
    private void stopLoop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mScroller.setDuration(600);
    }

    //图片保存
    @OnClick({R.id.iv_down})
    public void onViewClicked(View view) {
        ImageView v = (ImageView) mAdapter.getPrimaryItem();
        // 网络检查
        boolean isConnected = NetworkUtil.isNetworkConnected(getActivity());
        if (isConnected) {
            Observable.just(ImageUitl.downloadPic(mUrl, Constants.FIRST_DWON))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if (integer == Constants.EXISTS) {
                            SnakbarUtil.picAlreadyExists(mPbDown);
                        } else if (integer == Constants.DWON_PIC_EROOR) {
                            SnakbarUtil.showDownPicFail(mPbDown);
                        }
                    });
        } else {
            SnakbarUtil.netErrors(mPbDown);
        }

    }

    private void shareGirl() {
        FileUtil.delFile(Constants.DELETE_DIR);
        Observable.just(ImageUitl.downloadPic(mUrl, Constants.EXISTS))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == Constants.FIRST_DWON || integer == Constants.EXISTS) {
                        Uri url = Uri.fromFile(new File(Constants.DELETE_DIR));
                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_SEND);
                        intent.putExtra(Intent.EXTRA_STREAM, url);
                        intent.setType("image/*");
                        startActivity(Intent.createChooser(intent, "将妹子分享到"));

                    } else if (integer == Constants.DWON_PIC_EROOR) {
                        SnakbarUtil.showSharePicFail(mPbDown);
                    }
                });
    }


    public GirlFragment newInstance() {
        return new GirlFragment();
    }


    @Override
    public void onPause() {
        super.onPause();
        if (mDisposable != null) {
            mMenuItem.setIcon(R.drawable.btn_playing_play);
            mScroller.setDuration(300);
            mDisposable.dispose();

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        disposables.clear();
    }

}
