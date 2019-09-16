package com.yibao.biggirl.mvp.gank.girl;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.HideToolbarListener;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.model.girl.DownGrilProgressData;
import com.yibao.biggirl.mvp.webview.WebPresenter;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.FileUtil;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.util.SystemUiVisibilityUtil;
import com.yibao.biggirl.util.WallPaperUtil;
import com.yibao.biggirl.view.ProgressView;

import java.io.File;
import java.util.List;
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
 * Des：${TODO}
 * Time:2017/4/8 04:24
 *
 * @author Stran
 */
public class GirlActivity
        extends AppCompatActivity implements ViewPager.OnPageChangeListener, HideToolbarListener, GirlContract.View {


    @BindView(R.id.vp)
    ViewPager mVp;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.iv_down)
    ProgressView mPbDown;
    @BindView(R.id.app_bar_layout)
    AppBarLayout mAppBarLayout;
    private Unbinder mBind;
    private MenuItem mMenuItemPlay;
    private CompositeDisposable disposables;
    private int mPosition;
    /**
     * 默认下载进度
     */
    public static final int DEFULT_DOWN_PREGRESS = 0;
    /**
     * 下载进度最大值
     */
    public static int MAX_DOWN_PREGRESS = 100;

    /**
     * PergerView滑动的状态的最值
     */
    public static final int STATUS_MAX_NUM = 3;
    private String mUrl;
    private List<String> mList;
    private PagerScroller mScroller;
    private GirlAdapter mAdapter;
    private Disposable mDisposable;
    private boolean mIsHidden = false;
    private boolean mIsConnected;
    private boolean isPlay = false;
    private MenuItem mMenuFavorite;
    private boolean isFavorite = false;
    private WebPresenter mWebPresenter;
    private Long mId;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.girl_frag);
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        mBind = ButterKnife.bind(this);
        initView();
        initData();
        getProgress();
    }

    private void initView() {
        mToolbar.setTitle(R.string.girl_title);
        mList = getIntent().getStringArrayListExtra("girlList");
        mPosition = getIntent().getIntExtra("position", 0);
        mUrl = mList.get(mPosition);
        mWebPresenter = new WebPresenter(this);


    }

    private void initData() {
        disposables = new CompositeDisposable();
        setSupportActionBar(mToolbar);
        mAppBarLayout.setAlpha(0.7f);
        mScroller = new PagerScroller(this);
        mIsConnected = NetworkUtil.isNetworkConnected(this);
        mWebPresenter.queryFavoriteIsCollect(2, "3");

        if (mList.size() != 0) {
            mAdapter = new GirlAdapter(this, mList);
        }
        mVp.setPageTransformer(true, new GirlPageTransformer());
        mVp.setAdapter(mAdapter);
        mVp.setCurrentItem(mPosition);
        mVp.addOnPageChangeListener(this);


    }


    @Override
    public void hideOrShowAppbar() {
        hideOrShowToolbar();
    }

    protected void hideOrShowToolbar() {
        mAppBarLayout.animate()
                .translationY(mIsHidden ? 0 : -mAppBarLayout.getHeight())
                .setInterpolator(new DecelerateInterpolator(2))
                .start();
        if (mIsHidden) {
            mMenuItemPlay.setIcon(R.drawable.btn_playing_play_selector);
            mPbDown.setVisibility(View.VISIBLE);
        } else {
            mMenuItemPlay.setIcon(R.drawable.btn_playing_pause_selector);
            mPbDown.setVisibility(View.INVISIBLE);
        }
        mIsHidden = !mIsHidden;
    }


    private void autoPreview() {
        hideOrShowToolbar();
        if (isPlay) {
            //停止自动播放
            stopLoop();
        } else {
            //开始自动播放
            startLoop();
        }
        isPlay = !isPlay;
    }

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

    // ViewPager停止轮播
    private void stopLoop() {
        if (!mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
        mScroller.setDuration(600);
    }

    public void getProgress() {
        disposables.add(MyApplication.getIntstance().bus()
                .toObserverable(DownGrilProgressData.class)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(data -> this.setProgress(data.getProgress(),
                        data.getType())));
    }

    private void setProgress(int progress, int downPicType) {
        mPbDown.setProgress(progress);
        if (downPicType == Constants.EXISTS && progress == MAX_DOWN_PREGRESS) {
            SnakbarUtil.showSuccessView(mPbDown);
        } else if (downPicType == Constants.FIRST_DWON && progress == MAX_DOWN_PREGRESS) {
            //将下载的图片插入到系统相册
            disposables.add(ImageUitl.insertImageToPhotos().observeOn(AndroidSchedulers.mainThread()).subscribe(aBoolean -> {
                if (aBoolean) {
                    SnakbarUtil.showSuccessView(mPbDown);
                } else {
                    SnakbarUtil.showDownPicFail(mPbDown);
                }

            }));
        }
    }


    //图片保存

    @OnClick({R.id.iv_down})
    public void onViewClicked(View view) {
        // 网络检查
        if (mIsConnected) {
            disposables.add(ImageUitl.savePic(mUrl, Constants.FIRST_DWON).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(integer -> {
                        if (integer == Constants.EXISTS) {
                            SnakbarUtil.picAlreadyExists(mPbDown);
                        } else if (integer == Constants.DWON_PIC_EROOR) {
                            SnakbarUtil.showDownPicFail(mPbDown);
                        }
                    }));
        } else {
            SnakbarUtil.netErrors(mPbDown);
        }

    }

    private void shareGirl() {
        if (mIsConnected) {
            FileUtil.delFile(Constants.DELETE_DIR);
            disposables.add(ImageUitl.savePic(mUrl, Constants.EXISTS).observeOn(AndroidSchedulers.mainThread())
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
                    }));
        } else {
            SnakbarUtil.netErrors(mPbDown);

        }

    }

    //设置收藏图标
    public void refreshFavoriteBtn() {
        mMenuFavorite.setIcon(isFavorite ? R.drawable.ic_star_green_24dp : R.drawable.ic_star_white_24dp);
        isFavorite = !isFavorite;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.girl_main, menu);
        mMenuItemPlay = menu.findItem(R.id.action_auto_play);
        mMenuFavorite = menu.findItem(R.id.action_favorite_mz);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_favorite_mz:
                favoriteGirl();
                break;
            //设置壁纸
            case R.id.action_setwallpaer:
                WallPaperUtil.setWallPaper(this, mAdapter);
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

    private void favoriteGirl() {
        if (isFavorite) {
            mWebPresenter.cancelFavorite((long) mPosition, 2);
        } else {
//            private String url;
//            private String gankId;
//            private String imagUrl;
//            private String des;
//            private String name;
//            private String type;
//            private String time;
            FavoriteWebBean favoriteBean = new FavoriteWebBean();
            favoriteBean.setImagUrl(mUrl);
            favoriteBean.setType(Constants.SING_GIRL);
            favoriteBean.setDes("AA");
            favoriteBean.setName("AA");
            favoriteBean.setGankId("01");
            favoriteBean.setTime("10");
            favoriteBean.setUrl(mUrl);
            favoriteBean.setId((long) mPosition);
            mWebPresenter.insertFavorite(favoriteBean);

        }
        refreshFavoriteBtn();

    }


    @Override
    protected void onPause() {
        super.onPause();
        if (mDisposable != null) {
            mDisposable.dispose();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        if (disposables != null) {
            disposables.dispose();
            disposables.clear();
            disposables = null;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
        mUrl = mList.get(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state < STATUS_MAX_NUM) {
            mPbDown.setProgress(DEFULT_DOWN_PREGRESS);
        }
    }

    @Override
    public void insertStatus(Long status) {
        if (status > 0) {
            mId = status;
            LogUtil.d("刚存入的  ID :" + mId);
            isFavorite = true;
            refreshFavoriteBtn();
            SnakbarUtil.favoriteSuccessView(mToolbar, "收藏成功  -_-");


        } else {

            SnakbarUtil.favoriteFailView(mToolbar, "收藏失败");
        }
    }

    @Override
    public void cancelStatus(Long id) {
        if (id < 0) {
            SnakbarUtil.favoriteFailView(mToolbar, "取消收藏失败");
        } else {

            mId = id;
            isFavorite = false;
            refreshFavoriteBtn();
            LogUtil.d("lsp" + "已取消收藏  ID :" + id);
        }
    }

    @Override
    public void queryAllFavorite(List<FavoriteWebBean> list) {

    }

    @Override
    public void queryFavoriteIsCollect(List<FavoriteWebBean> list) {
        if (list.size() != 0) {
            LogUtil.d("已经存在了     ");
            isFavorite = true;
            refreshFavoriteBtn();
        }
    }


}
