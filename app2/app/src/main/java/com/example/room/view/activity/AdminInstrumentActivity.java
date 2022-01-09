package com.example.room.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room.R;
import com.example.room.adapter.AdminInstrumentAdapter;
import com.example.room.gateways.Gateway;
import com.example.room.model.Instrument;

import java.util.ArrayList;
import java.util.List;

public class AdminInstrumentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> instrumentName, instrumentDescription;
    private AdminInstrumentAdapter instrumentAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_instrument);

        recyclerView = findViewById(R.id.admin_instrument_recyclerview);
        emptyImageView = findViewById(R.id.empty_admin_instrument_imageView);
        emptyTextView = findViewById(R.id.empty_admin_instrument_textView);

        instrumentName = new ArrayList<>();
        instrumentDescription = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        int roomId = -1;

        if(bundle != null)
            roomId = bundle.getInt("roomId");

        Gateway gateway = new Gateway();
        String token = getToken();
        List<Instrument> instruments = gateway.getRoomsInstrument(token, roomId);

        setInstruments(instruments);
        setDataInRecycleView(instruments, gateway, token);
    }

    private void setInstruments(List<Instrument> instruments) {
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

    private void setDataInRecycleView(List<Instrument> instruments, Gateway gateway, String token) {
        instrumentAdapter = new AdminInstrumentAdapter(AdminInstrumentActivity.this, instrumentName,
                instrumentDescription, instruments, gateway, token);
        recyclerView.setAdapter(instrumentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminInstrumentActivity.this));
    }

    private String getToken() {
        SharedPreferences preferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }
}