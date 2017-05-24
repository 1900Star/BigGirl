package com.yibao.biggirl.model.unsplash;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/21 14:27
 */
class LinksBean {
    /**
     * self : https://api.unsplash.com/users/tidesinourveins
     * html : http://unsplash.com/@tidesinourveins
     * photos : https://api.unsplash.com/users/tidesinourveins/photos
     * likes : https://api.unsplash.com/users/tidesinourveins/likes
     * portfolio : https://api.unsplash.com/users/tidesinourveins/portfolio
     * following : https://api.unsplash.com/users/tidesinourveins/following
     * followers : https://api.unsplash.com/users/tidesinourveins/followers
     */

    private String self;
    private String html;
    private String photos;
    private String likes;
    private String portfolio;
    private String following;
    private String followers;

    public String getSelf() { return self;}

    public void setSelf(String self) { this.self = self;}

    public String getHtml() { return html;}

    public void setHtml(String html) { this.html = html;}

    public String getPhotos() { return photos;}

    public void setPhotos(String photos) { this.photos = photos;}

    public String getLikes() { return likes;}

    public void setLikes(String likes) { this.likes = likes;}

    public String getPortfolio() { return portfolio;}

    public void setPortfolio(String portfolio) { this.portfolio = portfolio;}

    public String getFollowing() { return following;}

    public void setFollowing(String following) { this.following = following;}

    public String getFollowers() { return followers;}

    public void setFollowers(String followers) { this.followers = followers;}
}
