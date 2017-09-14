package com.yibao.biggirl.model.music;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/9/14 00:58
 */
public class MusicLrcBean {
    private int startTime;
    private String content;

    public MusicLrcBean() {
    }

    public MusicLrcBean(int startTime, String content) {
        this.startTime = startTime;
        this.content = content;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
