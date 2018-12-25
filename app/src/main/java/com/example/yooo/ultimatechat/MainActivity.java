package com.example.yooo.ultimatechat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {
    CredentialsStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.storage = new CredentialsStorage(getApplicationContext());

        setContentView(R.layout.activity_signin);

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Ultimate chat";
            String description = "Chat app";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Ultimate chat", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        if (storage.getUserCredentials() != null) {
            UserCredentials.setInstance(storage.getUserCredentials());
            Intent intent = new Intent(this, ChatListActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        WebSocketControls.getSocket().emit(WebSocketControls.USER_LEAVE, UserCredentials.getInstance().getUsername());
        super.onDestroy();
    }

    public void registerRedirect(View v) {
        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void chatListRedirect(View v) {
        this.trySignIn();

        if (UserCredentials.getInstance().getToken() != null) {
            Intent intent = new Intent(this, ChatListActivity.class);
            startActivity(intent);
        }
    }

    public void trySignIn() {
        // Get user credentials from input fields
        String username = ((EditText) findViewById(R.id.username_input)).getText().toString();
        String password = ((EditText) findViewById(R.id.password_input)).getText().toString();

        UserCredentials creds = UserCredentials.getInstance();
        creds.setUsername(username);
        creds.setPassword(password);

        // Parse to JSON
        String json = new Gson().toJson(creds);

        AuthAPI.LoginRequest lr = new AuthAPI.LoginRequest();

        try {
            creds = lr.execute(json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (creds == null) {
            Toast.makeText(getApplicationContext(), AuthAPI.LOGIN_ATTEMPT_FAILED, Toast.LENGTH_LONG).show();
        } else {
            UserCredentials.setInstance(creds);
            storage.persistUserData(creds);
        }
    }
}
