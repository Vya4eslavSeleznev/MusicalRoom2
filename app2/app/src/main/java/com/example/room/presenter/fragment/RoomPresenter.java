package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;

public class RoomPresenter {

    private final Gateway gateway;
    private final View roomFragment;

    public RoomPresenter(View roomFragment, Gateway gateway) {
        this.gateway = gateway;
        this.roomFragment = roomFragment;
    }

    public void addRoom(String name, String description, Long price) {
        gateway.addRoom(roomFragment.getSharedPreferences().getString("token", null), name, description, price);
    }

    public void addRoomEventLogic() {
        roomFragment.addRoomEventLogic();
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void addRoomEventLogic();
    }
}
