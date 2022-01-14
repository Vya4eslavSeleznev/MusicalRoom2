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

    public List<Room> getRooms() {
        return gateway.getAllRooms(getToken());
    }

    public Gateway getGateway() {
        return gateway;
    }

    public void setRooms() {
        roomActivity.setRooms(getRooms());
    }

    public void setDataInRecycleView() {
        roomActivity.setDataInRecycleView(gateway, getToken(), getRooms());
    }

    public Intent adapterEventLogic(int position) {
        return roomActivity.adapterEventLogic(position);
    }

    private String getToken() {
        return roomActivity.getSharedPreferences().getString("token", null);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setRooms(List<Room> rooms);
        void setDataInRecycleView(Gateway gateway, String token, List<Room> rooms);
        Intent adapterEventLogic(int position);
    }
}
