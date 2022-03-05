package com.example.room.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.RoomAdapter;
import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.Repository;
import com.example.room.presenter.activity.RoomPresenter;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity implements RoomPresenter.View {

    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomDescription, roomPrice;
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
        roomDescription = new ArrayList<>();
        roomPrice = new ArrayList<>();

        repository = new Repository();

        RoomPresenter presenter = new RoomPresenter(this);
        presenter.setRooms();
        presenter.setDataInRecycleView();
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreferences(this);
    }

    @Override
    public void setRooms(List<Room> rooms) {
        repository.setRooms(rooms, roomName, roomDescription, roomPrice, emptyImageView, emptyTextView);
    }

    @Override
    public void setDataInRecycleView(Gateway gateway, String token, List<Room> rooms) {
        RoomAdapter roomAdapter = new RoomAdapter(RoomActivity.this, roomName, roomDescription, roomPrice,
                rooms, gateway, token);

        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomActivity.this));
    }
}
