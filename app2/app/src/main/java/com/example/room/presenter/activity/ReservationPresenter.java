package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.Customer;
import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class ReservationPresenter {

    private final Gateway gateway;
    private final View reservationActivity;

    public ReservationPresenter(View instrumentActivity) {
        this.gateway = new Gateway();
        this.reservationActivity = instrumentActivity;
    }

    public void setReservations() {
        reservationActivity.setReservations(gateway.getCustomerReservation(getToken(), getCustomer().getId()));
    }

    public void setRecycleView() {
        reservationActivity.setRecycleView(gateway.getCustomerReservation(getToken(), getUserId()), gateway, getToken());
    }

    public void confirmDialog() {
        reservationActivity.confirmDialog(gateway, getToken(), getUserId());
    }

    private Customer getCustomer() {
        return gateway.getCustomer(getToken(), getUserId());
    }

    private String getToken() {
        return reservationActivity.getSharedPreferences().getString("token", null);
    }

    private int getUserId() {
        return reservationActivity.getSharedPreferences().getInt("userId", 0);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setReservations(List<Reservation> reservations);
        void setRecycleView(List<Reservation> reservations, Gateway gateway, String token);
        void confirmDialog(Gateway gateway, String token, int userId);
    }
}
