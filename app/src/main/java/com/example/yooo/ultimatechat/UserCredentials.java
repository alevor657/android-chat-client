package com.example.yooo.ultimatechat;

import android.view.View;
import android.widget.EditText;

import java.io.Serializable;

public class UserCredentials {
    private static UserCredentials instance;
    private String username;
    private String password;
    private String email;
    private String token;
    private String profilePictureUrl;

    public UserCredentials() { }

    public static synchronized UserCredentials getInstance() {
        if (instance == null) {
            instance = new UserCredentials();
        }
        return instance;
    }

    public static synchronized void setInstance(UserCredentials i) {
        instance = i;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        instance.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        instance.token = token;
    }

    public String getProfilePictureUrl() {
        return profilePictureUrl;
    }

    public void setProfilePictureUrl(String profilePictureUrl) {
        this.profilePictureUrl = profilePictureUrl;
    }

    public void setUsername(String username) {
        instance.username = username;
    }

    public void setPassword(String password) {
        instance.password = password;
    }
}
