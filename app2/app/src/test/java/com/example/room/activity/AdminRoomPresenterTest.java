package com.example.room.activity;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Intent;
import android.content.SharedPreferences;

import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.activity.AdminRoomPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomPresenterTest {

    private final AdminRoomPresenter adminRoomPresenter;
    private final Gateway gateway;
    private final AdminRoomPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<Room> roomsToReturn;
    private final List<Room> rooms;

    public AdminRoomPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(AdminRoomPresenter.View.class);
        this.adminRoomPresenter = new AdminRoomPresenter(view, gateway);
        this.roomsToReturn = new ArrayList<>();
        this.rooms = getAllRooms();
    }

    @Test
    public void getRooms() {
        assertEquals(roomsToReturn, rooms);
    }

    @Test
    public void setRooms() {
        adminRoomPresenter.setRooms();
        verify(view, times(1)).setRooms(rooms);
    }

    @Test
    public void setDataInRecycleView() {
        adminRoomPresenter.setDataInRecycleView();
        verify(view, times(1)).setDataInRecycleView(gateway, token, rooms);
    }

    @Test
    public void adapterEventLogic() {
        Intent intentToReturn = new Intent();
        int position = 4;

        when(view.adapterEventLogic(position)).thenReturn(intentToReturn);

        Intent intent = view.adapterEventLogic(position);

        assertEquals(intentToReturn, intent);
    }

    private List<Room> getAllRooms() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(gateway.getAllRooms(token)).thenReturn(roomsToReturn);

        return adminRoomPresenter.getRooms();
    }
}
