package com.example.yooo.ultimatechat;

import android.os.AsyncTask;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthAPI {
    public static final String LOGIN_ATTEMPT_FAILED = "Bad credentials, pleasy try again";
    public static final HttpUrl SIGN_IN_URL = HttpUrl.parse("http://192.168.1.63:1338/user/signin");
    public static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");

    public static class LoginRequest extends AsyncTask<String, Void, UserCredentials> {
        @Override
        protected UserCredentials doInBackground(String... params) {
            RequestBody body = RequestBody.create(AuthAPI.MEDIA_TYPE, params[0]);

            Request request = new Request.Builder()
                    .url(AuthAPI.SIGN_IN_URL)
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();

            Response res = null;
            try {
                res = client
                        .newCall(request)
                        .execute();
            } catch (IOException e) {
                e.printStackTrace();
            }

            UserCredentials uc = null;

            try {
                if (res.isSuccessful()) {
                    uc = new Gson().fromJson(res.body().string(), UserCredentials.class);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            return uc;
        }

        @Override
        protected void onPostExecute(UserCredentials result) {
            super.onPostExecute(result);
        }
    }
}
