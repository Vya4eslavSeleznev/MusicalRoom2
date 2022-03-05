package com.example.room.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.AdminInstrumentAdapter;
import com.example.room.model.Instrument;
import com.example.room.model.gateways.Gateway;
import com.example.room.presenter.Repository;
import com.example.room.presenter.activity.AdminInstrumentPresenter;

import java.util.ArrayList;
import java.util.List;

public class AdminInstrumentActivity extends AppCompatActivity implements AdminInstrumentPresenter.View {

    private RecyclerView recyclerView;
    private ArrayList<String> instrumentName, instrumentDescription;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private Repository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_instrument);

        recyclerView = findViewById(R.id.admin_instrument_recyclerview);
        emptyImageView = findViewById(R.id.empty_admin_instrument_imageView);
        emptyTextView = findViewById(R.id.empty_admin_instrument_textView);

        instrumentName = new ArrayList<>();
        instrumentDescription = new ArrayList<>();

        repository = new Repository();

        AdminInstrumentPresenter presenter = new AdminInstrumentPresenter(this);
        presenter.setInstruments();
        presenter.setDataInRecycleView();
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return repository.getSharedPreferences(this);
    }

    @Override
    public void setInstruments(List<Instrument> instruments) {
        repository.setInstruments(instruments, instrumentName, instrumentDescription,
                emptyImageView, emptyTextView);
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

        return repository.getRoomId(bundle);
    }
}
