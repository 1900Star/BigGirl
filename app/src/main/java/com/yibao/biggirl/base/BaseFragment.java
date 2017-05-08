package com.yibao.biggirl.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * 作者：Stran on 2017/3/29 15:24
 * 描述：${TODO}
 * 邮箱：strangermy@outlook.com
 */
public abstract class BaseFragment
        extends Fragment
{

    protected BaseActivity mActivity;
    public LoadingPager mLoadingPager;

    //获取fragment布局文件ID
    protected abstract int getLayoutId();

    //获取宿主Activity
    protected BaseActivity getHoldingActivity() {
        return mActivity;
    }


    //添加fragment
    protected void addFragment(BaseFragment fragment) {
        if (null != fragment) {
            getHoldingActivity().addFragment(fragment);
        }
    }



    protected abstract void init();

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState)
    {


        View view = inflater.inflate(getLayoutId(), container, false);
        initView(view, savedInstanceState);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        initListener();
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    protected abstract void initView(View view, Bundle savedInstanceState);

    protected abstract void initData();

    protected abstract void initListener();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

}
