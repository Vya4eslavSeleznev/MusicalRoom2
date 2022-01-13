package com.example.room.view.fragment;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room.R;
import com.example.room.databinding.FragmentReserveBinding;
import com.example.room.model.Customer;
import com.example.room.model.Room;
import com.example.room.presenter.fragment.ReservationPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationFragment extends Fragment implements ReservationPresenter.View {

    private ReservationPresenter presenter;
    private FragmentReserveBinding binding;
    private TextView datePicker;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ArrayList<String> roomName;
    private ArrayList<Integer> roomId;
    private Spinner roomSpinner;
    private int currentPosition;
    private String selectedDate;
    private Customer customer;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReserveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button reservationButton = root.findViewById(R.id.reservationButton);
        Button dateButton = root.findViewById(R.id.dateButton);
        roomSpinner = root.findViewById(R.id.roomSpinner);
        datePicker = root.findViewById(R.id.datePicker);

        roomId = new ArrayList<>();
        roomName = new ArrayList<>();

        presenter = new ReservationPresenter(this);
        token = presenter.getSharedPreferences().getString("token", null);
        presenter.parseRoomData(token);
        customer = presenter.getCustomer(token, getSharedPreferences().getInt("userId", 0));

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String> (getActivity(),
                android.R.layout.simple_spinner_dropdown_item, roomName);

        roomSpinner.setAdapter(spinnerAdapter);

        roomSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long arg3) {
                currentPosition = roomSpinner.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        dateButton.setOnClickListener(v -> presenter.createDateDialog());
        dateSetListener = (view, year, month, day) -> presenter.setDate(year, month, day);

        reservationButton.setOnClickListener(v -> {
            presenter.addReservation(token, selectedDate, roomId.get(currentPosition), customer.getId());
            Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
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
    public void createDateDialog() {
        Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,
                dateSetListener, year, month + 1, day);

        dialog.show();
    }

    @Override
    public void setDate(int year, int month, int day) {
        month += 1;
        Log.d("", "OnDateSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);
        String date = year + "-" + month + "-" + day;
        datePicker.setText(date);
        selectedDate = datePicker.getText().toString();
    }
}
