package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.AdminInstrumentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AdminInstrumentPresenterTest {

    private final AdminInstrumentPresenter adminInstrumentPresenter;
    private final Gateway gateway;
    private final AdminInstrumentPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "vverfdv43";
    private final int roomId = 4;
    private final List<Instrument> instruments;

    public AdminInstrumentPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(AdminInstrumentPresenter.View.class);
        this.adminInstrumentPresenter = new AdminInstrumentPresenter(view, gateway);
        this.instruments = getInstruments();
    }

    @Test
    public void setInstruments() {
        this.adminInstrumentPresenter.setInstruments();
        verify(view, times(1)).setInstruments(instruments);
    }

    @Test
    public void setDataInRecycleView() {
        this.adminInstrumentPresenter.setDataInRecycleView();
        verify(view, times(1)).setDataInRecycleView(instruments, gateway, token);
    }

    private List<Instrument> getInstruments() {
        List<Instrument> instruments = new ArrayList<>();

        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(view.getRoomId()).thenReturn(roomId);
        when(gateway.getRoomsInstrument(token, roomId)).thenReturn(instruments);

        return instruments;
    }
}
