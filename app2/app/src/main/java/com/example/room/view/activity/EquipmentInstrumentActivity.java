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
import com.example.room.adapter.EquipmentInstrumentAdapter;
import com.example.room.model.Instrument;
import com.example.room.presenter.activity.EquipmentInstrumentPresenter;

import java.util.ArrayList;
import java.util.List;

public class EquipmentInstrumentActivity extends AppCompatActivity implements EquipmentInstrumentPresenter.View {

    private EquipmentInstrumentPresenter presenter;
    private RecyclerView recyclerView;
    private ArrayList<String> instrumentName, instrumentDescription;
    private EquipmentInstrumentAdapter instrumentAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equipment_instrument);

        recyclerView = findViewById(R.id.equipment_instrument_recyclerview);
        emptyImageView = findViewById(R.id.empty_equipment_instrument_imageView);
        emptyTextView = findViewById(R.id.empty_equipment_instrument_textView);

        instrumentName = new ArrayList<>();
        instrumentDescription = new ArrayList<>();

        presenter = new EquipmentInstrumentPresenter(this);

        Bundle bundle = getIntent().getExtras();
        int roomId = -1;

        if(bundle != null)
            roomId = bundle.getInt("roomId");

        List<Instrument> instruments = presenter.getRoomsInstrument(
                getSharedPreferences().getString("token", null), roomId);

        setInstruments(instruments);
        setDataInRecycleView();
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
    public void setDataInRecycleView() {
        instrumentAdapter = new EquipmentInstrumentAdapter(EquipmentInstrumentActivity.this,
                instrumentName, instrumentDescription);
        recyclerView.setAdapter(instrumentAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(EquipmentInstrumentActivity.this));
    }
}
