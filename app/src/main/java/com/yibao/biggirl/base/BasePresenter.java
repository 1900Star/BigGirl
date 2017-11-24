package com.yibao.biggirl.base;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/22 09:54
 */
public interface BasePresenter {
    //idCod有暂时没用
    void start(String type, int idCode);

    void subscribe();

    void unsubscribe();
}
