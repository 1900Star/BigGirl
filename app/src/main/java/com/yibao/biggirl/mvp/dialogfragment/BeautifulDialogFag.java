package com.yibao.biggirl.mvp.dialogfragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.yibao.biggirl.R;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.webview.WebViewActivity;

/**
 * Author：Sid
 * Des：${打开漂亮Girl的视频}
 * Time:2017/5/31 18:37
 */
public class BeautifulDialogFag
        extends DialogFragment
{


    private View mView;

    public static BeautifulDialogFag newInstance() {
        return new BeautifulDialogFag();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        mView = inflater.inflate(R.layout.beautiful_dialog_fag, null);
        builder.setView(mView);
        initView();
        return builder.create();
    }

//        @Override
//        public View getViews() {
//            mView = LinearLayout.inflate(getActivity(), R.layout.beautiful_dialog_fag, null);
//            initView();
//            return mView;
//        }


    private void initView() {
        TextView feir = (TextView) mView.findViewById(R.id.tv_feir);
        TextView xuan = (TextView) mView.findViewById(R.id.tv_fxx);
        feir.setOnClickListener(view1 -> showDesDetall(getActivity(), Api.myFeir));
        xuan.setOnClickListener(view12 -> showDesDetall(getActivity(), Api.myXXuan));
    }

    private void showDesDetall(Context context, String url) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", url);
        context.startActivity(intent);


    }
}
