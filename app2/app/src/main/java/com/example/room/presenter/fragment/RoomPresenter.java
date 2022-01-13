package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;

public class RoomPresenter {

    private final Gateway gateway;
    private final View roomFragment;

    public RoomPresenter(View roomFragment) {
        this.gateway = new Gateway();
        this.roomFragment = roomFragment;
    }

    public void addRoom(String token, String name, String description, Long price) {
        gateway.addRoom(token, name, description, price);
    }

    public void addRoomEventLogic() {
        roomFragment.addRoomEventLogic();
    }

    public SharedPreferences getSharedPreferences() {
        return roomFragment.getSharedPreferences();
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void addRoomEventLogic();
    }
}
