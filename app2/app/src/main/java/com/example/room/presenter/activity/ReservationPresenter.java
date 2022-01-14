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

    public SharedPreferences getSharedPreferences() {
        return reservationActivity.getSharedPreferences();
    }

    public void setReservations(String token, int userId) {
        reservationActivity.setReservations(gateway.getCustomerReservation(token, getCustomer(token, userId).getId()));
    }

    public void setRecycleView(String token, int userId) {
        reservationActivity.setRecycleView(gateway.getCustomerReservation(token, userId), gateway, token);
    }

    public void confirmDialog(String token, int userId) {
        reservationActivity.confirmDialog(gateway, token, userId);
    }

    private Customer getCustomer(String token, int userId) {
        return gateway.getCustomer(token, userId);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setReservations(List<Reservation> reservations);
        void setRecycleView(List<Reservation> reservations, Gateway gateway, String token);
        void confirmDialog(Gateway gateway, String token, int userId);
    }
}
