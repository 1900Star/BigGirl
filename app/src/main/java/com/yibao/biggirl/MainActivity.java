package com.yibao.biggirl;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.yibao.biggirl.base.OnRvItemWebClickListener;
import com.yibao.biggirl.mvp.android.AndroidFragment;
import com.yibao.biggirl.mvp.app.AppFragment;
import com.yibao.biggirl.mvp.expand.ExpandFragment;
import com.yibao.biggirl.mvp.frontend.FrontEndFragment;
import com.yibao.biggirl.mvp.girl.GirlActivity;
import com.yibao.biggirl.mvp.girls.GirlsAdapter;
import com.yibao.biggirl.mvp.girls.GirlsFragment;
import com.yibao.biggirl.mvp.girls.TabPagerAdapter;
import com.yibao.biggirl.mvp.ios.IOSFragment;
import com.yibao.biggirl.mvp.video.VideoFragmnet;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.DialogUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.webview.WebViewActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    TabLayout mTablayout;
    @BindView(R.id.view_pager)
    ViewPager mViewPager;
    @BindView(R.id.toolbar)
    Toolbar   mToolbar;
    private long   exitTime   = 0;
    private String arrTitle[] = {"Girl",
                                 "Android",
                                 "Video",
                                 "App",
                                 "iOS",
                                 "前端",
                                 "拓展资源"};
    private Unbinder        mBind;
    private ImageView       mIvHeader;
    private List<Fragment>  mFragments;
    private TabPagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBind = ButterKnife.bind(this);
        if (savedInstanceState == null) {
            initView();
            initData();
            //            initListener();

        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            //关于作者
            case R.id.about_me:
                DialogUtil.creatDialog(this, R.layout.aboutme);
                break;
            case R.id.my_feir:
                //
                showDesDetall(Api.myFeir);
                //                RetrofitHelper.getUnsplashApi();

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
        mFragments = new ArrayList<>();
        mFragments.add(new GirlsFragment().newInstance());
        mFragments.add(new AndroidFragment().newInstance());
        mFragments.add(new VideoFragmnet().newInstance());
        mFragments.add(new AppFragment().newInstance(Constants.FRAGMENT_APP));
        mFragments.add(new IOSFragment().newInstance());
        mFragments.add(new FrontEndFragment().newInstance());
        mFragments.add(new ExpandFragment().newInstance());
        mPagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),
                                            mFragments,
                                            Arrays.asList(arrTitle));
        mViewPager.setOffscreenPageLimit(7);
        mViewPager.setAdapter(mPagerAdapter);

    }

    //加载动态布局
    private void initView() {
        setSupportActionBar(mToolbar);
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
        Glide.with(this)
             .load(list.get(0))
             .asBitmap()
             .diskCacheStrategy(DiskCacheStrategy.SOURCE)
             .into(mIvHeader);

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
