package com.example.yooo.ultimatechat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
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
                            newChatView.setOnTouchListener(new OnSwipeTouchListener(ChatListActivity.this.getApplicationContext()){
                                @Override
                                public void onSwipeRight() {
                                    new AlertDialog.Builder(ChatListActivity.this)
                                            .setTitle("Do you want to delete this chat?")
                                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    chatListLayout.removeView(newChatView);
                                                }})
                                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.cancel();
                                                }})
                                            .show();
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

    public class OnSwipeTouchListener implements View.OnTouchListener {

        private final GestureDetector gestureDetector;

        public OnSwipeTouchListener(Context context) {
            gestureDetector = new GestureDetector(context, new GestureListener());
        }

        public void onSwipeLeft() {
        }

        public void onSwipeRight() {
        }


        public boolean onTouch(View v, MotionEvent event) {
            return gestureDetector.onTouchEvent(event);
        }

        private final class GestureListener extends GestureDetector.SimpleOnGestureListener {

            private static final int SWIPE_DISTANCE_THRESHOLD = 100;
            private static final int SWIPE_VELOCITY_THRESHOLD = 100;

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                float distanceX = e2.getX() - e1.getX();
                float distanceY = e2.getY() - e1.getY();
                if (Math.abs(distanceX) > Math.abs(distanceY) && Math.abs(distanceX) > SWIPE_DISTANCE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (distanceX > 0)
                        onSwipeRight();
                    else
                        onSwipeLeft();
                    return true;
                }
                return false;
            }
        }
    }
}
