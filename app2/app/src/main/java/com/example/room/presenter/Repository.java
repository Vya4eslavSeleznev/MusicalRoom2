package com.example.room.presenter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room.model.Instrument;
import com.example.room.model.Reservation;
import com.example.room.model.Room;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public SharedPreferences getSharedPreferences(Activity activity) {
        return activity.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    public void setInstruments(List<Instrument> instruments, ArrayList<String> instrumentName,
                               ArrayList<String> instrumentDescription, ImageView emptyImageView,
                               TextView emptyTextView) {
        if(instruments.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= instruments.size() - 1; i++) {
                instrumentName.add(instruments.get(i).getName());
                instrumentDescription.add(instruments.get(i).getDescription());
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public void setRooms(List<Room> rooms, ArrayList<String> roomName, ArrayList<String> roomDescription,
                         ArrayList<String> roomPrice, ImageView emptyImageView, TextView emptyTextView) {
        if(rooms.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= rooms.size() - 1; i++) {
                roomName.add(rooms.get(i).getName());
                roomDescription.add(rooms.get(i).getDescription());
                roomPrice.add(rooms.get(i).getPrice().toString());
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public void setReservations(List<Reservation> reservations, ArrayList<String> roomName,
                                ArrayList<String> roomPrice, ArrayList<String> reservationDate,
                                ArrayList<String> reservationConfirmation, ImageView emptyImageView,
                                TextView emptyTextView) {
        if(reservations.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= reservations.size() - 1; i++) {
                roomName.add(reservations.get(i).getRoom().getName());
                roomPrice.add(reservations.get(i).getRoom().getPrice().toString());
                reservationDate.add(reservations.get(i).getDate().toString());
                reservationConfirmation.add(String.valueOf(reservations.get(i).isConfirmed()));
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    public int getRoomId(Bundle bundle) {
        int roomId = -1;

        if(bundle != null)
            roomId = bundle.getInt("roomId");

        return roomId;
    }
}
