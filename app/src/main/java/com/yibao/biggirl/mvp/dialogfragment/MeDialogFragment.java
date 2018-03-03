package com.yibao.biggirl.mvp.dialogfragment;

import android.view.View;
import android.widget.LinearLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.base.BaseDialogFragment;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/30 13:27
 * @author Stran
 */
public class MeDialogFragment
        extends BaseDialogFragment
{

    private View mView;

    @Override
    public View getViews() {
        mView = LinearLayout.inflate(getActivity(), R.layout.me_dialog_fag, null);
        initView();
        return mView;
    }

    public static MeDialogFragment newInstance() {

        return new MeDialogFragment();
    }

    private void initView() {
        LinearLayout topPicContent = (LinearLayout) mView.findViewById(R.id.me_content);


    }
}
