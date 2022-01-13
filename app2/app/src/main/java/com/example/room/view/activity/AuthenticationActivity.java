package com.example.room.view.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.room.R;
import com.example.room.model.Token;
import com.example.room.presenter.activity.AuthenticationPresenter;

public class AuthenticationActivity extends AppCompatActivity implements AuthenticationPresenter.View{

    private AuthenticationPresenter presenter;
    private EditText login;
    private EditText password;

    public AuthenticationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentication);

        login = this.findViewById(R.id.authentication_editLogin);
        password = this.findViewById(R.id.authentication_editPassword);
        Button logInBtn = this.findViewById(R.id.log_in_button);
        presenter = new AuthenticationPresenter(this);

        logInBtn.setOnClickListener(v -> presenter.logInEventLogic());
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void logInEventLogic() {
        Token token = presenter.getToken(login.getText().toString(), password.getText().toString());

        try {
            SharedPreferences.Editor edit = presenter.getSharedPreferences().edit();
            edit.putString("token", token.getToken());
            edit.putInt("userId", token.getId());
            edit.putString("role", token.getRole());
            edit.apply();
        } catch (Exception ex) {
            runOnUiThread(() -> Toast.makeText(getApplicationContext(), "User is not found",Toast.LENGTH_LONG).show());

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
}
