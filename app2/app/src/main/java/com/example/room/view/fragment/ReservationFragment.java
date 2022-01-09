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
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room.R;
import com.example.room.databinding.FragmentReserveBinding;
import com.example.room.model.Customer;
import com.example.room.model.Room;
import com.example.room.presenter.ReservationPresenter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ReservationFragment extends Fragment implements ReservationPresenter.View {

    private ReservationPresenter presenter;
    private FragmentReserveBinding binding;
    private TextView datePicker;
    private Button dateButton;
    private DatePickerDialog.OnDateSetListener dateSetListener;
    private ArrayList<String> roomName;
    private ArrayList<Integer> roomId;
    private Button reservationButton;
    private Spinner roomSpinner;
    private int currentPosition;
    private String selectedDate;
    private Customer customer;
    private String token;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentReserveBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        reservationButton = root.findViewById(R.id.reservationButton);
        roomSpinner = root.findViewById(R.id.roomSpinner);
        datePicker = root.findViewById(R.id.datePicker);
        dateButton = root.findViewById(R.id.dateButton);

        roomId = new ArrayList<>();
        roomName = new ArrayList<>();

        token = getSharedPreferences().getString("token", null);

        presenter = new ReservationPresenter(this);
        customer = presenter.getCustomer(token, getSharedPreferences().getInt("userId", 0));

        parseRoomData(presenter.getRooms(token));

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

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();

                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(getContext(), android.R.style.Theme_DeviceDefault_Dialog,
                        dateSetListener, year, month + 1, day);

                dialog.show();
            }
        });

        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                month += 1;
                Log.d("", "OnDateSet: yyyy-mm-dd: " + year + "-" + month + "-" + day);
                String date = year + "-" + month + "-" + day;
                datePicker.setText(date);
                selectedDate = datePicker.getText().toString();
            }
        };


        reservationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.addReservation(token, selectedDate, roomId.get(currentPosition), customer.getId());
                Toast.makeText(getActivity(), "Successful!", Toast.LENGTH_SHORT).show();
            }
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
}
