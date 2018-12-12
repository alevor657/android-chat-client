package com.example.yooo.ultimatechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.concurrent.ExecutionException;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void tryRegister(View v) {
        String username = ((EditText) findViewById(R.id.username_input)).getText().toString();
        String email = ((EditText) findViewById(R.id.email_input)).getText().toString();

        String password = ((EditText) findViewById(R.id.password_input)).getText().toString();
        String password2 = ((EditText) findViewById(R.id.password_input2)).getText().toString();

        if (!password.equals(password2)) {
            Toast.makeText(getApplicationContext(), AuthAPI.REGISTER_PASSWORDS_DO_NOT_MATCH, Toast.LENGTH_LONG).show();
            ((EditText) findViewById(R.id.password_input)).getText().clear();
            ((EditText) findViewById(R.id.password_input2)).getText().clear();
            return;
        }

        UserCredentials creds = UserCredentials.getInstance();
        creds.setUsername(username);
        creds.setPassword(password);
        creds.setEmail(email);

        // Parse to JSON
        String json = new Gson().toJson(creds);

        AuthAPI.RegisterRequest rr = new AuthAPI.RegisterRequest();

        try {
            creds = rr.execute(json).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        if (creds == null) {
            Toast.makeText(getApplicationContext(), AuthAPI.REGISTER_ATTEMPT_FAILED, Toast.LENGTH_LONG).show();
        } else {
            UserCredentials.setInstance(creds);
        }

        if (UserCredentials.getInstance().getToken() != null) {
            Intent intent = new Intent(this, ChatListActivity.class);
            startActivity(intent);
        }
    }
}
