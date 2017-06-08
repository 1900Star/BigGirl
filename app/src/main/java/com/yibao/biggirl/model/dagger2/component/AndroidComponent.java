package com.yibao.biggirl.model.dagger2.component;

import com.yibao.biggirl.model.dagger2.moduls.AndroidModuls;
import com.yibao.biggirl.mvp.android.AndroidFragment;

import dagger.Component;

/**
 * Author：Sid
 * Des：${3 : 通过接口将创建实例的代码关联}
 * Time:2017/6/8 01:17
 */
@Component(modules = AndroidModuls.class)
public interface AndroidComponent {
    void in(AndroidFragment androidFragment);
}
