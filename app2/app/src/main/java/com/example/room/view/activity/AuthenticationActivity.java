package com.example.room.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.room.R;
import com.example.room.gateways.Gateway;
import com.example.room.model.Token;

public class AuthenticationActivity extends AppCompatActivity {

    private EditText login;
    private EditText password;
    private Button logInBtn;
    private SharedPreferences prefs;
    private SharedPreferences.Editor edit;

    public AuthenticationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        login = this.findViewById(R.id.authentication_editLogin);
        password = this.findViewById(R.id.authentication_editPassword);
        logInBtn = this.findViewById(R.id.log_in_button);

        prefs = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Gateway gateway = new Gateway();
                Token token = gateway.getToken(login.getText().toString(), password.getText().toString());

                try {
                    edit = prefs.edit();
                    edit.putString("token", token.getToken());
                    edit.putInt("userId", token.getId());
                    edit.putString("role", token.getRole());
                    edit.apply();
                } catch (Exception ex) {
                    runOnUiThread(new Runnable(){
                        public void run() {
                            Toast.makeText(getApplicationContext(), "User is not found",Toast.LENGTH_LONG).show();
                        }
                    });

                    return;
                }

                Intent intent;

                if (token.getRole().equals("ADMIN")) {
                    intent = new Intent(AuthenticationActivity.this, AdminActivity.class);
                } else {
                    intent = new Intent(AuthenticationActivity.this, UserActivity.class);
                }

                startActivity(intent);
            }
        });
    }
}