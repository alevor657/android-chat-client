package com.example.yooo.ultimatechat;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

public class CredentialsStorage {
    Context ctx;

    public CredentialsStorage(Context ctx) {
        this.ctx = ctx;
    }

    public void persistUserData(UserCredentials creds) {
        SharedPreferences prefs = ctx.getSharedPreferences("currentUser", ctx.MODE_PRIVATE);

        if (prefs.contains("userCredentials")) {
            return;
        }

        SharedPreferences.Editor ed = ctx.getSharedPreferences("currentUser", ctx.MODE_PRIVATE).edit();
        ed.putString("userCredentials", new Gson().toJson(creds));
        ed.commit();
    }

    public void clearUserData() {
        SharedPreferences prefs = ctx.getSharedPreferences("currentUser", ctx.MODE_PRIVATE);

        if (prefs.contains("userCredentials")) {
            prefs.edit().clear().commit();
        }
    }

    public UserCredentials getUserCredentials() {
        SharedPreferences prefs = ctx.getSharedPreferences("currentUser", ctx.MODE_PRIVATE);
        String userDataJSON = prefs.getString("userCredentials", "");

        if (!userDataJSON.isEmpty()) {
            return new Gson().fromJson(userDataJSON, UserCredentials.class);
        }

        return null;
    }
}
