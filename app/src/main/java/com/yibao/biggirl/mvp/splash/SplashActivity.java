package com.yibao.biggirl.mvp.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yibao.biggirl.MainActivity;
import com.yibao.biggirl.R;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.util.SystemUiVisibilityUtil;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 02:00
 */
public class SplashActivity
        extends AppCompatActivity
{


    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    //    private String url = "http://imgsrc.baidu.com/baike/pic/item/a044ad345982b2b78714197432adcbef77099bf2.jpg";
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBind = ButterKnife.bind(this);
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);
        if (NetworkUtil.isNetworkConnected(this)) {
            Observable.timer(2, TimeUnit.SECONDS)
                      .subscribeOn(Schedulers.io())
                      .observeOn(AndroidSchedulers.mainThread())
                      .subscribe(aLong -> {
                          SplashActivity.this.startActivity(new Intent(SplashActivity.this,
                                                                       MainActivity.class));
                          finish();
                      });

        } else {

            SnakbarUtil.netErrorsLong(mIvSplash);
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }


}
