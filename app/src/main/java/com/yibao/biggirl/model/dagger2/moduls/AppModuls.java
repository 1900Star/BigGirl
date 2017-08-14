package com.yibao.biggirl.model.dagger2.moduls;

import com.yibao.biggirl.mvp.app.AppFag;
import com.yibao.biggirl.mvp.app.AppPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Author：Sid
 * Des：${2 : 用于存放对象创建的代码  将  new GirlsPresenter()代码放到指定类的指定方法中了}
 * Time:2017/6/8 01:11
 */
@Module
public class AppModuls {
    private AppFag mAppFragment;

    public AppModuls(AppFag appFragment) {
        this.mAppFragment = appFragment;
    }

    //
    @Provides
    public AppPresenter provideAppPresenter() {
        return new AppPresenter(mAppFragment);
    }

}
