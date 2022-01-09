package com.example.room.presenter;

import android.content.SharedPreferences;

import com.example.room.gateways.Gateway;
import com.example.room.model.Customer;
import com.example.room.model.Room;

import java.util.List;

public class ReservationPresenter {

    private Gateway gateway;
    private View reservationFragment;

    public ReservationPresenter(View reservationFragment) {
        this.gateway = new Gateway();
        this.reservationFragment = reservationFragment;
    }

    public List<Room> getRooms(String token) {
        return gateway.getAllRooms(token);
    }

    public Customer getCustomer(String token, int userId) {
        return gateway.getCustomer(token, userId);
    }

    public void addReservation(String token, String date, int roomId, int customerId) {
        gateway.addReservation(token, date, roomId, customerId);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void parseRoomData(List<Room> rooms);
        void createDateDialog();
        void setDate(int year, int month, int day);
    }
}
