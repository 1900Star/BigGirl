package com.yibao.biggirl.mvp.webview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.GeolocationPermissions;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.R;
import com.yibao.biggirl.model.dagger2.component.DaggerWebComponent;
import com.yibao.biggirl.model.dagger2.moduls.WebModuls;
import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;
import com.yibao.biggirl.model.favoriteweb.UpdataFavorite;
import com.yibao.biggirl.mvp.favorite.FavoriteContract;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.SnakbarUtil;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.yibao.biggirl.R.id.toolbar;

/**
 * Author：Sid
 * Des：${WebView}
 * Time:2017/4/23 14:08
 */
public class WebActivity
        extends AppCompatActivity
        implements FavoriteContract.View
{
    private static final String TAG = "WebActivity";
    @BindView(toolbar)
    Toolbar     mToolbar;
    @BindView(R.id.progress_bar_web)
    ProgressBar mProgressBarWeb;
    @BindView(R.id.content_web)
    FrameLayout mContentWeb;
    private WebView  mWebView;
    private Unbinder mBind;
    @Inject
    WebPresenter mPresenter;

    private String          mUrl;
    private FavoriteWebBean mBean;
    private boolean         isFavorite;
    private long            mId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        mBind = ButterKnife.bind(this);
        initView();
        DaggerWebComponent component = (DaggerWebComponent) DaggerWebComponent.builder()
                                                                              .webModuls(new WebModuls(
                                                                                      this))
                                                                              .build();
        component.in(this);
        mId = getIntent().getLongExtra("id", 0);
        mBean = getIntent().getParcelableExtra("favoriteBean");
        LogUtil.d("验证 ID :" + mBean.getId() + " **  " + mId);
        mPresenter.queryFavoriteIsCollect(mBean.getGankId());
        mUrl = mBean.getUrl();
        initData();
    }

    private void initView() {
        mToolbar.inflateMenu(R.menu.activity_web);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(view -> finish());
        mToolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case android.R.id.home:
                    finish();
                    break;
                case R.id.web_favorite:
                    if (isFavorite) {
                        mPresenter.cancelFavorite(mId, 1);// 取消收藏
                    } else {
                        mPresenter.insertFavorite(mBean);//收藏
                    }
                    break;
                case R.id.web_share://分享
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getTitle());
                    shareIntent.putExtra(Intent.EXTRA_TEXT, mUrl);
                    shareIntent.setType("text/plain");
                    startActivity(shareIntent);
                    break;
                case R.id.web_browser:
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(mUrl));
                    startActivity(intent);

                    break;
                    default:
                        break;
            }

            return false;
        });
    }

    @Override
    public void insertStatus(Long status) {
        if (status > 0) {
            mId = status;
            LogUtil.d("刚存入的  ID :" + mId);
            isFavorite = true;
            refreshFavoriteBtn();
            SnakbarUtil.favoriteSuccessView(mWebView, "收藏成功  -_-");


        } else {

            SnakbarUtil.favoriteFailView(mWebView, "收藏失败");
        }
    }

    @Override
    public void cancelStatus(Long id) {
        if (id < 0) {
            SnakbarUtil.favoriteFailView(mWebView, "取消收藏失败");
        } else {

            mId = id;
            isFavorite = false;
            refreshFavoriteBtn();
            LogUtil.d(TAG + "已取消收藏  ID :" + id);
            //      通知收藏列表更新
            MyApplication.getIntstance()
                         .bus()
                         .post(new UpdataFavorite(1));
            SnakbarUtil.favoriteSuccessView(mWebView, "已取消收藏  -_-");

        }
    }

    //这个方法不会使用
    @Override
    public void queryAllFavorite(List<FavoriteWebBean> list) {}

    @Override
    public void queryFavoriteIsCollect(List<FavoriteWebBean> list) {
        if (list.size() != 0) {
            LogUtil.d("已经存在了     ");
            isFavorite = true;
            refreshFavoriteBtn();
        }
    }

    //设置收藏图标
    public void refreshFavoriteBtn() {
        if (isFavorite) {
            mToolbar.getMenu()
                    .getItem(0)
                    .setIcon(R.drawable.ic_star_green_24dp);
        } else {
            mToolbar.getMenu()
                    .getItem(0)
                    .setIcon(R.drawable.ic_star_white_24dp);
        }
    }

    private void initData() {
        WebViewClient client = new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }


            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    view.loadUrl(url);
                    return false;
                } else {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                    return true;
                }
            }

        };


        WebChromeClient chromeClient = new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress < 100) {
                    mProgressBarWeb.setVisibility(View.VISIBLE);
                    mProgressBarWeb.setProgress(newProgress);

                } else if (newProgress == 100) {
                    mProgressBarWeb.setVisibility(View.GONE);
                }


            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                mToolbar.setTitle(title);

            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);

            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                super.onGeolocationPermissionsHidePrompt();

            }

            @Override
            public void onGeolocationPermissionsShowPrompt(final String origin,
                                                           final GeolocationPermissions.Callback callback)

            {
                callback.invoke(origin, false, false);
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public boolean onCreateWindow(WebView view,
                                          boolean isDialog,
                                          boolean isUserGesture,
                                          Message resultMsg)
            {
                WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
                transport.setWebView(view);
                resultMsg.sendToTarget();

                return true;
            }

        };

        mWebView = new WebView(this.getApplicationContext());
        mWebView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.MarginLayoutParams.MATCH_PARENT,
                                                            ViewGroup.LayoutParams.MATCH_PARENT));
        mContentWeb.addView(mWebView);
        WebSettings settings = mWebView.getSettings();
        // 基本设置
        settings.setSupportZoom(true);
        settings.setLoadWithOverviewMode(true);
        settings.setUseWideViewPort(true);
        settings.setDefaultTextEncodingName("utf-8");
        settings.setLoadsImagesAutomatically(true);
        settings.setJavaScriptEnabled(true);
        // 缓存数据
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setAppCacheEnabled(true);
        String appCachePath = getApplicationContext().getCacheDir()
                                                     .getAbsolutePath();
        settings.setAppCachePath(appCachePath);

        //html中的_bank标签就是新建窗口打开，有时会打不开，需要加以下
        //然后 复写 WebChromeClient的onCreateWindow方法
        settings.setSupportMultipleWindows(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.setWebChromeClient(chromeClient);
        mWebView.setWebViewClient(client);
        mWebView.loadUrl(mUrl);

    }

    // 防止内存泄漏

    public void clearWebViewResource() {
        if (mWebView != null) {
            mWebView.clearHistory();
            ((ViewGroup) mWebView.getParent()).removeView(mWebView);
            mWebView.setTag(null);
            mWebView.loadUrl("about:blank");
            mWebView.stopLoading();
            mWebView.setWebViewClient(null);
            mWebView.setWebChromeClient(null);
            mWebView.removeAllViews();
            mWebView.destroy();
            mWebView = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        clearWebViewResource();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
