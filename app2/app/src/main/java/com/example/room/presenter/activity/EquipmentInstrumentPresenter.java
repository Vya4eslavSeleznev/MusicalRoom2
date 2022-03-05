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

    public void setInstruments() {
        equipmentInstrumentActivity.setInstruments(gateway.getRoomsInstrument(getToken(),
                equipmentInstrumentActivity.getRoomId()));
    }

    public void setDataInRecycleView() {
        equipmentInstrumentActivity.setDataInRecycleView();
    }

    private String getToken() {
        return equipmentInstrumentActivity.getSharedPreferences().getString("token", null);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView();
        int getRoomId();
    }
}
