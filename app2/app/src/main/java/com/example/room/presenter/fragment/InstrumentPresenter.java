package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;

public class InstrumentPresenter {

    private final Gateway gateway;
    private final View instrumentFragment;

    public InstrumentPresenter(View instrumentFragment) {
        this.gateway = new Gateway();
        this.instrumentFragment = instrumentFragment;
    }

    public void addInstrument(String token, String name, String description) {
        gateway.addInstrument(token, name, description);
    }

    public void addInstrumentEventLogic() {
        instrumentFragment.addInstrumentEventLogic(
                instrumentFragment.getSharedPreferences().getString("token", null));
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void addInstrumentEventLogic(String token);
    }
}
