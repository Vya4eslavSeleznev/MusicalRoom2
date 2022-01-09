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
import com.example.room.adapter.RoomAdapter;
import com.example.room.databinding.FragmentRoomBinding;
import com.example.room.gateways.Gateway;
import com.example.room.view.activity.RoomActivity;

import java.util.ArrayList;

public class RoomFragment extends Fragment {

    private FragmentRoomBinding binding;
    private Context context;
    private Button addRoom;
    private Button getAllRooms;
    private EditText nameTextView;
    private EditText descriptionTextView;
    private EditText priceTextView;
    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomPrice, roomDescription;
    private ImageView emptyImageView;
    private TextView emptyTextView;
    private RoomAdapter roomAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        init(root);

        Gateway gateway = new Gateway();
        String token = getToken();

        addRoom.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!nameTextView.getText().toString().matches("") ||
                   !descriptionTextView.getText().toString().matches("") ||
                   !priceTextView.getText().toString().matches(""))
                {
                    gateway.addRoom(token, nameTextView.getText().toString(), descriptionTextView.getText().toString(),
                            Long.parseLong(priceTextView.getText().toString()));

                    Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Empty field", Toast.LENGTH_SHORT).show();
                }


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

    private String getToken() {
        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }

    private void init(View root) {
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
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}