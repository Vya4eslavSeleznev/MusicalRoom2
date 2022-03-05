package com.example.room.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room.R;
import com.example.room.databinding.FragmentRoomBinding;
import com.example.room.presenter.Repository;
import com.example.room.presenter.fragment.RoomPresenter;
import com.example.room.view.activity.RoomActivity;

public class RoomFragment extends Fragment implements RoomPresenter.View {

    private RoomPresenter presenter;
    private FragmentRoomBinding binding;
    private EditText nameTextView;
    private EditText descriptionTextView;
    private EditText priceTextView;
    private Repository repository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRoomBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addRoom = root.findViewById(R.id.add_room_button);
        Button getAllRooms = root.findViewById(R.id.view_rooms_button);
        nameTextView = root.findViewById(R.id.room_name_edit);
        descriptionTextView = root.findViewById(R.id.room_description_edit);
        priceTextView = root.findViewById(R.id.room_price_edit);

        presenter = new RoomPresenter(this);
        repository = new Repository();

        addRoom.setOnClickListener(v -> presenter.addRoomEventLogic());

        getAllRooms.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RoomActivity.class);
            startActivity(intent);
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
        return repository.getSharedPreferences(getActivity());
    }

    @Override
    public void addRoomEventLogic() {
        if(!nameTextView.getText().toString().matches("") ||
           !descriptionTextView.getText().toString().matches("") ||
           !priceTextView.getText().toString().matches(""))
        {
            presenter.addRoom(nameTextView.getText().toString(), descriptionTextView.getText().toString(),
                    Long.parseLong(priceTextView.getText().toString()));

            Toast.makeText(getActivity(), "Added successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}
