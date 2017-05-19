package com.yibao.biggirl.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

/**
 * 作者：Stran on 2017/3/29 15:25
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseActivity
        extends AppCompatActivity
{


    protected abstract int getLayoutId();

    protected abstract int getFragmentContentId();
    public abstract void initView();
    public abstract void initData();
    protected abstract BaseFragment getFragment();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        initView();
        initData();
//        addFragment(getFragment());
    }



    //添加fragment
    protected void addFragment(BaseFragment fragment) {
//        if (fragment != null) {
//            getFragmentManager().beginTransaction()
//                                .replace(getFragmentContentId(),
//                                         fragment,
//                                         fragment.getClass()
//                                                 .getSimpleName())
//                                .addToBackStack(fragment.getClass()
//                                                        .getSimpleName())
//                                .commitAllowingStateLoss();
//
//
//        }
    }

    protected void removeFragment() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();

        } else {
            finish();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == keyCode) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 1) {
                finish();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }



}
