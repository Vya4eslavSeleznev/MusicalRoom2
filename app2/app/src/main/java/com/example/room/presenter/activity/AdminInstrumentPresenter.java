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

    public List<Instrument> getRoomsInstrument(String token, int roomId) {
        return gateway.getRoomsInstrument(token, roomId);
    }

    public void setInstruments(String token, int roomId) {
        instrumentActivity.setInstruments(getRoomsInstrument(token, roomId));
    }

    public SharedPreferences getSharedPreferences() {
        return instrumentActivity.getSharedPreferences();
    }

    public int getRoomId() {
        return instrumentActivity.getRoomId();
    }

    public void setDataInRecycleView(List<Instrument> instruments, String token) {
        instrumentActivity.setDataInRecycleView(instruments, gateway, token);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setInstruments(List<Instrument> instruments);
        void setDataInRecycleView(List<Instrument> instruments, Gateway gateway, String token);
        int getRoomId();
    }
}
