package com.example.room.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room.R;
import com.example.room.adapter.AdminRoomAdapter;
import com.example.room.gateways.Gateway;
import com.example.room.model.Room;

import java.util.ArrayList;
import java.util.List;

public class AdminRoomActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomDescription, roomPrice;
    private AdminRoomAdapter adminRoomAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;

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

        Gateway gateway = new Gateway();
        String token = getToken();

        List<Room> rooms = gateway.getAllRooms(token);

        setRooms(rooms);
        setDataInRecycleView(gateway, token, rooms);

        adminRoomAdapter.setOnItemClickListener(new AdminRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                System.out.println("================================CLICK ON RECYCLEVIEW=====================" + position);

                Intent intent = new Intent(AdminRoomActivity.this, AdminInstrumentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("roomId", rooms.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
    private void setRooms(List<Room> rooms) {
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

    private String getToken() {
        SharedPreferences preferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }

    private void setDataInRecycleView(Gateway gateway, String token, List<Room> rooms) {
        adminRoomAdapter = new AdminRoomAdapter(AdminRoomActivity.this, roomName, roomDescription, roomPrice,
                gateway, token, rooms);
        recyclerView.setAdapter(adminRoomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }
}