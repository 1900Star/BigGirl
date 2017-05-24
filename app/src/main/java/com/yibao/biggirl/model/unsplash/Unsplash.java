package com.yibao.biggirl.model.unsplash;

import java.util.List;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/21 14:14
 */
public class Unsplash {

    /**
     * id : GoEWA8YCQJ0
     * created_at : 2017-05-19T23:20:52-04:00
     * updated_at : 2017-05-21T02:20:29-04:00
     * width : 5472
     * height : 3648
     * color : #E0E2E4
     * likes : 127
     * liked_by_user : false
     * user : {"id":"fbPZwdKgWWs","updated_at":"2017-05-21T02:20:29-04:00","username":"tidesinourveins","name":"Jeremy Bishop","first_name":"Jeremy","last_name":"Bishop","portfolio_url":null,"bio":"@tidesinourveins Global Expeditions | Surf Photography | Adventure & Brand photography ","location":"Los Angeles, CA","total_likes":2212,"total_photos":511,"total_collections":10,"profile_image":{"small":"https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=8bb5a4dcbbe82648e3c78eb143435886","medium":"https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=164eaa85bd58a3e71871d0387246310c","large":"https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=07596ffc8a1763674708a2d8500f2427"},"links":{"self":"https://api.unsplash.com/users/tidesinourveins","html":"http://unsplash.com/@tidesinourveins","photos":"https://api.unsplash.com/users/tidesinourveins/photos","likes":"https://api.unsplash.com/users/tidesinourveins/likes","portfolio":"https://api.unsplash.com/users/tidesinourveins/portfolio","following":"https://api.unsplash.com/users/tidesinourveins/following","followers":"https://api.unsplash.com/users/tidesinourveins/followers"}}
     * current_user_collections : []
     * urls : {"raw":"https://images.unsplash.com/photo-1495250357898-6822052ef5b8","full":"https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=85&fm=jpg&crop=entropy&cs=srgb&s=5c95ab038b9a7007cc09664b98e70f67","regular":"https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=1080&fit=max&s=67b77166a7e4268dad7aa6c737f7d09d","small":"https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=400&fit=max&s=86028cb24c231a2b6981e7af2ef516f8","thumb":"https://images.unsplash.com/photo-1495250357898-6822052ef5b8?ixlib=rb-0.3.5&q=80&fm=jpg&crop=entropy&cs=tinysrgb&w=200&fit=max&s=e2d6b928e999110c454ab57b3c0feb79"}
     * categories : []
     * links : {"self":"https://api.unsplash.com/photos/GoEWA8YCQJ0","html":"http://unsplash.com/photos/GoEWA8YCQJ0","download":"http://unsplash.com/photos/GoEWA8YCQJ0/download","download_location":"https://api.unsplash.com/photos/GoEWA8YCQJ0/download"}
     */

    private String id;
    private String     created_at;
    private String     updated_at;
    private int        width;
    private int        height;
    private String     color;
    private int        likes;
    private boolean    liked_by_user;
    private UserBean   user;
    private UrlsBean   urls;
    private LinksBeanX links;
    private List<?>    current_user_collections;
    private List<?>    categories;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getCreated_at() { return created_at;}

    public void setCreated_at(String created_at) { this.created_at = created_at;}

    public String getUpdated_at() { return updated_at;}

    public void setUpdated_at(String updated_at) { this.updated_at = updated_at;}

    public int getWidth() { return width;}

    public void setWidth(int width) { this.width = width;}

    public int getHeight() { return height;}

    public void setHeight(int height) { this.height = height;}

    public String getColor() { return color;}

    public void setColor(String color) { this.color = color;}

    public int getLikes() { return likes;}

    public void setLikes(int likes) { this.likes = likes;}

    public boolean isLiked_by_user() { return liked_by_user;}

    public void setLiked_by_user(boolean liked_by_user) { this.liked_by_user = liked_by_user;}

    public UserBean getUser() { return user;}

    public void setUser(UserBean user) { this.user = user;}

    public UrlsBean getUrls() { return urls;}

    public void setUrls(UrlsBean urls) { this.urls = urls;}

    public LinksBeanX getLinks() { return links;}

    public void setLinks(LinksBeanX links) { this.links = links;}

    public List<?> getCurrent_user_collections() { return current_user_collections;}

    public void setCurrent_user_collections(List<?> current_user_collections) { this.current_user_collections = current_user_collections;}

    public List<?> getCategories() { return categories;}

    public void setCategories(List<?> categories) { this.categories = categories;}
}
