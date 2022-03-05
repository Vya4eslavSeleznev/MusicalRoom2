package com.example.room.fragment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.ConfirmationPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ConfirmationPresenterTest {

    private final ConfirmationPresenter confirmationPresenter;
    private final Gateway gateway;
    private final ConfirmationPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<Reservation> reservations;
    private final List<Reservation> reservationsToReturn;

    public ConfirmationPresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(ConfirmationPresenter.View.class);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.confirmationPresenter = new ConfirmationPresenter(view, gateway);
        this.reservationsToReturn = new ArrayList<>();
        this.reservations = getAllCustomerReservation();
    }

    @Test
    public void setReservations() {
        confirmationPresenter.setReservations();
        verify(view, times(1)).setReservations(reservations);
    }

    @Test
    public void setRecycleView() {
        confirmationPresenter.setRecycleView();
        verify(view, times(1)).setRecycleView(reservations, gateway, token);
    }

    private List<Reservation> getAllCustomerReservation() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(gateway.getAllReservations(token)).thenReturn(reservationsToReturn);

        return gateway.getAllReservations(token);
    }
}
