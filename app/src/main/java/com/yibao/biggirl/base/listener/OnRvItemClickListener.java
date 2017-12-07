package com.yibao.biggirl.base.listener;

import com.yibao.biggirl.model.favoriteweb.FavoriteWebBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/14 09:49
 */
public interface OnRvItemClickListener<T> {

    void showDetail(FavoriteWebBean bean, Long id);

    void showBigGirl(int position, List<T> list);
}
