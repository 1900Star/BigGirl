package com.yibao.biggirl.mvp.dialogfragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseDialogFragment;
import com.yibao.biggirl.util.ImageUitl;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/31 18:37
 */
public class AboutMeDialogFag
        extends BaseDialogFragment {


    private View mView;

    public static AboutMeDialogFag newInstance(String url) {
        AboutMeDialogFag fag = new AboutMeDialogFag();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fag.setArguments(bundle);
        return fag;
    }

    @Override
    public View getViews() {

        mView = LinearLayout.inflate(getActivity(), R.layout.aboutme_dialog_fag, null);
        initView();
        return mView;
    }

    private void initView() {
        String url = getArguments().getString("url");
        ImageView headerView = mView.findViewById(R.id.about_header_iv);
        ImageUitl.loadPic(getActivity(), url, headerView);


    }

}
