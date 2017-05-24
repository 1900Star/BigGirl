package com.yibao.biggirl.model.unsplash;

/**
 * Author：Sid
 * Des：${TODO}
 * Time:2017/5/21 14:27
 */
public class UserBean {
    /**
     * id : fbPZwdKgWWs
     * updated_at : 2017-05-21T02:20:29-04:00
     * username : tidesinourveins
     * name : Jeremy Bishop
     * first_name : Jeremy
     * last_name : Bishop
     * portfolio_url : null
     * bio : @tidesinourveins Global Expeditions | Surf Photography | Adventure & Brand photography
     * location : Los Angeles, CA
     * total_likes : 2212
     * total_photos : 511
     * total_collections : 10
     * profile_image : {"small":"https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=32&w=32&s=8bb5a4dcbbe82648e3c78eb143435886","medium":"https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=64&w=64&s=164eaa85bd58a3e71871d0387246310c","large":"https://images.unsplash.com/profile-1475405901109-04b2f633a548?ixlib=rb-0.3.5&q=80&fm=jpg&crop=faces&cs=tinysrgb&fit=crop&h=128&w=128&s=07596ffc8a1763674708a2d8500f2427"}
     * links : {"self":"https://api.unsplash.com/users/tidesinourveins","html":"http://unsplash.com/@tidesinourveins","photos":"https://api.unsplash.com/users/tidesinourveins/photos","likes":"https://api.unsplash.com/users/tidesinourveins/likes","portfolio":"https://api.unsplash.com/users/tidesinourveins/portfolio","following":"https://api.unsplash.com/users/tidesinourveins/following","followers":"https://api.unsplash.com/users/tidesinourveins/followers"}
     */

    private String id;
    private String           updated_at;
    private String           username;
    private String           name;
    private String           first_name;
    private String           last_name;
    private Object           portfolio_url;
    private String           bio;
    private String           location;
    private int              total_likes;
    private int              total_photos;
    private int              total_collections;
    private ProfileImageBean profile_image;
    private LinksBean        links;

    public String getId() { return id;}

    public void setId(String id) { this.id = id;}

    public String getUpdated_at() { return updated_at;}

    public void setUpdated_at(String updated_at) { this.updated_at = updated_at;}

    public String getUsername() { return username;}

    public void setUsername(String username) { this.username = username;}

    public String getName() { return name;}

    public void setName(String name) { this.name = name;}

    public String getFirst_name() { return first_name;}

    public void setFirst_name(String first_name) { this.first_name = first_name;}

    public String getLast_name() { return last_name;}

    public void setLast_name(String last_name) { this.last_name = last_name;}

    public Object getPortfolio_url() { return portfolio_url;}

    public void setPortfolio_url(Object portfolio_url) { this.portfolio_url = portfolio_url;}

    public String getBio() { return bio;}

    public void setBio(String bio) { this.bio = bio;}

    public String getLocation() { return location;}

    public void setLocation(String location) { this.location = location;}

    public int getTotal_likes() { return total_likes;}

    public void setTotal_likes(int total_likes) { this.total_likes = total_likes;}

    public int getTotal_photos() { return total_photos;}

    public void setTotal_photos(int total_photos) { this.total_photos = total_photos;}

    public int getTotal_collections() { return total_collections;}

    public void setTotal_collections(int total_collections) { this.total_collections = total_collections;}

    public ProfileImageBean getProfile_image() { return profile_image;}

    public void setProfile_image(ProfileImageBean profile_image) { this.profile_image = profile_image;}

    public LinksBean getLinks() { return links;}

    public void setLinks(LinksBean links) { this.links = links;}
}
