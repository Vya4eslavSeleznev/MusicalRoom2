package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Customer;
import com.example.room.model.Room;

import java.util.List;

public class ReservationPresenter {

    private final Gateway gateway;
    private final View reservationFragment;

    public ReservationPresenter(View reservationFragment) {
        this.gateway = new Gateway();
        this.reservationFragment = reservationFragment;
    }

    public Customer getCustomer(int userId) {
        return gateway.getCustomer(getToken(), userId);
    }

    public void addReservation(String date, int roomId, int customerId) {
        gateway.addReservation(getToken(), date, roomId, customerId);
    }

    public void parseRoomData() {
        reservationFragment.parseRoomData(gateway.getAllRooms(getToken()));
    }

    public void createDateDialog() {
        reservationFragment.createDateDialog();
    }

    public void setDate(int year, int month, int day) {
        reservationFragment.setDate(year, month, day);
    }

    public SharedPreferences getSharedPreferences() {
        return reservationFragment.getSharedPreferences();
    }

    private String getToken() {
        return getSharedPreferences().getString("token", null);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void parseRoomData(List<Room> rooms);
        void createDateDialog();
        void setDate(int year, int month, int day);
    }
}
