package com.example.room.fragment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;
import android.util.MutableInt;
import android.widget.Spinner;

import com.example.room.model.Instrument;
import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.RoomsInstrumentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RoomsInstrumentPresenterTest {

    private final RoomsInstrumentPresenter profilePresenter;
    private final Gateway gateway;
    private final RoomsInstrumentPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<Room> roomsToReturn;
    private final List<Instrument> instrumentsToReturn;

    public RoomsInstrumentPresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(RoomsInstrumentPresenter.View.class);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.profilePresenter = new RoomsInstrumentPresenter(view, gateway);
        this.roomsToReturn = new ArrayList<>();
        this.instrumentsToReturn = new ArrayList<>();
    }

    @Test
    public void getRooms() {
        List<Room> rooms = getAllRooms();
        assertEquals(roomsToReturn, rooms);
    }

    @Test
    public void getInstruments() {
        List<Instrument> instruments = getAllInstruments();
        assertEquals(instrumentsToReturn, instruments);
    }

    @Test
    public void addRoomsInstrument() {
        Long roomId = 8L;
        Long instrumentId = 9L;

        getToken();

        profilePresenter.addRoomsInstrument(roomId, instrumentId);

        verify(gateway, times(1)).addRoomsInstrument(token, roomId, instrumentId);
    }

    @Test
    public void parseData() {
        List<Room> rooms = getAllRooms();
        List<Instrument> instruments = getAllInstruments();

        profilePresenter.parseData();

        verify(view, times(1)).parseRoomData(rooms);
        verify(view, times(1)).parseInstrumentData(instruments);
    }

    @Test
    public void setSpinners() {
        profilePresenter.setSpinners();
        verify(view, times(1)).setSpinners();
    }

    @Test
    public void spinnerEventLogic() {
        Spinner spinner = Mockito.mock(Spinner.class);
        MutableInt pos = Mockito.mock(MutableInt.class);

        profilePresenter.spinnerEventLogic(spinner, pos);
        verify(view, times(1)).spinnerEventLogic(spinner, pos);
    }

    private void getToken() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
    }

    private List<Instrument> getAllInstruments() {
        getToken();
        when(gateway.getAllInstruments(token)).thenReturn(instrumentsToReturn);

        return profilePresenter.getInstruments();
    }

    private List<Room> getAllRooms() {
        getToken();
        when(gateway.getAllRooms(token)).thenReturn(roomsToReturn);

        return profilePresenter.getRooms();
    }
}
