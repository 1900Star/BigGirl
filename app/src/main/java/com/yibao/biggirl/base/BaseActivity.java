package com.yibao.biggirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 作者：Stran on 2017/3/29 15:25
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseActivity
        extends AppCompatActivity

{

    private Unbinder mBind;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mBind = ButterKnife.bind(this);
        initInject();
        initView();
        initData();
        initListener();
    }

    protected abstract void initInject();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void initData();

    public abstract int getLayoutId();


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();

    }
}
