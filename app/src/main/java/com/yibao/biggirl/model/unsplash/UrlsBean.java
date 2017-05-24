package com.yibao.biggirl.model.unsplash;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/21 14:27
 */
public class UrlsBean {
    /**
     * raw : https://images.unsplash.com/photo-1495250357898-6822052ef5b8
     * full : https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=5c95ab038b9a7007cc09664b98e70f67
     * regular : https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=67b77166a7e4268dad7aa6c737f7d09d
     * small : https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=86028cb24c231a2b6981e7af2ef516f8
     * thumb : https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=e2d6b928e999110c454ab57b3c0feb79
     */

    private String raw;
    private String full;
    private String regular;
    private String small;
    private String thumb;

    public String getRaw() { return raw;}

    public void setRaw(String raw) { this.raw = raw;}

    public String getFull() { return full;}

    public void setFull(String full) { this.full = full;}

    public String getRegular() { return regular;}

    public void setRegular(String regular) { this.regular = regular;}

    public String getSmall() { return small;}

    public void setSmall(String small) { this.small = small;}

    public String getThumb() { return thumb;}

    public void setThumb(String thumb) { this.thumb = thumb;}
}
