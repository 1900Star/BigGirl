package com.yibao.biggirl.model.dagger2.moduls;

import com.yibao.biggirl.mvp.android.AndroidFragment;

import dagger.Module;

/**
 * Author：Sid
 * Des：${2 : 用于存放对象创建的代码  将  new GirlsPresenter()代码放到指定类的指定方法中了}
 * Time:2017/6/8 01:11
 */
@Module
public class AndroidModuls {
    private AndroidFragment mAndroidFragment;

    public AndroidModuls(AndroidFragment androidFragment) {
        this.mAndroidFragment = androidFragment;
    }

    //
//    @Provides
//    public AndroidPresenter provideAndroidPresenter() {
//        return new AndroidPresenter(mAndroidFragment);
//    }

}
