package com.example.room.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.RoomsInstrumentAdapter;
import com.example.room.model.RoomsInstrument;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.Repository;
import com.example.room.presenter.activity.RoomsInstrumentPresenter;

import java.util.ArrayList;
import java.util.List;

public class RoomsInstrumentActivity extends AppCompatActivity implements RoomsInstrumentPresenter.View {

    private RecyclerView recyclerView;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private Repository repository;
    private ArrayList<String> roomName, instrumentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms_instrument);

        recyclerView = findViewById(R.id.rooms_instrument_recyclerview);
        emptyImageView = findViewById(R.id.empty_rooms_instrument_imageView);
        emptyTextView = findViewById(R.id.empty_rooms_instrument_textView);

        roomName = new ArrayList<>();
        instrumentName = new ArrayList<>();

        repository = new Repository();
        RoomsInstrumentPresenter presenter = new RoomsInstrumentPresenter(this);

        presenter.setRoomsInstrument();
        presenter.setDataInRecycleView();
    }


    @Override
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreferences(this);
    }

    @Override
    public void setRoomsInstrument(List<RoomsInstrument> roomsInstrument) {
        if(roomsInstrument.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= roomsInstrument.size() - 1; i++) {
                roomName.add(roomsInstrument.get(i).getRoom().getName());
                instrumentName.add(roomsInstrument.get(i).getInstrument().getName());
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public void setDataInRecycleView(Gateway gateway, String token, List<RoomsInstrument> roomsInstrument) {
        RoomsInstrumentAdapter roomsInstrumentAdapter = new RoomsInstrumentAdapter(
                RoomsInstrumentActivity.this, roomsInstrument, roomName, instrumentName,
                gateway, token);

        recyclerView.setAdapter(roomsInstrumentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomsInstrumentActivity.this));
    }
}