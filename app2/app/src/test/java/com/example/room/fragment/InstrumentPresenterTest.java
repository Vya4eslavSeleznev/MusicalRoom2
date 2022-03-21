package com.example.room.fragment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.InstrumentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

public class InstrumentPresenterTest {

    private final InstrumentPresenter instrumentPresenter;
    private final Gateway gateway;
    private final InstrumentPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";

    public InstrumentPresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(InstrumentPresenter.View.class);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.instrumentPresenter = new InstrumentPresenter(view, gateway);
    }

    @Test
    public void addInstrument() {
        final String name = "TestName";
        final String description = "TestDescription";

        instrumentPresenter.addInstrument(token, name, description);
        verify(gateway, times(1)).addInstrument(token, name, description);
    }

    @Test
    public void addInstrumentEventLogic() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);

        instrumentPresenter.addInstrumentEventLogic();
        verify(view, times(1)).addInstrumentEventLogic(token);
    }
}
