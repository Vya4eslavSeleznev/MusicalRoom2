package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Room;

import java.util.List;

public class EquipmentPresenter {

    private final Gateway gateway;
    private final View equipmentFragment;

    public EquipmentPresenter(View equipmentFragment) {
        this.gateway = new Gateway();
        this.equipmentFragment = equipmentFragment;
    }

    public List<Room> listOfRooms() {
        return gateway.getAllRooms(equipmentFragment.getSharedPreferences().getString("token", null));
    }

    public void setRooms() {
        equipmentFragment.roomSeparation(listOfRooms());
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void roomSeparation(List<Room> rooms);
    }
}
