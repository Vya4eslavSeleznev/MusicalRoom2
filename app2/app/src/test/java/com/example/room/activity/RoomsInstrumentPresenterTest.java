package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.RoomsInstrument;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.RoomsInstrumentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RoomsInstrumentPresenterTest {

    private final RoomsInstrumentPresenter roomsInstrumentPresenter;
    private final Gateway gateway;
    private final RoomsInstrumentPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<RoomsInstrument> roomsInstruments;
    private final List<RoomsInstrument> roomsInstrumentsToReturn;

    public RoomsInstrumentPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(RoomsInstrumentPresenter.View.class);
        this.roomsInstrumentPresenter = new RoomsInstrumentPresenter(view, gateway);
        this.roomsInstrumentsToReturn = new ArrayList<>();
        this.roomsInstruments = getRoomsInstrument();
    }

    @Test
    public void setRoomsInstrument() {
        roomsInstrumentPresenter.setRoomsInstrument();
        verify(view, times(1)).setRoomsInstrument(roomsInstruments);
    }

    @Test
    public void setDataInRecycleView() {
        roomsInstrumentPresenter.setDataInRecycleView();
        verify(view, times(1)).setDataInRecycleView(gateway, token, roomsInstruments);
    }

    private List<RoomsInstrument> getRoomsInstrument() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(gateway.getAllRoomsInstrument(token)).thenReturn(roomsInstrumentsToReturn);

        return gateway.getAllRoomsInstrument(token);
    }
}
