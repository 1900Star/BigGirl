package com.yibao.biggirl.mvp.splash;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.widget.ImageView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.model.girls.GrilsDataSource;
import com.yibao.biggirl.model.girls.RemoteGirlsData;
import com.yibao.biggirl.mvp.main.MainActivity;
import com.yibao.biggirl.util.Constants;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.util.NetworkUtil;
import com.yibao.biggirl.util.SnakbarUtil;
import com.yibao.biggirl.util.SystemUiVisibilityUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 02:00
 */
public class SplashActivity
        extends AppCompatActivity {


    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    private Unbinder mBind;
    private Disposable mDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mBind = ButterKnife.bind(this);
        SystemUiVisibilityUtil.hideStatusBar(getWindow(), true);

        if (NetworkUtil.isNetworkConnected(this)) {
            mDisposable = Observable.timer(2, TimeUnit.SECONDS)
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
        if (mDisposable != null) {
            mDisposable.dispose();
            mDisposable = null;
        }
    }


}
