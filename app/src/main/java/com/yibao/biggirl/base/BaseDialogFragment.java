package com.yibao.biggirl.base;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.yibao.biggirl.util.SystemUiVisibilityUtil;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/31 18:50
 */
public abstract class BaseDialogFragment
        extends DialogFragment
{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow()
                   .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        return getViews();
    }


    public abstract View getViews();

}
