package com.example.room.fragment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.room.adapter.EquipmentRoomAdapter;
import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.EquipmentPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class EquipmentPresenterTest {

    private final EquipmentPresenter equipmentPresenter;
    private final Gateway gateway;
    private final EquipmentPresenter.View view;
    private final SharedPreferences sharedPreferences;
    private final String token = "fgrtduf7";
    private final List<Room> rooms;
    private final List<Room> roomsToReturn;

    public EquipmentPresenterTest() {
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(EquipmentPresenter.View.class);
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.equipmentPresenter = new EquipmentPresenter(view, gateway);
        this.roomsToReturn = new ArrayList<>();
        this.rooms = getListOfRooms();
    }

    @Test
    public void setRooms() {
        equipmentPresenter.setRooms();
        verify(view, times(1)).setRooms(rooms);
    }

    @Test
    public void adapterEventLogic() {
        Intent intentToReturn = new Intent();
        int position = 4;

        when(view.adapterEventLogic(position, rooms)).thenReturn(intentToReturn);

        Intent intent = view.adapterEventLogic(position, rooms);

        assertEquals(intentToReturn, intent);
    }

    @Test
    public void setRecycleView() {
        Context context = Mockito.mock(Context.class);
        ArrayList<String> roomName = new ArrayList<>();
        ArrayList<String> roomDescription = new ArrayList<>();
        ArrayList<String> roomPrice = new ArrayList<>();
        EquipmentRoomAdapter equipmentRoomAdapterToReturn = new EquipmentRoomAdapter(context, roomName, roomDescription, roomPrice);

        when(view.setRecycleView()).thenReturn(equipmentRoomAdapterToReturn);

        EquipmentRoomAdapter equipmentRoomAdapter = view.setRecycleView();

        assertEquals(equipmentRoomAdapterToReturn, equipmentRoomAdapter);
    }

    private List<Room> getListOfRooms() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
        when(gateway.getAllRooms(token)).thenReturn(roomsToReturn);

        return gateway.getAllRooms(token);
    }
}
