package com.example.room.activity;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.RoomPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class RoomPresenterTest {

    private final RoomPresenter roomPresenter;
    private final Gateway gateway;
    private final RoomPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<Room> roomsToReturn;
    private final List<Room> rooms;

    public RoomPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(RoomPresenter.View.class);
        this.roomPresenter = new RoomPresenter(view, gateway);
        this.roomsToReturn = new ArrayList<>();
        this.rooms = getAllRooms();
    }

    @Test
    public void setRooms() {
        roomPresenter.setRooms();
        verify(view, times(1)).setRooms(rooms);
    }

    @Test
    public void setDataInRecycleView() {
        roomPresenter.setDataInRecycleView();
        verify(view, times(1)).setDataInRecycleView(gateway, token, rooms);
    }

    private List<Room> getAllRooms() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(gateway.getAllRooms(token)).thenReturn(roomsToReturn);

        return gateway.getAllRooms(token);
    }

}
