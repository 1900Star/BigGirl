package com.yibao.biggirl.model.dagger2.component;

import com.yibao.biggirl.model.dagger2.moduls.WebModuls;
import com.yibao.biggirl.mvp.webview.WebActivity;

import dagger.Component;

/**
 * Author：Sid
 * Des：${3 : 通过接口将创建实例的代码关联}
 * Time:2017/6/8 01:17
 */
@Component(modules = WebModuls.class)
public interface WebComponent {
    void in(WebActivity webActivity);
}
