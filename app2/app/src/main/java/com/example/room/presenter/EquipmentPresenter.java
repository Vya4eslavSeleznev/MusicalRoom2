package com.example.room.presenter;

import android.content.SharedPreferences;

import com.example.room.gateways.Gateway;
import com.example.room.model.Room;

import java.util.List;

public class EquipmentPresenter {

    private Gateway gateway;
    private View equipmentFragment;

    public EquipmentPresenter(View equipmentFragment) {
        this.gateway = new Gateway();
        this.equipmentFragment = equipmentFragment;
    }

    public List<Room> getRooms(String token) {
        return gateway.getAllRooms(token);
    }

    public interface View {

        void setRooms(List<Room> rooms);
        SharedPreferences getSharedPreferences();
    }
}
