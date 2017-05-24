package com.yibao.biggirl.model.unsplash;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/21 14:27
 */
class ProfileImageBean {
    /**
     * small : https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=8bb5a4dcbbe82648e3c78eb143435886
     * medium : https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=164eaa85bd58a3e71871d0387246310c
     * large : https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=07596ffc8a1763674708a2d8500f2427
     */

    private String small;
    private String medium;
    private String large;

    public String getSmall() { return small;}

    public void setSmall(String small) { this.small = small;}

    public String getMedium() { return medium;}

    public void setMedium(String medium) { this.medium = medium;}

    public String getLarge() { return large;}

    public void setLarge(String large) { this.large = large;}
}
