package com.example.yooo.ultimatechat;

import android.view.View;
import android.widget.EditText;

public class UserCredentials {
    private String username;
    private String password;

    public UserCredentials(View usernameViev, View passwordView) {
        this.username = ((EditText) usernameViev).getText().toString();
        this.password = ((EditText) passwordView).getText().toString();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
