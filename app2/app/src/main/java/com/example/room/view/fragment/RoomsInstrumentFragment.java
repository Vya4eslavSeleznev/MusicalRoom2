package com.example.room.view.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.MutableInt;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room.R;
import com.example.room.databinding.FragmentRoomsInstrumentBinding;
import com.example.room.gateways.Gateway;
import com.example.room.model.Instrument;
import com.example.room.model.Room;

import java.util.ArrayList;
import java.util.List;

public class RoomsInstrumentFragment extends Fragment {

    private FragmentRoomsInstrumentBinding binding;
    private Context context;
    private ArrayList<String> instrumentName, roomName;
    private ArrayList<Integer> instrumentId, roomId;
    private Spinner roomSpinner;
    private Spinner instrumentSpinner;
    private MutableInt roomCurrentPosition;
    private MutableInt instrumentCurrentPosition;
    private Button addEquipmentButton;
    private Button viewEquipmentButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentRoomsInstrumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);

        Gateway gateway = new Gateway();
        String token = getToken();

        List<Room> rooms = gateway.getAllRooms(token);
        List<Instrument> instruments = gateway.getAllInstruments(token);

        parseRoomData(rooms);
        parseInstrumentData(instruments);

        setSpinners();

        roomCurrentPosition = new MutableInt(0);
        instrumentCurrentPosition = new MutableInt(0);

        spinnerEvent(roomSpinner, roomCurrentPosition);
        spinnerEvent(instrumentSpinner, instrumentCurrentPosition);

        addEquipmentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gateway.addRoomsInstrument(token, (long) rooms.get(roomCurrentPosition.value).getId(),
                        (long) instruments.get(instrumentCurrentPosition.value).getId());

                Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show();
            }
        });

        return root;
    }

    private void init(View root) {
        roomSpinner = root.findViewById(R.id.room_spinner);
        instrumentSpinner = root.findViewById(R.id.instrument_spinner);
        addEquipmentButton = root.findViewById(R.id.add_equipment_button);

        roomId = new ArrayList<>();
        roomName = new ArrayList<>();
        instrumentId = new ArrayList<>();
        instrumentName = new ArrayList<>();
    }

    private void setSpinners() {
        ArrayAdapter<String> roomSpinnerAdapter = new ArrayAdapter<String> (context,
                android.R.layout.simple_spinner_dropdown_item, roomName);

        ArrayAdapter<String> instrumentSpinnerAdapter = new ArrayAdapter<String> (context,
                android.R.layout.simple_spinner_dropdown_item, instrumentName);

        roomSpinner.setAdapter(roomSpinnerAdapter);
        instrumentSpinner.setAdapter(instrumentSpinnerAdapter);
    }

    private String getToken() {
        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }

    private void parseRoomData(List<Room> rooms) {
        for(int i = 0; i <= rooms.size() - 1; i++) {
            roomId.add(rooms.get(i).getId());
            roomName.add(rooms.get(i).getName());
        }
    }

    private void parseInstrumentData(List<Instrument> instruments) {
        for(int i = 0; i <= instruments.size() - 1; i++) {
            instrumentId.add(instruments.get(i).getId());
            instrumentName.add(instruments.get(i).getName());
        }
    }

    private void spinnerEvent(Spinner spinner, MutableInt pos) {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                pos.value = spinner.getSelectedItemPosition();
                System.out.println(spinner.getSelectedItem() + " .Position: " +  spinner.getSelectedItemPosition());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}