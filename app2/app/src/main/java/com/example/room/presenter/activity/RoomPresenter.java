package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class RoomPresenter {

    private final Gateway gateway;
    private final View roomActivity;

    public RoomPresenter(View roomActivity) {
        this.gateway = new Gateway();
        this.roomActivity = roomActivity;
    }

    public void setRooms() {
        roomActivity.setRooms(getAllRooms());
    }

    public void setDataInRecycleView() {
        roomActivity.setDataInRecycleView(gateway, getToken(), getAllRooms());
    }

    private List<Room> getAllRooms() {
        return gateway.getAllRooms(getToken());
    }

    private String getToken() {
        return roomActivity.getSharedPreferences().getString("token", null);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setRooms(List<Room> rooms);
        void setDataInRecycleView(Gateway gateway, String token, List<Room> rooms);
    }
}
