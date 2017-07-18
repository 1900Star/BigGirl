package com.yibao.biggirl.mvp.favorite;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.listener.OnDeleteItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.model.favorite.FavoriteBean;
import com.yibao.biggirl.mvp.video.VideoFragmnets;
import com.yibao.biggirl.mvp.webview.WebActivity;
import com.yibao.biggirl.util.ActivityUtils;
import com.yibao.biggirl.util.LogUtil;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/6/17 18:45
 */
public class FavoriteActivity
        extends AppCompatActivity implements OnRvItemClickListener,OnDeleteItemClickListener
{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activty_gril);
        if (savedInstanceState == null) {
            initData();
        }
    }

    private void initData() {
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("收藏");
        bar.setDisplayHomeAsUpEnabled(true);

        FavoriteFag favoriteFag = (FavoriteFag) getSupportFragmentManager().findFragmentById(R.id.content_girl_activity);


        if (favoriteFag == null) {

            favoriteFag = new FavoriteFag().newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                                                favoriteFag,
                                                R.id.content_girl_activity);
        }
    }



    private void initDatas() {
        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setTitle("收藏");
        bar.setDisplayHomeAsUpEnabled(true);

        VideoFragmnets favoriteFag = (VideoFragmnets) getSupportFragmentManager().findFragmentById(R.id.content_girl_activity);


        if (favoriteFag == null) {

            favoriteFag = new VideoFragmnets().newInstance();

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                                                favoriteFag,
                                                R.id.content_girl_activity);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                 break;
            default:
                 break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();


    }

    @Override
    public void showDetail(FavoriteBean bean,Long id) {
        Intent intent = new Intent(this, WebActivity.class);
        intent.putExtra("id", id);
        LogUtil.d("跳转 ID :" + bean.getId());
        intent.putExtra("favoriteBean", bean);
        startActivity(intent);
    }

    @Override
    public void showBigGirl(int position, List<String> list) {

    }

    @Override
    public void deleteFavorite(Long id) {


    }
}
