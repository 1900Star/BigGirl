package com.yibao.biggirl.mvp.dialogfragment;

import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseDialogFragment;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/31 18:37
 */
public class AboutMeDialogFag
        extends BaseDialogFragment
{


    public static AboutMeDialogFag newInstance() {
        return new AboutMeDialogFag();
    }

    @Override
    public View getViews() {
        return LinearLayout.inflate(getActivity(), R.layout.aboutme_dialog_fag, null);
    }

}
