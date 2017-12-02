package com.yibao.biggirl.mvp.main;

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

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.base.MyPageChangeListener;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.mvp.dialogfragment.AboutMeDialogFag;
import com.yibao.biggirl.mvp.dialogfragment.BeautifulDialogFag;
import com.yibao.biggirl.mvp.dialogfragment.MeDialogFragment;
import com.yibao.biggirl.mvp.dialogfragment.TopBigPicDialogFragment;
import com.yibao.biggirl.mvp.favorite.FavoriteActivity;
import com.yibao.biggirl.mvp.gank.girl.GirlActivity;
import com.yibao.biggirl.mvp.map.MapsActivity;
import com.yibao.biggirl.mvp.music.musiclist.MusicListActivity;
import com.yibao.biggirl.mvp.splash.SplashActivity;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.FileUtil;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.SnakbarUtil;

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
        implements OnRvItemClickListener,
                   NavigationView.OnNavigationItemSelectedListener,
                   OnRvItemLongClickListener

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
    private String    mUrl;


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
        System.out.println("AAAAAAA");
    }

    //加载动态布局
    private void initView() {
        setSupportActionBar(mToolbar);
        mToolbarLayout.setTitleEnabled(false);
        View headerView = mNavView.inflateHeaderView(R.layout.nav_header_main);

        mIvHeader = headerView.findViewById(R.id.iv_nav_header);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                                                                 mDrawerLayout,
                                                                 mToolbar,
                                                                 R.string.navigation_drawer_open,
                                                                 R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        mNavView.setNavigationItemSelectedListener(this);
    }

    private void initData() {
        mTablayout.setupWithViewPager(mViewPager);
        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager());
        mViewPager.setOffscreenPageLimit(Constants.arrTitle.length);
        mViewPager.setAdapter(pagerAdapter);


    }

    private void initListener() {

        //App头像
        mIvHeader.setOnClickListener(view -> MeDialogFragment.newInstance()
                                                             .show(getSupportFragmentManager(),
                                                                   "me"));
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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            //关于作者
            case R.id.action_about_me:
                AboutMeDialogFag.newInstance()
                                .show(getSupportFragmentManager(), "about");
                break;


            case R.id.action_my_favorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;
            case R.id.action_map:
                startActivity(new Intent(this, MapsActivity.class));

                break;
            case R.id.action_music: // ***************** 我的音乐
                startActivity(new Intent(this, MusicListActivity.class));
                break;
            case R.id.action_beautiful:
                //                                                RetrofitHelper.getUnsplashApi();
                BeautifulDialogFag.newInstance()
                                  .show(getSupportFragmentManager(), "beautiful");
                break;
            case R.id.action_share_me:
                shareMe();
                break;
            case R.id.action_setting:
                startActivity(new Intent(this, SplashActivity.class));
                finish();
                break;
            default:
                break;
        }
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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

                break;
            case R.id.main_action_star:
                //TODO
                break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }


    //打开Toolbar的背景大图
    @OnClick(R.id.iv_collapsing)
    public void onViewClicked() {

        TopBigPicDialogFragment diaLog = TopBigPicDialogFragment.newInstance(mUrl);
        if (diaLog.isResumed()) {
            diaLog.dismiss();
        }
        diaLog.show(getSupportFragmentManager(), "dialog_big_girl");

    }

    //长按显示预览
    @Override
    public void showPreview(String url) {

        TopBigPicDialogFragment.newInstance(url)
                               .show(getSupportFragmentManager(), "dialog_big_girl");
    }


    //ViewPager监听器
    private class MyOnpageChangeListener
            extends MyPageChangeListener
    {
        @Override
        public void onPageSelected(int position) {
            //切换ViewPage随机变换Toolbar的背景图片
            Random random       = new Random();
            int    girlPosition = random.nextInt(Api.picUrlArr.length);
            mUrl = Api.picUrlArr[girlPosition];
            ImageUitl.loadPicHolder(MyApplication.getIntstance(), mUrl, mIvCollapsing);
        }

    }

    //分享
    private void shareMe() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getTitle());
        shareIntent.putExtra(Intent.EXTRA_TEXT, Constants.SHARE_ME);
        shareIntent.setType("text/plain");
        startActivity(shareIntent);
    }


    //打开WebViewActivity
    @Override
    public void showDetail(FavoriteWebBean bean, Long id) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("favoriteBean", bean);
        intent.putExtra("id", id);
        startActivity(intent);
    }


    //打开ViewPager浏览大图
    @Override
    public void showBigGirl(int position, List<String> list) {
        //设置navHeader头像,待定
        //        ImageUitl.loadPic(this, list.get(position), mIvHeader);
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
                FileUtil.delDir(Constants.dir, true);
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
