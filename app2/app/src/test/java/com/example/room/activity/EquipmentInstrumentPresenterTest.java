package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.EquipmentInstrumentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInstrumentPresenterTest {

    private final EquipmentInstrumentPresenter equipmentInstrumentPresenter;
    private final Gateway gateway;
    private final EquipmentInstrumentPresenter.View view;
    private final SharedPreferences sharedPreferences;

    public EquipmentInstrumentPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(EquipmentInstrumentPresenter.View.class);
        this.equipmentInstrumentPresenter = new EquipmentInstrumentPresenter(view, gateway);
    }

    @Test
    public void setInstruments() {
        final String token = "fgrtduf7";
        final int roomId = 4;
        List<Instrument> instruments;
        List<Instrument> instrumentsToReturn = new ArrayList<>();

        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(view.getRoomId()).thenReturn(roomId);
        when(gateway.getRoomsInstrument(token, roomId)).thenReturn(instrumentsToReturn);

        instruments = gateway.getRoomsInstrument(token, roomId);
        equipmentInstrumentPresenter.setInstruments();

        verify(view, times(1)).setInstruments(instruments);
    }

    @Test
    public void setDataInRecycleView() {
        equipmentInstrumentPresenter.setDataInRecycleView();

        verify(view, times(1)).setDataInRecycleView();
    }
}
