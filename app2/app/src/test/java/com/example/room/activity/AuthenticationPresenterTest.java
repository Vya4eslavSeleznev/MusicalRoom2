package com.example.room.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.example.room.model.Token;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.AuthenticationPresenter;

import org.junit.Test;
import org.mockito.Mockito;

public class AuthenticationPresenterTest {

    private final AuthenticationPresenter authenticationPresenter;
    private final Gateway gateway;
    private final AuthenticationPresenter.View view;

    public AuthenticationPresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(AuthenticationPresenter.View.class);
        this.authenticationPresenter = new AuthenticationPresenter(view, gateway);
    }

    @Test
    public void getToken_tokenReturned() {
        final String login = "testLogin";
        final String password = "testPassword";

        Token tokenToReturn = new Token(4, login, "wr3wevfdvfv", "ADMIN");
        when(gateway.getToken(login, password)).thenReturn(tokenToReturn);

        Token token = authenticationPresenter.getToken(login, password);

        assertEquals(tokenToReturn, token);
    }

    @Test
    public void getSharedPreferences_sharedPreferencesCalled() {
        authenticationPresenter.getSharedPreferences();
        verify(view, times(1)).getSharedPreferences();
    }

    @Test
    public void logInEventLogic_logInEventLogicCalled() {
        authenticationPresenter.logInEventLogic();
        verify(view, times(1)).logInEventLogic();
    }
}
