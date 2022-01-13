package com.example.room.presenter.activity;

import com.example.room.model.gateways.Gateway;

public class InstrumentPresenter {

    private final Gateway gateway;
    private View instrumentActivity;

    public InstrumentPresenter(View instrumentActivity) {
        this.gateway = new Gateway();
        this.instrumentActivity = instrumentActivity;
    }



    public interface View {

    }
}
