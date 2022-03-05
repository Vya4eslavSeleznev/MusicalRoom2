package com.example.room.fragment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Customer;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.ProfilePresenter;

import org.junit.Test;
import org.mockito.Mockito;

public class ProfilePresenterTest {

    private final ProfilePresenter profilePresenter;
    private final Gateway gateway;
    private final ProfilePresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";

    public ProfilePresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(ProfilePresenter.View.class);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.profilePresenter = new ProfilePresenter(view, gateway);
    }

    @Test
    public void getCustomer() {
        int userId = 44;
        Customer customerToReturn = new Customer(4, "TestName", "4444");

        when(gateway.getCustomer(token, userId)).thenReturn(customerToReturn);

        Customer customer = gateway.getCustomer(token, userId);

        assertEquals(customerToReturn, customer);
    }

    @Test
    public void setCustomer() {
        int userId = 5;

        getToken();
        when(sharedPreferences.getInt("userId", 0)).thenReturn(userId);

        profilePresenter.setCustomer();

        verify(view, times(1)).setCustomer(token, userId);
    }

    @Test
    public void updateCustomer() {
        final int customerId = 7;
        final String name = "TestName";
        final String phone = "56789";

        profilePresenter.updateCustomer(token, customerId, name, phone);
        verify(gateway, times(1)).updateCustomer(token, customerId, name, phone);
    }

    @Test
    public void refreshEventLogic() {
        getToken();
        profilePresenter.refreshEventLogic();
        verify(view, times(1)).refreshEventLogic(token);
    }

    private void getToken() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
    }
}
