package com.yibao.biggirl.model.all;

import java.util.List;

/**
 * Author：Sid
 * Des：${Gank.io  当天所有的数据 All}
 * Time:2017/6/11 14:53
 */
public class AllResultsBean {
    /**
     * _id : 5938c2dd421aa92c73b647d8
     * createdAt : 2017-06-08T11:22:05.993Z
     * desc : Vue-scroll-behavior：便捷定制vue应用导航路径上的滚动行为，尤其在 hash 模式下
     * publishedAt : 2017-06-08T11:27:47.21Z
     * source : web
     * type : 前端
     * url : https://github.com/jeneser/vue-scroll-behavior
     * used : true
     * who : yazhe wang
     * images : ["http://img.gank.io/56aca21b-3f5c-46fb-82d0-023e0c39010c"]
     */

    private String       _id;
    private String       createdAt;
    private String       desc;
    private String       publishedAt;
    private String       source;
    private String       type;
    private String       url;
    private boolean      used;
    private String       who;
    private List<String> images;

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
