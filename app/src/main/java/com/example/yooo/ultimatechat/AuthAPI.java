package com.example.yooo.ultimatechat;

import android.view.View;
import android.widget.EditText;

import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.MediaType;

public class AuthAPI {
    public static final HttpUrl SIGN_IN_URL = HttpUrl.parse("http://192.168.1.63:1338/user/signin");
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
}
