package com.example.room.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.AdminRoomAdapter;
import com.example.room.model.Room;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.Repository;
import com.example.room.presenter.activity.AdminRoomPresenter;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomActivity extends AppCompatActivity implements AdminRoomPresenter.View {

    private AdminRoomPresenter presenter;
    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomDescription, roomPrice;
    private AdminRoomAdapter adminRoomAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_room);

        recyclerView = findViewById(R.id.admin_room_recyclerview);
        emptyImageView = findViewById(R.id.admin_empty_room_imageView);
        emptyTextView = findViewById(R.id.admin_empty_room_textView);

        roomName = new ArrayList<>();
        roomDescription = new ArrayList<>();
        roomPrice = new ArrayList<>();

        repository = new Repository();

        presenter = new AdminRoomPresenter(this);
        presenter.setRooms();
        presenter.setDataInRecycleView();

        adminRoomAdapter.setOnItemClickListener(position -> {
            Intent intent = presenter.adapterEventLogic(position);
            startActivity(intent);
        });
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
        adminRoomAdapter = new AdminRoomAdapter(AdminRoomActivity.this, roomName,
                roomDescription, roomPrice, gateway, token, rooms);

        recyclerView.setAdapter(adminRoomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public Intent adapterEventLogic(int position) {
        Intent intent = new Intent(AdminRoomActivity.this, AdminInstrumentActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("roomId", presenter.getRooms().get(position).getId());
        intent.putExtras(bundle);

        return intent;
    }
}
