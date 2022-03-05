package com.example.room.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.room.R;
import com.example.room.databinding.FragmentProfileBinding;
import com.example.room.model.Customer;
import com.example.room.presenter.Repository;
import com.example.room.presenter.fragment.ProfilePresenter;
import com.example.room.view.activity.ReservationActivity;

public class ProfileFragment extends Fragment implements ProfilePresenter.View {

    private ProfilePresenter presenter;
    private FragmentProfileBinding binding;
    private TextView nameTextView;
    private TextView phoneTextView;
    private Customer customer;
    private Repository repository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button myRoomsButton = root.findViewById(R.id.my_rooms_button);
        Button refreshButton = root.findViewById(R.id.refresh_button);
        nameTextView = root.findViewById(R.id.editName);
        phoneTextView = root.findViewById(R.id.editPhone);

        repository = new Repository();

        presenter = new ProfilePresenter(this);
        presenter.setCustomer();

        myRoomsButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ReservationActivity.class);
            startActivity(intent);
        });

        refreshButton.setOnClickListener(v -> presenter.refreshEventLogic());

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
    public void refreshEventLogic(String token) {
        if(!nameTextView.getText().toString().matches("") ||
                !phoneTextView.getText().toString().matches("") ||
                (nameTextView.getText().toString().matches("") &&
                        phoneTextView.getText().toString().matches("")))
        {
            presenter.updateCustomer(token, this.customer.getId(),
                    nameTextView.getText().toString(), phoneTextView.getText().toString());

            Toast.makeText(getActivity(), "Updated successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void setCustomer(String token, int userId) {
        customer = presenter.getCustomer(token, userId);
        nameTextView.setText(customer.getName());
        phoneTextView.setText(customer.getPhone());
    }
}
