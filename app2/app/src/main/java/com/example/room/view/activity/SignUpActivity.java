package com.example.room.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.room.R;
import com.example.room.presenter.activity.SignUpPresenter;

public class SignUpActivity extends AppCompatActivity implements SignUpPresenter.View {

    private EditText name;
    private EditText phone;
    private EditText login;
    private EditText password;
    private SignUpPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        name = this.findViewById(R.id.sign_up_editName);
        phone = this.findViewById(R.id.sign_up_editPhone);
        login = this.findViewById(R.id.sign_up_editLogin);
        password = this.findViewById(R.id.sign_up_editPassword);
        Button signUpBtn = this.findViewById(R.id.sign_up_button);

        presenter = new SignUpPresenter(this);

        signUpBtn.setOnClickListener(v -> presenter.addUser());
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void addUser() {
        if(!name.getText().toString().matches("") ||
                !phone.getText().toString().matches("") ||
                !login.getText().toString().matches("") ||
                !password.getText().toString().matches(""))
        {
            presenter.addCustomer(login.getText().toString(), password.getText().toString(),
                    name.getText().toString(), phone.getText().toString());

            Toast.makeText(this, "Signed up successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(this, "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}