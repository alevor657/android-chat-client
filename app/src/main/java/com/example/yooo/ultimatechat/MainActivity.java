package com.example.yooo.ultimatechat;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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
        startActivity(new Intent(this, ChatListActivity.class));
    }
}
