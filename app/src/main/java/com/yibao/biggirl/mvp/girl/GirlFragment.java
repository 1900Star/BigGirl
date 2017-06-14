package com.yibao.biggirl.mvp.girl;


import android.content.Intent;
import android.graphics.Color;
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

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.model.girl.DownGrilProgressData;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.util.WallPaperUtil;
import com.yibao.biggirl.view.ProgressView;

import java.io.File;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static com.yibao.biggirl.R.id.vp;


/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/1 05:29
 */
public class GirlFragment
        extends Fragment
        implements ViewPager.OnPageChangeListener
{


    @BindView(R.id.toolbar)
    Toolbar      mToolbar;
    @BindView(vp)
    ViewPager    mVp;
    @BindView(R.id.iv_down)
    ProgressView mPbDown;
    Unbinder unbinder;
    //传递过来的position
    private int mPosition;
    //默认下载进度
    public static final int DEFULT_DOWN_PREGRESS = 0;
    //下载进度最大值
    public static       int MAX_DOWN_PREGRESS    = 100;

    //PerView滑动的状态的最值
    public static final int STATUS_MAX_NUM = 3;
    private GirlAdapter mPagerGirlAdapter;
    private View   mView = null;
    private String mUrl  = null;
    private List<String>        mList;
    private CompositeDisposable disposables;
    private MyApplication       mApplication;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mList = bundle.getStringArrayList("girlList");
        mPosition = bundle.getInt("position");
        mUrl = mList.get(mPosition);
        LogUtil.d("positon= " + mPosition + "   url = " + mUrl);
        mApplication = (MyApplication) getActivity().getApplication();
        disposables = new CompositeDisposable();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState)
    {
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

        mToolbar.setNavigationIcon(R.mipmap.back);
        mToolbar.setBackgroundColor(Color.BLACK);
        setHasOptionsMenu(true);

        mPagerGirlAdapter = new GirlAdapter(getActivity(), mList);
        mVp.setAdapter(mPagerGirlAdapter);
        mVp.setCurrentItem(mPosition);
        mVp.addOnPageChangeListener(this);
    }


    private void setProgress(int progress) {
        mPbDown.setProgress(progress);
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
                                    .subscribe(data -> GirlFragment.this.setProgress(data.getProgress())));
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
        menu.clear();
        inflater.inflate(R.menu.girl_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
            case R.id.action_setwallpaer: //设置壁纸
                WallPaperUtil.setWallPaper(getActivity(), mPagerGirlAdapter);
                SnakbarUtil.setWallpaer(mPbDown);
                break;
            case R.id.action_gallery:  //从相册选择壁纸
                WallPaperUtil.choiceWallPaper(getActivity());
                break;
            case R.id.action_share_mz:  //分享美女
                Observable.just(ImageUitl.downloadPic(mUrl, 1))
                          .subscribeOn(Schedulers.io())
                          .observeOn(AndroidSchedulers.mainThread())
                          .subscribe(integer -> {
                              if (integer == Constants.FIRST_DWON || integer == Constants.EXISTS) {
                                  Uri    url    = Uri.fromFile(new File(Constants.dir + "/share.jpg"));
                                  Intent intent = new Intent();
                                  intent.setAction(Intent.ACTION_SEND);
                                  intent.putExtra(Intent.EXTRA_STREAM, url);
                                  intent.setType("image/*");
                                  startActivity(Intent.createChooser(intent, "将妹子分享到"));

                              } else if (integer == Constants.DWON_PIC_EROOR) {
                                  SnakbarUtil.showSharePicFail(mPbDown);
                              }
                          });


                break;
            case R.id.action_gank:  //干货集中营
                initData();
                break;
            default:
                break;

        }


        return super.onOptionsItemSelected(item);
    }


    //图片保存
    @OnClick({R.id.iv_down})
    public void onViewClicked(View view) {

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


    public GirlFragment newInstance() { return new GirlFragment(); }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        unbinder.unbind();
    }
}
