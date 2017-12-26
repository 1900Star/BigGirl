package com.yibao.biggirl.mvp.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.yibao.biggirl.R
import kotlinx.android.synthetic.main.kot.*


/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.mvp.main
 *  @文件名:   Kot
 *  @创建者:   Stran
 *  @创建时间:  2017/12/3 23:13
 *  @描述：    TODO
 */
 class Kot : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.kot)
        kot_iv.setImageResource(R.mipmap.xuan)

    }


}