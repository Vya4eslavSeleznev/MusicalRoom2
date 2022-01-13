package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class EquipmentInstrumentPresenter {

    private Gateway gateway;
    private View equipmentInstrumentActivity;

    public EquipmentInstrumentPresenter(View equipmentInstrumentActivity) {
        this.gateway = new Gateway();
        this.equipmentInstrumentActivity = equipmentInstrumentActivity;
    }

    public List<Instrument> getRoomsInstrument(String token, int roomId) {
        return gateway.getRoomsInstrument(token, roomId);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView();
    }
}
