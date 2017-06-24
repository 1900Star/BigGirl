package com.yibao.biggirl.model.girl;

/**
 * Author：Sid
 * Des：${封装图片下载进度}
 * Time:2017/4/24 13:11
 */
public class DownGrilProgressData {
    private int progress;
    private int type;

    public DownGrilProgressData(int progress, int type) {
        this.progress = progress;
        this.type = type;
    }

    public int getProgress() {
        return progress;
    }

    public int getType() {
        return type;
    }
}
