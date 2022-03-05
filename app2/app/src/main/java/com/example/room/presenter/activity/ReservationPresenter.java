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
        reservationActivity.setReservations(getCustomerReservation());
    }

    public void setRecycleView() {
        reservationActivity.setRecycleView(getCustomerReservation(), gateway, getToken());
    }

    public void confirmDialog() {
        reservationActivity.confirmDialog(gateway, getToken(), getCustomer().getId(), getCustomerReservation());
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

    private List<Reservation> getCustomerReservation() {
        return gateway.getCustomerReservation(getToken(), getCustomer().getId());
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setReservations(List<Reservation> reservations);
        void setRecycleView(List<Reservation> reservations, Gateway gateway, String token);
        void confirmDialog(Gateway gateway, String token, int userId, List<Reservation> reservations);
    }
}
