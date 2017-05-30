package com.yibao.biggirl.mvp.girl;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
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
import android.view.Window;
import android.widget.ImageView;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.model.girl.DownGrilProgressData;
import com.yibao.biggirl.util.BitmapUtil;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.util.WallPaperUtil;
import com.yibao.biggirl.view.ProgressView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;


/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/1 05:29
 */
public class GirlFragment
        extends Fragment
        implements ViewPager.OnPageChangeListener
{

    @BindView(R.id.vp)
    ViewPager    mVp;
    @BindView(R.id.iv_down)
    ProgressView mPbDown;
    Unbinder unbinder;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;

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


    //图片保存
    @OnClick(R.id.iv_down)
    public void onViewClicked() {
        //网络检查
        boolean isConnected = NetworkUtil.isNetworkConnected(getActivity());
        if (isConnected) {
            ImageView view   = (ImageView) mPagerGirlAdapter.getPrimaryItem();
            Bitmap    bitmap = BitmapUtil.drawableToBitmap(view.getDrawable());

            ImageUitl.downloadPic(bitmap, mUrl, true);
            //                        SnakbarUtil.savePic(mPbDown, mUrl);

        } else {
            SnakbarUtil.netErrors(mPbDown);
        }
    }

    private void setProgress(int progress) {
        mPbDown.setProgress(progress);

        if (progress == MAX_DOWN_PREGRESS) {
            SnakbarUtil.showSuccessStatus(mPbDown);
        }
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        getColor();
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
            case R.id.action_share_mz:  //默认美女
                ImageView iv = (ImageView) mPagerGirlAdapter.getPrimaryItem();
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_SEND);
                //                intent.putExtras(iv);
                intent.setType("image/*");
                startActivity(Intent.createChooser(intent, "分享MeiZhi到"));


                break;
            case R.id.action_gank:  //干货集中营
                initData();
                break;

        }


        return super.onOptionsItemSelected(item);
    }

    public GirlFragment newInstance() { return new GirlFragment(); }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        disposables.clear();
    }

    //Rxbus接收下载进度 ，设置progress进度
    public void getProgress() {
        disposables.add(mApplication.bus()
                                    .toObserverable(DownGrilProgressData.class)
                                    .subscribe(data -> GirlFragment.this.setProgress(data.getProgress())));
    }


    public void getColor() {
        ImageView iv = (ImageView) mPagerGirlAdapter.getPrimaryItem();
        if (iv != null) {

            //            Palette.Builder builder = Palette.from(BitmapUtil.drawableToBitmap(iv.getDrawable()));
            //            builder.generate(palette -> {
            //                Palette.Swatch vir = palette.getVibrantSwatch();
            //                if (vir == null) {
            //                    return;
            //                }
            //                int rgb = vir.getRgb();
            //                LogUtil.d("Rgb== " + rgb);
            //                mPbDown.setBackgroundColor(rgb);
            //                mToolbar.setBackgroundColor(rgb);
            //
            if (android.os.Build.VERSION.SDK_INT >= 21) {
                Window window = getActivity().getWindow();
                //                    window.setStatusBarColor(ColorUtil.colorBurn(vir.getRgb()));
            }
            //            });
        }
    }


}
