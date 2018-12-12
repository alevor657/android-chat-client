package com.example.yooo.ultimatechat;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String profilePictureUrl;

    User(String username, String profilePictureUrl)
    {
        this.username = username;
        this.profilePictureUrl = profilePictureUrl;
    }

//    User()
//    {
//        this.username = username;
//        this.profilePictureUrl = profilePictureUrl;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }
}
