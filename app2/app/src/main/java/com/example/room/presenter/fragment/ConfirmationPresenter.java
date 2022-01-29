package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class ConfirmationPresenter {

    private final Gateway gateway;
    private final View confirmationFragment;

    public ConfirmationPresenter(View confirmationFragment) {
        this.gateway = new Gateway();
        this.confirmationFragment = confirmationFragment;
    }

    public void setReservations() {
        confirmationFragment.setReservations(getReservations());
    }

    public void setRecycleView() {
        confirmationFragment.setRecycleView(getReservations(), gateway, getToken());
    }

    private String getToken() {
        return confirmationFragment.getSharedPreferences().getString("token", null);
    }

    private List<Reservation> getReservations() {
        return gateway.getAllReservations(getToken());
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setReservations(List<Reservation> reservations);
        void setRecycleView(List<Reservation> reservations, Gateway gateway, String token);
    }
}
