package com.example.yooo.ultimatechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ChatListActivity extends AppCompatActivity {
    User currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        currentUser = (User) getIntent().getSerializableExtra("User");

        View chatListView = this.findViewById(R.id.chatListObject);
        final Intent intent = new Intent(this, ChatListObject.class);
        intent.putExtra("User", currentUser);
        chatListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });

    }
}
