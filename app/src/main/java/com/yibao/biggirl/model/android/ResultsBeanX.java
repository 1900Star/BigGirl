package com.yibao.biggirl.model.android;

import com.google.gson.Gson;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/4/23 08:41
 */
public class ResultsBeanX {
    /**
     * _id : 58f8b3b7421aa9544ed889a6
     * createdAt : 2017-04-20T21:12:23.526Z
     * desc : 支持搜索的spinner
     * images : ["http://img.gank.io/07318b56-1902-4b4d-878e-c9bbcf9babbb"]
     * publishedAt : 2017-04-21T11:30:29.323Z
     * source : chrome
     * type : Android
     * url : https://github.com/miteshpithadiya/SearchableSpinner
     * used : true
     * who : galois
     */

    private String _id;
    private String       createdAt;
    private String       desc;
    private String       publishedAt;
    private String       source;
    private String       type;
    private String       url;
    private boolean      used;
    private String       who;
    private List<String> images;

    public static ResultsBeanX objectFromData(String str) {

        return new Gson().fromJson(str, ResultsBeanX.class);
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

    public List<String> getImages() { return images;}

    public void setImages(List<String> images) { this.images = images;}
}
