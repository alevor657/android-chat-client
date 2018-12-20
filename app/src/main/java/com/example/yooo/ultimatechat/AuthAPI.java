package com.example.yooo.ultimatechat;

import android.os.AsyncTask;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthAPI {
    public static final String LOGIN_ATTEMPT_FAILED = "Bad credentials, pleasy try again";
    public static final String REGISTER_PASSWORDS_DO_NOT_MATCH = "Sorry, provided passwords did not match";
    public static final String REGISTER_ATTEMPT_FAILED = "Sorry, this email or username is already taken or credentials format is bad";

    public static final HttpUrl SIGN_IN_URL = HttpUrl.parse("http://192.168.1.48:1338/user/signin");
    public static final HttpUrl SIGN_UP_URL = HttpUrl.parse("http://192.168.1.48:1338/user/signup");
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
                if (res != null) {
                    if (res.isSuccessful()) {
                        uc = new Gson().fromJson(res.body().string(), UserCredentials.class);
                    }
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

    public static class RegisterRequest extends AsyncTask<String, Void, UserCredentials> {
        @Override
        protected UserCredentials doInBackground(String... params) {
            RequestBody body = RequestBody.create(AuthAPI.MEDIA_TYPE, params[0]);

            Request request = new Request.Builder()
                    .url(AuthAPI.SIGN_UP_URL)
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
