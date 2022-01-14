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
import com.example.room.adapter.AdminInstrumentAdapter;
import com.example.room.model.gateways.Gateway;
import com.example.room.model.Instrument;
import com.example.room.presenter.activity.AdminInstrumentPresenter;

import java.util.ArrayList;
import java.util.List;

public class AdminInstrumentActivity extends AppCompatActivity implements AdminInstrumentPresenter.View {

    private RecyclerView recyclerView;
    private ArrayList<String> instrumentName, instrumentDescription;
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

        AdminInstrumentPresenter presenter = new AdminInstrumentPresenter(this);
        presenter.setInstruments();
        presenter.setDataInRecycleView();
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void setInstruments(List<Instrument> instruments) {
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

    @Override
    public void setDataInRecycleView(List<Instrument> instruments, Gateway gateway, String token) {
        AdminInstrumentAdapter instrumentAdapter = new AdminInstrumentAdapter(AdminInstrumentActivity.this,
                instrumentName, instrumentDescription, instruments, gateway, token);

        recyclerView.setAdapter(instrumentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(AdminInstrumentActivity.this));
    }

    @Override
    public int getRoomId() {
        Bundle bundle = getIntent().getExtras();
        int roomId = -1;

        if(bundle != null)
            roomId = bundle.getInt("roomId");

        return roomId;
    }
}
