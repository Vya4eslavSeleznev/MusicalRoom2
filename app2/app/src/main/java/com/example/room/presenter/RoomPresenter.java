package com.example.room.presenter;

import android.content.SharedPreferences;

import com.example.room.gateways.Gateway;

public class RoomPresenter {

    private Gateway gateway;
    private View roomFragment;

    public RoomPresenter(View roomFragment) {
        this.gateway = new Gateway();
        this.roomFragment = roomFragment;
    }

    public void addRoom(String token, String name, String description, Long price) {
        gateway.addRoom(token, name, description, price);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void addRoomEventLogic();
    }
}
