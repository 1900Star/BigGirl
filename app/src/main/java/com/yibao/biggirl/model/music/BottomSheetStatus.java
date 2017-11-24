package com.yibao.biggirl.model.music;

/**
 * Author：Sid
 * Des：${用于BottomSheetAdapter中,item点击播放音乐}
 * Time:2017/8/13 06:53
 */
public class BottomSheetStatus {
    /**
     * type 用来表示播放音乐的标记
     */

    public int type;

    public BottomSheetStatus(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }

}
