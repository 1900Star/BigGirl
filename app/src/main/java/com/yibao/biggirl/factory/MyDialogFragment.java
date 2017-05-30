package com.yibao.biggirl.factory;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.util.ImageUitl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/30 13:27
 */
public class MyDialogFragment
        extends DialogFragment
{

    @BindView(R.id.dialog_bigview_content)
    LinearLayout mDialogBigviewContent;
    Unbinder unbinder;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState)
    {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow()
                   .setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mView = inflater.inflate(R.layout.dialog_big_girl, null);
        unbinder = ButterKnife.bind(this, mView);
        initView();
        return mView;
    }

    private void initView() {

//                ImageView     view = ImageUitl.creatZoomView(getActivity());
        ImageView view = new ImageView(getActivity());
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                                                   ViewGroup.LayoutParams.MATCH_PARENT);
        view.setScaleType(ImageView.ScaleType.CENTER_CROP);

        view.setLayoutParams(params);
        ImageUitl.loadPic(getActivity(), Api.picUrlArr[10], view);

        mDialogBigviewContent.addView(view);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
