package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class InstrumentPresenter {

    private final Gateway gateway;
    private final View instrumentActivity;

    public InstrumentPresenter(View instrumentActivity) {
        this.gateway = new Gateway();
        this.instrumentActivity = instrumentActivity;
    }

    public void setInstruments() {
        instrumentActivity.setInstruments(getAllInstruments());
    }

    public void setDataInRecycleView() {
        instrumentActivity.setDataInRecycleView(gateway, getToken(), getAllInstruments());
    }

    private List<Instrument> getAllInstruments() {
        return gateway.getAllInstruments(getToken());
    }

    private String getToken() {
        return instrumentActivity.getSharedPreferences().getString("token", null);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView(Gateway gateway, String token, List<Instrument> instruments);
    }
}
