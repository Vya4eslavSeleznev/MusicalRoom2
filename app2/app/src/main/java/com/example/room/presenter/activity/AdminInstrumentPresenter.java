package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Instrument;

import java.util.List;

public class AdminInstrumentPresenter {

    private final Gateway gateway;
    private final View instrumentActivity;

    public AdminInstrumentPresenter(View instrumentActivity) {
        this.gateway = new Gateway();
        this.instrumentActivity = instrumentActivity;
    }

    public void setInstruments() {
        instrumentActivity.setInstruments(getRoomsInstrument());
    }

    public void setDataInRecycleView() {
        instrumentActivity.setDataInRecycleView(getRoomsInstrument(), gateway, getToken());
    }

    private String getToken() {
        return instrumentActivity.getSharedPreferences().getString("token", null);
    }

    private List<Instrument> getRoomsInstrument() {
        return gateway.getRoomsInstrument(getToken(), instrumentActivity.getRoomId());
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView(List<Instrument> instruments, Gateway gateway, String token);
        int getRoomId();
    }
}
