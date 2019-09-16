package com.yibao.biggirl.base;

/**
 * @项目名： BigGirl
 * @包名： com.yibao.biggirl.base
 * @文件名: BaseActivity
 * @创建者: Stran
 * @创建时间: 2017/12/29 20:56
 * @描述： TODO
 */

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import com.yibao.biggirl.MyApplication;
import com.yibao.biggirl.base.listener.OnRvItemClickListener;
import com.yibao.biggirl.base.listener.OnRvItemLongClickListener;

import io.reactivex.disposables.CompositeDisposable;

/**
 * @author Stran
 */
public abstract class BaseActivity extends AppCompatActivity implements OnRvItemClickListener, OnRvItemLongClickListener {
    public CompositeDisposable mDisposable;
    public MyApplication mApplication;
    public String mTag = getClass().getSimpleName() + "    ";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
        mApplication = MyApplication.getIntstance();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(this.getClass().getSimpleName(), "onDestroy");
        if (mDisposable != null) {
            mDisposable.clear();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(this.getClass().getSimpleName(), "onStop");
    }

}
