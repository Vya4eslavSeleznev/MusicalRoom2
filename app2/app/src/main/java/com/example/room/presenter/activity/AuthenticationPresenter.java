package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Token;

public class AuthenticationPresenter {

    private Gateway gateway;
    private View authenticationActivity;

    public AuthenticationPresenter(View authenticationActivity) {
        this.gateway = new Gateway();
        this.authenticationActivity = authenticationActivity;
    }

    public Token getToken(String login, String password) {
        return gateway.getToken(login, password);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void logInEventLogic();
    }
}
