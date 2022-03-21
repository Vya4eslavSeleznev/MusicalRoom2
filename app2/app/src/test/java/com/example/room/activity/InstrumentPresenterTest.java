package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.InstrumentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class InstrumentPresenterTest {

    private final InstrumentPresenter instrumentPresenter;
    private final Gateway gateway;
    private final InstrumentPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<Instrument> instrumentsToReturn;
    private final List<Instrument> instruments;

    public InstrumentPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(InstrumentPresenter.View.class);
        this.instrumentPresenter = new InstrumentPresenter(view, gateway);
        this.instrumentsToReturn = new ArrayList<>();
        this.instruments = getInstruments();
    }

    @Test
    public void setInstruments() {
        instrumentPresenter.setInstruments();
        verify(view, times(1)).setInstruments(instruments);
    }

    @Test
    public void setDataInRecycleView() {
        instrumentPresenter.setDataInRecycleView();
        verify(view, times(1)).setDataInRecycleView(gateway, token, instruments);
    }

    private List<Instrument> getInstruments() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(gateway.getAllInstruments(token)).thenReturn(instrumentsToReturn);

        return gateway.getAllInstruments(token);
    }
}
