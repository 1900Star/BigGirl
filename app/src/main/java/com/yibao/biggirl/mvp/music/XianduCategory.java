package com.yibao.biggirl.mvp.music;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/8/14 22:09
 */
import java.io.Serializable;

/**
 * Created by liyu on 2016/12/13.
 */

public class XianduCategory  implements Serializable{
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
