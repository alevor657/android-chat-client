package com.example.yooo.ultimatechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class ChatListObject extends AppCompatActivity {
    private RecyclerView mMessageRecycler;
    private ChatListAdapter mMessageAdapter;
    private List<Message> messages;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activiy_chatview);
        messages = new ArrayList<>();
        name = UserCredentials.getInstance().getUsername();
        mMessageRecycler = findViewById(R.id.reyclerview_message_list);
        mMessageAdapter = new ChatListAdapter(this, messages, UserCredentials.getInstance());
        mMessageRecycler.setLayoutManager(new LinearLayoutManager(this));
        mMessageRecycler.setAdapter(mMessageAdapter);
        setTitle(name);

        View button = findViewById(R.id.button_chatbox_send);
        final EditText editBox = findViewById(R.id.edittext_chatbox);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (editBox.getText().length() > 0) {
                    String chatName = getIntent().getStringExtra("room");

                    Message message = new Message(
                            editBox.getText().toString(),
                            new User(UserCredentials.getInstance().getUsername(), null),
                            Calendar.getInstance().getTimeInMillis()
                    );

                    message.setRoom(chatName);

                    WebSocketControls.getSocket().emit(WebSocketControls.MESSAGE_SEND, new Gson().toJson(message));

                    editBox.setText("");
                }
            }
        });

        Socket socket = WebSocketControls.getSocket();
        socket.on(WebSocketControls.NEW_MESSAGE, new onMessage());
        socket.on(WebSocketControls.RESPONSE_MESSAGE_HISTORY, new onMessageHistory());
    }

    @Override
    protected void onResume() {
        super.onResume();
        String chatName = getIntent().getStringExtra("room");
        WebSocketControls.getSocket().emit(WebSocketControls.REQUEST_MESSAGE_HISTORY, chatName);
    }

    class onMessage implements Emitter.Listener {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mMessageAdapter.addMessage(new Gson().fromJson(args[0].toString(), Message.class));
                }
            });
        }
    }

    class onMessageHistory implements Emitter.Listener {
        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Message[] messages = new Gson().fromJson(args[0].toString(), Message[].class);

                    for (Message message: messages) {
                        mMessageAdapter.addMessage(message);
                    }
                }
            });
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
