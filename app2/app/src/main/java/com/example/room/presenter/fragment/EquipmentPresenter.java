package com.example.room.presenter.fragment;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.room.adapter.EquipmentRoomAdapter;
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

    public void setRooms() {
        equipmentFragment.setRooms(listOfRooms());
    }

    public Intent adapterEventLogic(int position) {
        return equipmentFragment.adapterEventLogic(position, listOfRooms());
    }

    public EquipmentRoomAdapter setRecycleView() {
        return equipmentFragment.setRecycleView();
    }

    private List<Room> listOfRooms() {
        return gateway.getAllRooms(equipmentFragment.getSharedPreferences().getString("token", null));
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setRooms(List<Room> rooms);
        Intent adapterEventLogic(int position, List<Room> rooms);
        EquipmentRoomAdapter setRecycleView();
    }
}
