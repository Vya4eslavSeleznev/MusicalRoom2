package com.example.room.view.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.InstrumentAdapter;
import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;

import java.util.ArrayList;
import java.util.List;

public class InstrumentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> instrumentName, instrumentDescription;
    private InstrumentAdapter instrumentAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instrument);

        recyclerView = findViewById(R.id.instrument_recyclerview);
        emptyImageView = findViewById(R.id.empty_instrument_imageView);
        emptyTextView = findViewById(R.id.empty_instrument_textView);

        instrumentName = new ArrayList<>();
        instrumentDescription = new ArrayList<>();

        Gateway gateway = new Gateway();
        String token = getToken();

        List<Instrument> instruments = gateway.getAllInstruments(token);

        setRooms(instruments);
        setDataInRecycleView(gateway, token, instruments);
    }

    private void setRooms(List<Instrument> instruments) {
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

    private String getToken() {
        SharedPreferences preferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }

    private void setDataInRecycleView(Gateway gateway, String token, List<Instrument> instruments) {
        instrumentAdapter = new InstrumentAdapter(InstrumentActivity.this, instrumentName, instrumentDescription,
                instruments, gateway, token);
        recyclerView.setAdapter(instrumentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(InstrumentActivity.this));
    }
}