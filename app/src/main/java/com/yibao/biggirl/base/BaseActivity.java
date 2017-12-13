package com.yibao.biggirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yibao.biggirl.MyApplication;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 作者：Stran on 2017/3/29 15:25
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseActivity
        extends AppCompatActivity

{

    public CompositeDisposable mDisposable;
    public MyApplication mApplication;
    public String TAG = getClass().getSimpleName() + "    ";
    public int size = 20;
    public int page = 1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDisposable = new CompositeDisposable();
        mApplication = MyApplication.getIntstance();
    }


    //找到数组中的最大值
    public int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposable != null) {
            mDisposable.clear();
        }

    }
}
