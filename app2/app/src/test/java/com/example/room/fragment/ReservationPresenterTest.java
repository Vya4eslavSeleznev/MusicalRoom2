package com.example.room.fragment;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.content.SharedPreferences;

import com.example.room.model.Customer;
import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.fragment.ReservationPresenter;

import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

public class ReservationPresenterTest {

    private final ReservationPresenter reservationPresenter;
    private final Gateway gateway;
    private final ReservationPresenter.View view;
    private final SharedPreferences sharedPreferences;
    final String token = "fgrtduf7";

    public ReservationPresenterTest() {
        this.sharedPreferences = Mockito.mock(SharedPreferences.class);
        this.gateway = Mockito.mock(Gateway.class);
        this.view = Mockito.mock(ReservationPresenter.View.class);
        this.reservationPresenter = new ReservationPresenter(view, gateway);
    }

    @Test
    public void addReservation() {
        final Customer customerToReturn = new Customer(4, "TestName", "4444");
        final int userId = 5;
        final String date = "2020-01-01";
        final int roomId = 6;

        getToken();
        when(sharedPreferences.getInt("userId", 0)).thenReturn(userId);
        when(gateway.getCustomer(token, userId)).thenReturn(customerToReturn);

        reservationPresenter.addReservation(date, roomId);
        Customer customer = gateway.getCustomer(token, userId);

        verify(gateway, times(1)).addReservation(token, date, roomId, customer.getId());
    }

    @Test
    public void parseRoomData() {
        List<Room> roomsToReturn = new ArrayList<>();

        getToken();
        when(gateway.getAllRooms(token)).thenReturn(roomsToReturn);

        List<Room> rooms = gateway.getAllRooms(token);

        reservationPresenter.parseRoomData();

        verify(view, times(1)).parseRoomData(rooms);
    }

    @Test
    public void createDateDialog() {
        reservationPresenter.createDateDialog();
        verify(view, times(1)).createDateDialog();
    }

    @Test
    public void setDate() {
        final int year = 2022;
        final int month = 3;
        final int day = 5;

        reservationPresenter.setDate(year, month, day);
        verify(view, times(1)).setDate(year, month, day);
    }

    private void getToken() {
        when(view.getSharedPreferences()).thenReturn(sharedPreferences);
        when(sharedPreferences.getString("token", null)).thenReturn(token);
    }
}
