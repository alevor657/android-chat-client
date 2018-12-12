package com.example.yooo.ultimatechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
                if(editBox.getText().length() > 0)
                {
                    Message message = new Message(editBox.getText().toString(), new User("test", null), Calendar.getInstance().getTimeInMillis());
                    mMessageAdapter.addMessage(message);
                    editBox.setText("");
                }
            }
        });
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
