package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;

public class SignUpPresenter {

    private final Gateway gateway;
    private final View signUpActivity;

    public SignUpPresenter(View signUpActivity) {
        this.gateway = new Gateway();
        this.signUpActivity = signUpActivity;
    }

    public void addCustomer(String login, String password, String name, String phone) {
        gateway.addCustomer(signUpActivity.getSharedPreferences().getString("token", null),
                login, password, name, phone);
    }

    public void addUser() {
        signUpActivity.addUser();
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void addUser();
    }
}
