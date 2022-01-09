package com.example.room.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.room.R;
import com.example.room.adapter.EquipmentRoomAdapter;
import com.example.room.databinding.FragmentEquipmentBinding;
import com.example.room.model.Room;
import com.example.room.presenter.EquipmentPresenter;
import com.example.room.view.activity.EquipmentInstrumentActivity;

import java.util.ArrayList;
import java.util.List;

public class EquipmentFragment extends Fragment implements EquipmentPresenter.View{

    private EquipmentPresenter presenter;
    private FragmentEquipmentBinding binding;
    private RecyclerView recyclerView;
    private ArrayList<String> roomName, roomPrice, roomDescription, instrumentName, instrumentDescription;
    private EquipmentRoomAdapter roomAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEquipmentBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        recyclerView = root.findViewById(R.id.user_rooms_instrument_recyclerview);
        emptyImageView = root.findViewById(R.id.empty2_imageView);
        emptyTextView = root.findViewById(R.id.empty2_textView);

        roomName = new ArrayList<>();
        roomPrice = new ArrayList<>();
        roomDescription = new ArrayList<>();

        roomAdapter = new EquipmentRoomAdapter(getActivity(), roomName, roomDescription, roomPrice);
        recyclerView.setAdapter(roomAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        presenter = new EquipmentPresenter(this);
        List<Room> rooms = presenter.getRooms(getSharedPreferences().getString("token", null));
        setRooms(rooms);

        roomAdapter.setOnItemClickListener(new EquipmentRoomAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), EquipmentInstrumentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putInt("roomId", rooms.get(position).getId());
                intent.putExtras(bundle);
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
    public void setRooms(List<Room> rooms) {
        if(rooms.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= rooms.size() - 1; i++) {
                roomName.add(rooms.get(i).getName());
                roomDescription.add(rooms.get(i).getDescription());
                roomPrice.add(rooms.get(i).getPrice().toString());
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    @Override
    public SharedPreferences getSharedPreferences() {
        return getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
    }
}
