package com.yibao.biggirl.model.dagger2.component;

import com.yibao.biggirl.model.dagger2.moduls.FavoriteModuls;
import com.yibao.biggirl.mvp.favorite.FavoriteFag;

import dagger.Component;

/**
 * Author：Sid
 * Des：${3 : 通过接口将创建实例的代码关联}
 * Time:2017/6/8 01:17
 */
@Component(modules = FavoriteModuls.class)
public interface FavoriteComponent {
    void in(FavoriteFag favoriteFag);
}
