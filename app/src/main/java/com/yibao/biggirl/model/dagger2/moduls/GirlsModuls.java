package com.yibao.biggirl.model.dagger2.moduls;

import com.yibao.biggirl.mvp.gank.girls.GirlsContract;
import com.yibao.biggirl.mvp.gank.girls.GirlsFragment;
import com.yibao.biggirl.mvp.gank.girls.GirlsPresenter;

import dagger.Module;
import dagger.Provides;

/**
 * Author：Sid
 * Des：${2 : 用于存放对象创建的代码  将  new GirlsPresenter()代码放到指定类的指定方法中了}
 * Time:2017/6/8 01:11
 */
@Module
public class GirlsModuls {
    private GirlsContract.View mView;

    public GirlsModuls(GirlsFragment view) {
        this.mView = view;
    }

    //
    @Provides
    public GirlsPresenter provideGirlsPresenter() {
        return new GirlsPresenter(mView);
    }

}
