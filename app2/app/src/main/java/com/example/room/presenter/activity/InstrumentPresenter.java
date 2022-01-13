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

    public SharedPreferences getSharedPreferences() {
        return instrumentActivity.getSharedPreferences();
    }

    public void setInstruments(String token) {
        instrumentActivity.setInstruments(getAllInstruments(token));
    }

    public void setDataInRecycleView(String token) {
        instrumentActivity.setDataInRecycleView(gateway, token, getAllInstruments(token));
    }

    private List<Instrument> getAllInstruments(String token) {
        return gateway.getAllInstruments(token);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView(Gateway gateway, String token, List<Instrument> instruments);
    }
}
