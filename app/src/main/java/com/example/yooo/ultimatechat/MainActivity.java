package com.example.yooo.ultimatechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
    }

    public void registerRedirect(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void chatListRedirect(View v) {
        this.trySignIn();
//        startActivity(new Intent(this, ChatListActivity.class));
    }

    public void trySignIn() {
        // Get user credentials from input fields
        UserCredentials creds = new UserCredentials(findViewById(R.id.username_input), findViewById(R.id.password_input));

        // Parse to JSON
        String json = new Gson().toJson(creds);

        RequestBody body = RequestBody.create(AuthAPI.MEDIA_TYPE, json);

        Request request = new Request.Builder()
                .url(AuthAPI.SIGN_IN_URL)
                .post(body)
                .build();

        OkHttpClient client = new OkHttpClient();

        client
                .newCall(request)
                .enqueue(
                        new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                e.printStackTrace();
                            }

                            @Override
                            public void onResponse(Call call, final Response response) throws IOException {
                                // TODO: Do the redirect logic...
                            }
                        }
                );
    }
}
