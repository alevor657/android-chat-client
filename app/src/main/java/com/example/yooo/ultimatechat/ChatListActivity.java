package com.example.yooo.ultimatechat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class ChatListActivity extends AppCompatActivity {
    User currentUser;
    ViewGroup chatListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);
        currentUser = (User) getIntent().getSerializableExtra("User");
        chatListLayout = findViewById(R.id.chatListLayout);
        final View addNewChat = findViewById(R.id.addNewChat);
        addNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChat();
            }
        });
    }

    private void addNewChat() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addChatForm = Objects.requireNonNull(inflater).inflate(R.layout.add_chat_form, null, false);
        final EditText newChatText = addChatForm.findViewById(R.id.newChatText);
        new AlertDialog.Builder(this).setView(addChatForm)
                .setTitle("Add new chat")
                .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String chatName = newChatText.getText().toString();
                        if(chatName.length() > 0) {
                            final View newChatView = LayoutInflater.from(ChatListActivity.this).inflate(R.layout.chat_object, null);
                            final Intent intent = new Intent(ChatListActivity.this, ChatListObject.class);
                            intent.putExtra("User", currentUser);
                            intent.putExtra("Name", chatName);
                            newChatView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(intent);
                                }
                            });
                            TextView newChatText = newChatView.findViewById(R.id.chatName);
                            newChatText.setText(chatName);
                            chatListLayout.addView(newChatView);
                        } else {
                            dialog.cancel();
                        }
                    }

                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }
}
