package com.example.room.fragment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.RoomPresenter;

import org.junit.Test;
import org.mockito.Mockito;

public class RoomPresenterTest {

    private final RoomPresenter roomPresenter;
    private final Gateway gateway;
    private final RoomPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";

    public RoomPresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(RoomPresenter.View.class);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.roomPresenter = new RoomPresenter(view, gateway);
    }

    @Test
    public void addRoom() {
        final String name = "TestName";
        final String description = "TestDescription";
        final Long price = 123L;

        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);

        roomPresenter.addRoom(name, description, price);

        verify(gateway, times(1)).addRoom(token, name, description, price);
    }

    @Test
    public void addRoomEventLogic() {
        roomPresenter.addRoomEventLogic();
        verify(view, times(1)).addRoomEventLogic();
    }
}
