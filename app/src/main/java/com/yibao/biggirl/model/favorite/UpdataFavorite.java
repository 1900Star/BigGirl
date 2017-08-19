package com.yibao.biggirl.model.favorite;

/**
 * Author：Sid
 * Des：${更新收藏列表}
 * Time:2017/8/20 03:31
 */
public class UpdataFavorite {
    public UpdataFavorite(int updateFlage) {
        this.updateFlage = updateFlage;
    }

    public int getUpdateFlage() {
        return updateFlage;
    }

    private int updateFlage = 1;

}
