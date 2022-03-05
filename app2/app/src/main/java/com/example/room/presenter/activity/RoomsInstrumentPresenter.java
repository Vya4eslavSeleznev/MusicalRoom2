package com.example.room.presenter.activity;

import android.content.SharedPreferences;

import com.example.room.model.RoomsInstrument;
import com.example.room.model.gateways.Gateway;

import java.util.List;

public class RoomsInstrumentPresenter {

    private final Gateway gateway;
    private final View roomsInstrumentActivity;

    public RoomsInstrumentPresenter(View roomsInstrumentActivity) {
        this.gateway = new Gateway();
        this.roomsInstrumentActivity = roomsInstrumentActivity;
    }

    public void setRoomsInstrument() {
        roomsInstrumentActivity.setRoomsInstrument(getAllRoomsInstrument());
    }

    public void setDataInRecycleView() {
        roomsInstrumentActivity.setDataInRecycleView(gateway, getToken(), getAllRoomsInstrument());
    }

    private String getToken() {
        return roomsInstrumentActivity.getSharedPreferences().getString("token", null);
    }

    private List<RoomsInstrument> getAllRoomsInstrument() {
        return gateway.getAllRoomsInstrument(getToken());
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void setRoomsInstrument(List<RoomsInstrument> roomsInstrument);
        void setDataInRecycleView(Gateway gateway, String token, List<RoomsInstrument> roomsInstrument);
    }
}
