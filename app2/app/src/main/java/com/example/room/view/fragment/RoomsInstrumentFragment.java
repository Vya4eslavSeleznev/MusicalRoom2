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
import com.example.room.model.Instrument;
import com.example.room.model.Room;
import com.example.room.presenter.fragment.RoomsInstrumentPresenter;

import java.util.ArrayList;
import java.util.List;

public class RoomsInstrumentFragment extends Fragment implements RoomsInstrumentPresenter.View {

    private RoomsInstrumentPresenter presenter;
    private FragmentRoomsInstrumentBinding binding;
    private Context context;
    private ArrayList<String> instrumentName, roomName;
    private ArrayList<Integer> instrumentId, roomId;
    private Spinner roomSpinner;
    private Spinner instrumentSpinner;
    private MutableInt roomCurrentPosition;
    private MutableInt instrumentCurrentPosition;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentRoomsInstrumentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        roomSpinner = root.findViewById(R.id.room_spinner);
        instrumentSpinner = root.findViewById(R.id.instrument_spinner);
        Button addEquipmentButton = root.findViewById(R.id.add_equipment_button);

        roomId = new ArrayList<>();
        roomName = new ArrayList<>();
        instrumentId = new ArrayList<>();
        instrumentName = new ArrayList<>();

        presenter = new RoomsInstrumentPresenter(this);
        presenter.parseData();
        presenter.setSpinners();

        roomCurrentPosition = new MutableInt(0);
        instrumentCurrentPosition = new MutableInt(0);

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                presenter.spinnerEventLogic(roomSpinner, roomCurrentPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        instrumentSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3)
            {
                presenter.spinnerEventLogic(instrumentSpinner, instrumentCurrentPosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        addEquipmentButton.setOnClickListener(v -> {
            String token = presenter.getSharedPreferences().getString("token", null);

            presenter.addRoomsInstrument(token,
                    (long) presenter.getRooms(token).get(roomCurrentPosition.value).getId(),
                    (long) presenter.getInstruments(token).get(instrumentCurrentPosition.value).getId());

            Toast.makeText(context, "Successful!", Toast.LENGTH_SHORT).show();
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void parseRoomData(List<Room> rooms) {
        for(int i = 0; i <= rooms.size() - 1; i++) {
            roomId.add(rooms.get(i).getId());
            roomName.add(rooms.get(i).getName());
        }
    }

    @Override
    public void parseInstrumentData(List<Instrument> instruments) {
        for(int i = 0; i <= instruments.size() - 1; i++) {
            instrumentId.add(instruments.get(i).getId());
            instrumentName.add(instruments.get(i).getName());
        }
    }

    @Override
    public void setSpinners() {
        ArrayAdapter<String> roomSpinnerAdapter = new ArrayAdapter<> (context,
                android.R.layout.simple_spinner_dropdown_item, roomName);

        ArrayAdapter<String> instrumentSpinnerAdapter = new ArrayAdapter<> (context,
                android.R.layout.simple_spinner_dropdown_item, instrumentName);

        roomSpinner.setAdapter(roomSpinnerAdapter);
        instrumentSpinner.setAdapter(instrumentSpinnerAdapter);
    }

    @Override
    public void spinnerEventLogic(Spinner spinner, MutableInt pos) {
        pos.value = spinner.getSelectedItemPosition();
        System.out.println(spinner.getSelectedItem() + " .Position: " +  spinner.getSelectedItemPosition());
    }
}
