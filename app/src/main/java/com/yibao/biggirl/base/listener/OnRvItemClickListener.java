package com.yibao.biggirl.base.listener;

import com.yibao.biggirl.model.favorite.FavoriteBean;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/14 09:49
 */
public interface OnRvItemClickListener {

    void showDetail(FavoriteBean bean, Long id);

    void showBigGirl(int position, List<String> list);
}
