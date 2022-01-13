package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class EquipmentInstrumentPresenter {

    private final Gateway gateway;
    private final View equipmentInstrumentActivity;

    public EquipmentInstrumentPresenter(View equipmentInstrumentActivity) {
        this.gateway = new Gateway();
        this.equipmentInstrumentActivity = equipmentInstrumentActivity;
    }

    public int getRoomId() {
        return equipmentInstrumentActivity.getRoomId();
    }

    public void setInstruments(String token, int roomId) {
        equipmentInstrumentActivity.setInstruments(gateway.getRoomsInstrument(token, roomId));
    }

    public SharedPreferences getSharedPreferences() {
        return equipmentInstrumentActivity.getSharedPreferences();
    }

    public void setDataInRecycleView() {
        equipmentInstrumentActivity.setDataInRecycleView();
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView();
        int getRoomId();
    }
}
