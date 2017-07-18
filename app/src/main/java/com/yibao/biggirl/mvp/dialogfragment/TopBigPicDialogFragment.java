package com.yibao.biggirl.mvp.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseDialogFragment;
import com.yibao.biggirl.util.AnimaUtil;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.view.ZoomImageView;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/30 13:27
 */
public class TopBigPicDialogFragment
        extends BaseDialogFragment
{

    private View mView;

    @Override
    public View getViews() {
        mView = LinearLayout.inflate(getActivity(), R.layout.dialog_big_girl, null);
        initView();
        return mView;
    }

    public static TopBigPicDialogFragment newInstance(String url) {
        TopBigPicDialogFragment fragment = new TopBigPicDialogFragment();
        Bundle                  bundle   = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);//把参数传递给该DialogFragment
        return fragment;
    }

    private void initView() {
        LinearLayout topPicContent = (LinearLayout) mView.findViewById(R.id.top_big_pic_content);

        ZoomImageView view = ImageUitl.creatZoomView(getActivity());
        String        url  = getArguments().getString("url");
        ImageUitl.loadPic(getActivity(), url, view);
        view.setOnClickListener(view1 -> TopBigPicDialogFragment.this.dismiss());
        AnimaUtil.applyBobbleAnim(topPicContent);
        topPicContent.addView(view);


    }
}
