package com.example.room.view.activity;

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
import com.example.room.model.Reservation;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.Repository;
import com.example.room.presenter.activity.ReservationPresenter;

import java.util.ArrayList;
import java.util.List;

public class ReservationActivity extends AppCompatActivity implements ReservationPresenter.View {

    private ReservationPresenter presenter;
    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomPrice, reservationDate;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private Repository repository;

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

        repository = new Repository();

        presenter = new ReservationPresenter(this);
        presenter.setReservations();
        presenter.setRecycleView();
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
            presenter.confirmDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreferences(this);
    }

    @Override
    public void setReservations(List<Reservation> reservations) {
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
    public void setRecycleView(List<Reservation> reservations, Gateway gateway, String token) {
        ReservationAdapter reservationAdapter = new ReservationAdapter(ReservationActivity.this,
                roomName, roomPrice,  reservationDate, reservations, gateway, token);

        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(reservationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(ReservationActivity.this));
        reservationAdapter.notifyDataSetChanged();
    }

    @Override
    public void confirmDialog(Gateway gateway, String token, int userId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all reservations?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            gateway.deleteCustomerReservations(token, userId);
            Intent intent = new Intent(ReservationActivity.this, ReservationActivity.class);
            startActivity(intent);
            finish();
        });

        builder.setNegativeButton("No", (dialog, which) -> {
        });

        builder.create().show();
    }
}
