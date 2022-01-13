package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Room;

import java.util.List;

public class AdminRoomPresenter {

    private Gateway gateway;
    private View roomActivity;

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

    public interface View {

        SharedPreferences getSharedPreferences();
        void setRooms(List<Room> rooms);
        void setDataInRecycleView(Gateway gateway, String token, List<Room> rooms);
    }
}
