package com.yibao.biggirl;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.yibao.biggirl.util.ImageUitl;
import com.yibao.biggirl.util.LogUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main2Activity
        extends AppCompatActivity
{

    @BindView(R.id.iv_test)
    ImageView mIvTest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);
        mIvTest.setOnClickListener(view -> {
            ImageUitl.downloadPic( "http://mvimg2.meitudata.com/576d550bbaf204850.jpg",true);
            LogUtil.d("我来了！");

        });

    }


}
