package com.example.room.view.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.ReservationAdapter;
import com.example.room.gateways.Gateway;
import com.example.room.model.Customer;
import com.example.room.model.Reservation;

import java.util.ArrayList;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomPrice, reservationDate;
    private ReservationAdapter reservationAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private String token;
    private Customer customer;
    private Gateway gateway;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);

        recyclerView = findViewById(R.id.room_recyclerview);
        emptyImageView = findViewById(R.id.empty_room_imageView);
        emptyTextView = findViewById(R.id.empty_room_textView);

        roomName = new ArrayList<>();
        roomPrice = new ArrayList<>();
        reservationDate = new ArrayList<>();

        gateway = new Gateway();
        SharedPreferences preferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);

        token = preferences.getString("token", null);
        int userId = preferences.getInt("userId", 0);
        customer = gateway.getCustomer(token, userId);

        List<Reservation> reservations = gateway.getCustomerReservation(token, customer.getId());

        setReservation(reservations);

        reservationAdapter = new ReservationAdapter(ReservationActivity.this, roomName, roomPrice,
                                                    reservationDate, reservations, gateway, token);
        recyclerView.removeAllViewsInLayout();

        recyclerView.setAdapter(reservationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReservationActivity.this));
        reservationAdapter.notifyDataSetChanged();
    }

    private void setReservation(List<Reservation> reservations) {
        if(reservations.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= reservations.size() - 1; i++) {
                roomName.add(reservations.get(i).getRoom().getName());
                roomPrice.add(reservations.get(i).getRoom().getPrice().toString());
                reservationDate.add(reservations.get(i).getDate().toString());
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_all_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.delete_all) {
            confirmDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all reservations?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                gateway.deleteCustomerReservations(token, customer.getId());

                Intent intent = new Intent(ReservationActivity.this, ReservationActivity.class);
                startActivity(intent);
                finish();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.create().show();
    }


}