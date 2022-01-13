package com.example.room.presenter.activity;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class AdminRoomPresenter {

    private final Gateway gateway;
    private final View roomActivity;

    public AdminRoomPresenter(View roomActivity) {
        this.gateway = new Gateway();
        this.roomActivity = roomActivity;
    }

    public List<Room> getRooms(String token) {
        return gateway.getAllRooms(token);
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setRooms(String token) {
        roomActivity.setRooms(getRooms(token));
    }

    public SharedPreferences getSharedPreferences() {
        return roomActivity.getSharedPreferences();
    }

    public void setDataInRecycleView(String token) {
        roomActivity.setDataInRecycleView(gateway, token, getRooms(token));
    }

    public Intent adapterEventLogic(int position) {
        return roomActivity.adapterEventLogic(position);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setRooms(List<Room> rooms);
        void setDataInRecycleView(Gateway gateway, String token, List<Room> rooms);
        Intent adapterEventLogic(int position);
    }
}
