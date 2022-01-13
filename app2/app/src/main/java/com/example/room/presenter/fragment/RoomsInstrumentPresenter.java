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

    public RoomsInstrumentPresenter(View roomsInstrumentFragment) {
        this.gateway = new Gateway();
        this.roomsInstrumentFragment = roomsInstrumentFragment;
    }

    public List<Room> getRooms(String token) {
        return gateway.getAllRooms(token);
    }

    public List<Instrument> getInstruments(String token) {
        return gateway.getAllInstruments(token);
    }

    public void addRoomsInstrument(String token, Long roomId, Long instrumentId) {
        gateway.addRoomsInstrument(token, roomId, instrumentId);
    }

    public void parseData() {
        String token = roomsInstrumentFragment.getSharedPreferences().getString("token", null);

        roomsInstrumentFragment.parseRoomData(gateway.getAllRooms(token));
        roomsInstrumentFragment.parseInstrumentData(gateway.getAllInstruments(token));
    }

    public void setSpinners() {
        roomsInstrumentFragment.setSpinners();
    }

    public void spinnerEventLogic(Spinner spinner, MutableInt pos) {
        roomsInstrumentFragment.spinnerEventLogic(spinner, pos);
    }

    public SharedPreferences getSharedPreferences() {
        return roomsInstrumentFragment.getSharedPreferences();
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void parseRoomData(List<Room> rooms);
        void parseInstrumentData(List<Instrument> instruments);
        void setSpinners();
        void spinnerEventLogic(Spinner spinner, MutableInt pos);
    }
}
