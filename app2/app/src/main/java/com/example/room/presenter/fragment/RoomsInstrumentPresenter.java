package com.example.room.presenter.fragment;

import android.content.SharedPreferences;
import android.util.MutableInt;
import android.widget.Spinner;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Instrument;
import com.example.room.model.Room;

import java.util.List;

public class RoomsInstrumentPresenter {

    private final Gateway gateway;
    private final View roomsInstrumentFragment;

    public RoomsInstrumentPresenter(View roomsInstrumentFragment, Gateway gateway) {
        this.gateway = gateway;
        this.roomsInstrumentFragment = roomsInstrumentFragment;
    }

    public List<Room> getRooms() {
        return gateway.getAllRooms(getToken());
    }

    public List<Instrument> getInstruments() {
        return gateway.getAllInstruments(getToken());
    }

    public void addRoomsInstrument(Long roomId, Long instrumentId) {
        gateway.addRoomsInstrument(getToken(), roomId, instrumentId);
    }

    public void parseData() {
        roomsInstrumentFragment.parseRoomData(gateway.getAllRooms(getToken()));
        roomsInstrumentFragment.parseInstrumentData(gateway.getAllInstruments(getToken()));
    }

    public void setSpinners() {
        roomsInstrumentFragment.setSpinners();
    }

    public void spinnerEventLogic(Spinner spinner, MutableInt pos) {
        roomsInstrumentFragment.spinnerEventLogic(spinner, pos);
    }

    private String getToken() {
        return roomsInstrumentFragment.getSharedPreferences().getString("token", null);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void parseRoomData(List<Room> rooms);
        void parseInstrumentData(List<Instrument> instruments);
        void setSpinners();
        void spinnerEventLogic(Spinner spinner, MutableInt pos);
    }
}
