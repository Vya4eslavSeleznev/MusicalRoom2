package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Customer;
import com.example.room.model.CustomerData;
import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.ReservationPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ReservationPresenterTest {

    private final ReservationPresenter reservationPresenter;
    private final Gateway gateway;
    private final ReservationPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final CustomerData customerData;

    public ReservationPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(ReservationPresenter.View.class);
        this.reservationPresenter = new ReservationPresenter(view, gateway);
        this.customerData = getCustomerData();
    }

    @Test
    public void setReservations() {
        reservationPresenter.setReservations();
        verify(view, times(1)).setReservations(customerData.getReservations());
    }

    @Test
    public void setRecycleView() {
        reservationPresenter.setRecycleView();
        verify(view, times(1)).setRecycleView(customerData.getReservations(),
                gateway, token);
    }

    @Test
    public void confirmDialog() {
        reservationPresenter.confirmDialog();
        verify(view, times(1)).confirmDialog(gateway, token,
                customerData.getCustomerId(), customerData.getReservations());
    }

    private CustomerData getCustomerData() {
        final int userId = 4;
        Customer customerToReturn = new Customer(5, "TestName", "4444");
        List<Reservation> reservationsToReturn = new ArrayList<>();

        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(sharedPreferences.getInt("userId", 0)).thenReturn(userId);
        when(gateway.getCustomer(token, userId)).thenReturn(customerToReturn);

        Customer customer = gateway.getCustomer(token, userId);

        when(gateway.getCustomerReservation(token, customer.getId())).thenReturn(reservationsToReturn);

        return new CustomerData(gateway.getCustomerReservation(token, customer.getId()), customer.getId());
    }
}
