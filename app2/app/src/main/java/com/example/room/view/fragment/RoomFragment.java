package com.example.room.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.databinding.FragmentRoomBinding;
import com.example.room.presenter.RoomPresenter;
import com.example.room.view.activity.RoomActivity;

import java.util.ArrayList;

public class RoomFragment extends Fragment implements RoomPresenter.View {

    private RoomPresenter presenter;
    private FragmentRoomBinding binding;
    private Button addRoom;
    private Button getAllRooms;
    private EditText nameTextView;
    private EditText descriptionTextView;
    private EditText priceTextView;
    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomPrice, roomDescription;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        roomName = new ArrayList<>();
        roomPrice = new ArrayList<>();
        roomDescription = new ArrayList<>();

        addRoom = root.findViewById(R.id.add_room_button);
        getAllRooms = root.findViewById(R.id.view_rooms_button);
        nameTextView = root.findViewById(R.id.room_name_edit);
        descriptionTextView = root.findViewById(R.id.room_description_edit);
        priceTextView = root.findViewById(R.id.room_price_edit);
        recyclerView = root.findViewById(R.id.room_recyclerview);
        emptyImageView = root.findViewById(R.id.empty_room_imageView);
        emptyTextView = root.findViewById(R.id.empty_room_textView);

        presenter = new RoomPresenter(this);

        addRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addRoomEventLogic();
            }
        });

        getAllRooms.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), RoomActivity.class);
                startActivity(intent);
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
    public void addRoomEventLogic() {
        if(!nameTextView.getText().toString().matches("") ||
           !descriptionTextView.getText().toString().matches("") ||
           !priceTextView.getText().toString().matches(""))
        {
            presenter.addRoom(getSharedPreferences().getString("token", null),
                    nameTextView.getText().toString(), descriptionTextView.getText().toString(),
                    Long.parseLong(priceTextView.getText().toString()));

            Toast.makeText(getActivity(), "Added successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}