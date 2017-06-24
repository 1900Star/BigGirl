package com.yibao.biggirl.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

/**
 * 作者：Stran on 2017/3/29 15:25
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseActivity
        extends AppCompatActivity
        implements OnRvItemClickListener
{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView(savedInstanceState);
    }

    protected abstract void initView(Bundle savedInstanceState );

    public abstract int getLayoutId();

    @Override
    public void showDetail(FavoriteBean bean,Long id ) {
        LogUtil.d("收藏的 Url    " + bean.getUrl());
        Intent intent = new Intent(this, WebActivity.class);
//        intent.putExtra("favoriteBean", bean);
        startActivity(intent);
    }

    @Override
    public void showBigGirl(int position, List<String> list) {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
