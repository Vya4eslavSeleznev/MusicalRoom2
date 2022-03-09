package com.example.room;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room.model.Customer;
import com.example.room.model.Instrument;
import com.example.room.model.Reservation;
import com.example.room.model.Room;
import com.example.room.presenter.Repository;

import org.junit.Test;
import org.mockito.Mockito;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class RepositoryTest {

    private final Repository repository;
    private final List<Instrument> instruments;
    private final ArrayList<String> instrumentName;
    private final ArrayList<String> instrumentDescription;
    private final ImageView emptyImageView;
    private final TextView emptyTextView;
    private final List<Room> rooms;
    private final ArrayList<String> roomName;
    private final ArrayList<String> roomDescription;
    private final ArrayList<String> roomPrice;
    private final List<Reservation> reservations;
    private final ArrayList<String> reservationDate;
    private final ArrayList<String> reservationConfirmation;

    public RepositoryTest() {
        repository = new Repository();
        this.instruments = new ArrayList<>();
        this.instrumentName = new ArrayList<>();
        this.instrumentDescription = new ArrayList<>();
        this.rooms = new ArrayList<>();
        this.roomName = new ArrayList<>();
        this.roomDescription = new ArrayList<>();
        this.roomPrice = new ArrayList<>();
        this.reservations = new ArrayList<>();
        this.reservationDate = new ArrayList<>();
        this.reservationConfirmation = new ArrayList<>();
        this.emptyImageView = Mockito.mock(ImageView.class);
        this.emptyTextView = Mockito.mock(TextView.class);
    }

    @Test
    public void getSharedPreferences() {
        SharedPreferences sharedPreferencesToReturn = Mockito.mock(SharedPreferences.class);
        Activity activity = Mockito.mock(Activity.class);

        when(activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)).thenReturn(sharedPreferencesToReturn);

        SharedPreferences sharedPreferences = repository.getSharedPreferences(activity);

        assertEquals(sharedPreferencesToReturn, sharedPreferences);
    }

    @Test
    public void setInstruments_emptyInstruments_valueNotSet() {
        repository.setInstruments(instruments, instrumentName, instrumentDescription, emptyImageView,
                emptyTextView);

        verify(emptyImageView, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void setInstruments_fullInstruments_valueSet() {
        instruments.add(new Instrument(1, "TestName1", "TestDescription1"));
        instruments.add(new Instrument(2, "TestName2", "TestDescription2"));

        repository.setInstruments(instruments, instrumentName, instrumentDescription, emptyImageView,
                emptyTextView);

        verify(emptyImageView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setRooms_emptyRooms_valueNotSet() {
        repository.setRooms(rooms, roomName, roomDescription, roomPrice, emptyImageView, emptyTextView);

        verify(emptyImageView, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void setRooms_fullRooms_valueSet() {
        rooms.add(new Room(1, "TestName1", "TestDescription1", 111L));
        rooms.add(new Room(2, "TestName2", "TestDescription2", 222L));

        repository.setRooms(rooms, roomName, roomDescription, roomPrice, emptyImageView, emptyTextView);

        verify(emptyImageView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void setReservations_emptyReservations_valueNotSet() {
        repository.setReservations(reservations, roomName, roomPrice, reservationDate,
                reservationConfirmation, emptyImageView, emptyTextView);

        verify(emptyImageView, times(1)).setVisibility(View.VISIBLE);
    }

    @Test
    public void setReservations_fullReservations_valueSet() {
        Room newRoom = new Room(1, "TestName1", "TestDescription1", 111L);
        Customer newCustomer = new Customer(1, "TestName", "1234");

        reservations.add(new Reservation(1, new Date(12345), newRoom, newCustomer, false));

        repository.setReservations(reservations, roomName, roomPrice, reservationDate,
                reservationConfirmation, emptyImageView, emptyTextView);

        verify(emptyImageView, times(1)).setVisibility(View.GONE);
    }

    @Test
    public void getRoomId_nullBundle_defaultRoomId() {
        int roomId = -1;
        Bundle bundle = null;

        int nullRoomId = repository.getRoomId(bundle);

        assertEquals(nullRoomId, roomId);
    }

    @Test
    public void getRoomId_notNullBundle_valueSet() {
        int roomId = 2;
        Bundle bundle = Mockito.mock(Bundle.class);

        when(bundle.getInt("roomId")).thenReturn(roomId);

        bundle.putInt("roomId", roomId);

        int notNullRoomId = repository.getRoomId(bundle);

        assertEquals(notNullRoomId, roomId);
    }

    @Test
    public void checkDate_selectedDateBeforeCurrent_false() throws ParseException {
        java.util.Date selectedDate = convertToDate("26-09-2010");

        boolean value = repository.checkDate(selectedDate);

        assertFalse(value);
    }

    @Test
    public void checkDate_selectedDateAfterCurrent_true() throws ParseException {
        java.util.Date selectedDate = convertToDate("26-09-2023");

        boolean value = repository.checkDate(selectedDate);

        assertTrue(value);
    }

    @Test
    public void checkDate_selectedDateEqualsCurrent_true() throws ParseException {
        java.util.Date selectedDate = new java.util.Date();

        boolean value = repository.checkDate(selectedDate);

        assertTrue(value);
    }

    private java.util.Date convertToDate(String receivedDate) throws ParseException{
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

        return formatter.parse(receivedDate);
    }
}
