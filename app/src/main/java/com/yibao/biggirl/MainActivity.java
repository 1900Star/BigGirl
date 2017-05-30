package com.yibao.biggirl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import com.yibao.biggirl.base.BaseFragment;
import com.yibao.biggirl.base.OnRvItemWebClickListener;
import com.yibao.biggirl.factory.FragmentFactorys;
import com.yibao.biggirl.factory.MyDialogFragment;
import com.yibao.biggirl.mvp.girl.BigGrilActivity;
import com.yibao.biggirl.mvp.girl.GirlActivity;
import com.yibao.biggirl.mvp.girls.GirlsAdapter;
import com.yibao.biggirl.mvp.girls.TabPagerAdapter;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 作者：Stran on 2017/3/23 15:12
 * 描述：${主页面}
 * 邮箱：strangermy@outlook.com
 */
public class MainActivity
        extends AppCompatActivity
        implements GirlsAdapter.OnRvItemClickListener,
                   OnRvItemWebClickListener,
                   NavigationView.OnNavigationItemSelectedListener
{
    @BindView(R.id.nav_view)
    NavigationView mNavView;
    @BindView(R.id.drawer_layout)
    DrawerLayout   mDrawerLayout;

    @BindView(R.id.tablayout)
    TabLayout               mTablayout;
    @BindView(R.id.view_pager)
    ViewPager               mViewPager;
    @BindView(R.id.toolbar)
    Toolbar                 mToolbar;
    @BindView(R.id.toolbar_layout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.iv_collapsing)
    ImageView               mIvCollapsing;
    private long exitTime = 0;

    private Unbinder  mBind;
    private ImageView mIvHeader;
    private int       mGirlPosition;
    private TabPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);
        if (savedInstanceState == null) {
            initView();
            initData();
            initListener();

        }

    }

    private void initListener() {
        MyOnpageChangeListener mMyOnpageChangeListener = new MyOnpageChangeListener();

        mViewPager.addOnPageChangeListener(mMyOnpageChangeListener);
        //监听ViewPager布局完成
        mViewPager.getViewTreeObserver()
                  .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                      @Override
                      public void onGlobalLayout() {
                          LogUtil.d("ViewPager布局完成");
                          //手动选中第一页
                          mMyOnpageChangeListener.onPageSelected(0);
                          mViewPager.getViewTreeObserver()
                                    .removeOnGlobalLayoutListener(this);
                      }
                  });
    }

    //打开背景大图
    @OnClick(R.id.iv_collapsing)
    public void onViewClicked() {

        LogUtil.d("mGirlPosition==" + mGirlPosition);
        Intent intent = new Intent(this, BigGrilActivity.class);
        intent.putExtra("position", mGirlPosition);

        startActivity(intent);

    }

    private class MyOnpageChangeListener
            implements ViewPager.OnPageChangeListener
    {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //切换ViewPage随机变换Toolbar的背景图片
            Random random = new Random();
            mGirlPosition = random.nextInt(Api.picUrlArr.length);
            ImageUitl.loadPicHolder(MyApplication.getIntstance(),
                                    Api.picUrlArr[mGirlPosition],
                                    mIvCollapsing);
            //触发加载数据
            BaseFragment baseFragment = FragmentFactorys.mCacheFragmentMap.get(position);

            //            baseFragment.mLoadingPager.triggerLoadData();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            //关于作者
            case R.id.about_me:
                DialogUtil.aboutMeDialog(this, R.layout.aboutme);
                break;
            case R.id.my_feir:

                //                                RetrofitHelper.getUnsplashApi();
                //                DialogUtil.creatDialog(this, R.layout.girl_video);
                MyDialogFragment dialogFragment = new MyDialogFragment();
                dialogFragment.show(getSupportFragmentManager(), "dialog_big_girl");
                //                AppPresenter.getDatas(20, 1, Constants.FRAGMENT_APP);
                break;
            case R.id.share_me:
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, getTitle());
                shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.SHARE_ME);
                shareIntent.setType("text/plain");
                startActivity(shareIntent);
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);


        return true;
    }


    private void initData() {

        mTablayout.setupWithViewPager(mViewPager);
        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(7);
        mViewPager.setAdapter(mPagerAdapter);
    }

    //加载动态布局
    private void initView() {
        setSupportActionBar(mToolbar);
        mToolbarLayout.setTitleEnabled(false);
        View headerView = mNavView.inflateHeaderView(R.layout.nav_header_main);
        mIvHeader = (ImageView) headerView.findViewById(R.id.iv_nav_header);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                 mDrawerLayout,
                                                                 mToolbar,
                                                                 R.string.navigation_drawer_open,
                                                                 R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_action_search:
                LogUtil.d("Search");
                break;
            case R.id.main_action_star:
                LogUtil.d("Star");
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    //打开WebViewActivity
    @Override
    public void showDesDetall(String url) {
        Intent intent = new Intent(this, WebViewActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);


    }


    //接口回调打开ViewPager浏览大图
    @Override
    public void showPagerFragment(int position, List<String> list) {
        //设置navHeader头像
        ImageUitl.loadPic(this, list.get(0), mIvHeader);
        Intent intent = new Intent(this, GirlActivity.class);
        intent.putStringArrayListExtra("girlList", (ArrayList<String>) list);
        intent.putExtra("position", position);
        startActivity(intent);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);

        }
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {

            //两秒之内按返回键多次就会退出
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                SnakbarUtil.finishActivity(mDrawerLayout);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
