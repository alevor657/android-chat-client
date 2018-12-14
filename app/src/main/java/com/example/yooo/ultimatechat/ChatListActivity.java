package com.example.yooo.ultimatechat;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Objects;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatListActivity extends AppCompatActivity {
    UserCredentials currentUser;
    ViewGroup chatListLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatlist);

        currentUser = UserCredentials.getInstance();
        chatListLayout = findViewById(R.id.chatListLayout);

        final View addNewChat = findViewById(R.id.addNewChat);
        addNewChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewChat();
            }
        });

        // Make a ws connection
        Socket socket = WebSocketControls.getSocket();

        socket.on(Socket.EVENT_CONNECT, new onConnect());
        socket.on(WebSocketControls.RECIEVED_ROOMS, new onRooms());

        socket.connect();
    }

    class onConnect implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            WebSocketControls.getSocket().emit(WebSocketControls.REQUEST_ROOM_NAMES);
        }
    }

    class onRooms implements Emitter.Listener {
        @Override
        public void call(Object... args) {
            Log.i("AAA", "Got rooms");
            String[] rooms = new Gson().fromJson((String) args[0], String[].class);

            for (final String room : rooms) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        createNewRoom(room);
                    }
                });
            }
        }

    }

    private void addNewChat() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View addChatForm = Objects.requireNonNull(inflater).inflate(R.layout.add_chat_form, null, false);
        final EditText newChatText = addChatForm.findViewById(R.id.newChatText);

        new AlertDialog.Builder(this).setView(addChatForm)
                .setTitle("Add new chat")
                .setPositiveButton("Add", this.createNewRoom(newChatText.getText().toString()))
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).show();
    }

    public DialogInterface.OnClickListener createNewRoom(final String chatName) {
        return new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {

                WebSocketControls.getSocket().emit(WebSocketControls.PUBLISH_NEW_ROOM, chatName);
                Log.d("AAA", "Sent new room");

                if(chatName.length() > 0) {
                    final View newChatView = LayoutInflater.from(ChatListActivity.this).inflate(R.layout.chat_object, null);
                    final Intent intent = new Intent(ChatListActivity.this, ChatListObject.class);
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

        };
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
