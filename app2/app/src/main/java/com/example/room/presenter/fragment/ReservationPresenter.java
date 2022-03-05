package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Customer;
import com.example.room.model.Room;

import java.util.List;

public class ReservationPresenter {

    private final Gateway gateway;
    private final View reservationFragment;

    public ReservationPresenter(View reservationFragment, Gateway gateway) {
        this.gateway = gateway;
        this.reservationFragment = reservationFragment;
    }

    public void addReservation(String date, int roomId) {
        gateway.addReservation(getToken(), date, roomId, getCustomer().getId());
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

    private String getToken() {
        return reservationFragment.getSharedPreferences().getString("token", null);
    }

    private Customer getCustomer() {
        return gateway.getCustomer(getToken(), reservationFragment.getSharedPreferences().getInt("userId", 0));
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void parseRoomData(List<Room> rooms);
        void createDateDialog();
        void setDate(int year, int month, int day);
    }
}
