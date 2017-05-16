package com.yibao.biggirl.model.video;

import com.google.gson.Gson;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/9 06:29
 */
public class VideoResultsBean {
    /**
     * _id : 590f2780421aa90c7fefdd58
     * createdAt : 2017-05-07T21:56:16.410Z
     * desc : 【阿斗】神秘金字塔惊现世界，暗藏可怕生物！速看恐怖片《夺命金字塔》
     * publishedAt : 2017-05-08T11:22:01.540Z
     * source : chrome
     * type : 休息视频
     * url : http://www.bilibili.com/video/av10389462/
     * used : true
     * who : LHF
     */

    private String _id;
    private String  createdAt;
    private String  desc;
    private String  publishedAt;
    private String  source;
    private String  type;
    private String  url;
    private boolean used;
    private String  who;

    public static VideoResultsBean objectFromData(String str) {

        return new Gson().fromJson(str, VideoResultsBean.class);
    }

    public String get_id() { return _id;}

    public void set_id(String _id) { this._id = _id;}

    public String getCreatedAt() { return createdAt;}

    public void setCreatedAt(String createdAt) { this.createdAt = createdAt;}

    public String getDesc() { return desc;}

    public void setDesc(String desc) { this.desc = desc;}

    public String getPublishedAt() { return publishedAt;}

    public void setPublishedAt(String publishedAt) { this.publishedAt = publishedAt;}

    public String getSource() { return source;}

    public void setSource(String source) { this.source = source;}

    public String getType() { return type;}

    public void setType(String type) { this.type = type;}

    public String getUrl() { return url;}

    public void setUrl(String url) { this.url = url;}

    public boolean isUsed() { return used;}

    public void setUsed(boolean used) { this.used = used;}

    public String getWho() { return who;}

    public void setWho(String who) { this.who = who;}
}
