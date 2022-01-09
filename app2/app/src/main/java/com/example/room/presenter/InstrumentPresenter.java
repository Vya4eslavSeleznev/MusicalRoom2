package com.example.room.presenter;

import android.content.SharedPreferences;

import com.example.room.gateways.Gateway;

public class InstrumentPresenter {

    private Gateway gateway;
    private View instrumentFragment;

    public InstrumentPresenter(View instrumentFragment) {
        this.gateway = new Gateway();
        this.instrumentFragment = instrumentFragment;
    }

    public void addInstrument(String token, String name, String description) {
        gateway.addInstrument(token, name, description);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void addInstrumentEventLogic();
    }
}
