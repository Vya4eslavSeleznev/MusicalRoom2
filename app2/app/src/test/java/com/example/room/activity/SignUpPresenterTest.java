package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.SignUpPresenter;

import org.junit.Test;
import org.mockito.Mockito;

public class SignUpPresenterTest {

    private final SignUpPresenter signUpPresenter;
    private final Gateway gateway;
    private final SignUpPresenter.View view;
    private final SharedPreferences sharedPreferences;

    public SignUpPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(SignUpPresenter.View.class);
        this.signUpPresenter = new SignUpPresenter(view, gateway);
    }

    @Test
    public void addCustomer() {
        final String login = "TestLogin";
        final String password = "TestPassword";
        final String name = "TestName";
        final String phone = "4444";
        String token = "fgrtduf7";

        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);

        signUpPresenter.addCustomer(login, password, name, phone);

        verify(gateway, times(1)).addCustomer(token, login, password, name, phone);
    }

    @Test
    public void addUser() {
        signUpPresenter.addUser();
        verify(view, times(1)).addUser();
    }
}
