package com.yibao.biggirl.mvp.girl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.FrameLayout;

import com.yibao.biggirl.R;
import com.yibao.biggirl.network.Api;
import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;
import com.yibao.biggirl.view.ZoomImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class BigGrilActivity
        extends AppCompatActivity
        implements View.OnClickListener
{


    @BindView(R.id.fl_biggirl_content)
    FrameLayout mBiggirlContent;
    private Unbinder mBind;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_big_gril);
        mBind = ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();

        int           position = extras.getInt("position");
        ZoomImageView view     = new ZoomImageView(this);
        ImageUitl.loadPic(this, Api.picUrlArr[position], view);
        mBiggirlContent.addView(view);
        view.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        LogUtil.d("我的监听事件");
        finish();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
    }
}
