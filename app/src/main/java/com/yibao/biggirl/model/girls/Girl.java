package com.yibao.biggirl.model.girls;

/*
 *  @项目名：  BigGirl 
 *  @包名：    com.yibao.biggirl.model.girls
 *  @文件名:   Girl
 *  @创建者:   Stran
 *  @创建时间:  2017/12/5 2:15
 *  @描述：    TODO
 */

public class Girl  {
    private String url;
    private int width = 0;
    private int height = 0;
    private String link;//仅妹子图网站需要

    public Girl(String url) {
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
