package com.yibao.biggirl.base;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import com.yibao.biggirl.MyApplication;


/**
 * 作者：Stran on 2017/3/29 15:24
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseFragment
        extends Fragment
{

    public LoadingPager mLoadingPager;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {
        if (mLoadingPager == null) {
            mLoadingPager = new LoadingPager(MyApplication.getIntstance()) {
                @Override
                public LoadedResult initData() {
                    return BaseFragment.this.initData();
                }

                @Override
                public View initSuccessView() {
                    return BaseFragment.this.initSuccessView();
                }
            };
        } else {
            //低版本会出现一个问题,需要加上如下代码-->在2.3的系统,不过现在还有安卓4.4以下的手机吗？
            ViewParent parent = mLoadingPager.getParent();
            if (parent != null && parent instanceof ViewGroup) {
                ((ViewGroup) parent).removeView(mLoadingPager);
            }
        }


        return mLoadingPager;
    }


    protected abstract LoadingPager.LoadedResult initData();

    protected abstract View initSuccessView();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
