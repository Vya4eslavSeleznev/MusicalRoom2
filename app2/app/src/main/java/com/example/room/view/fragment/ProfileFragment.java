package com.example.room.view.fragment;

import android.content.Context;
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
import com.example.room.presenter.ProfilePresenter;
import com.example.room.view.activity.ReservationActivity;

public class ProfileFragment extends Fragment implements ProfilePresenter.View {

    private ProfilePresenter presenter;
    private FragmentProfileBinding binding;
    private TextView nameTextView;
    private TextView phoneTextView;
    private Button refreshButton;
    private Button myRoomsButton;
    private Customer customer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        myRoomsButton = root.findViewById(R.id.my_rooms_button);
        nameTextView = root.findViewById(R.id.editName);
        phoneTextView = root.findViewById(R.id.editPhone);
        refreshButton = root.findViewById(R.id.refresh_button);

        presenter = new ProfilePresenter(this);
        customer = presenter.getCustomer(getSharedPreferences().getString("token", null),
                getSharedPreferences().getInt("userId", 0));

        nameTextView.setText(customer.getName());
        phoneTextView.setText(customer.getPhone());

        myRoomsButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReservationActivity.class);
                startActivity(intent);
            }
        });

        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshEventLogic();
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
    public void refreshEventLogic() {
        if(!nameTextView.getText().toString().matches("") ||
                !phoneTextView.getText().toString().matches("") ||
                (nameTextView.getText().toString().matches("") &&
                        phoneTextView.getText().toString().matches("")))
        {
            presenter.updateCustomer(getSharedPreferences().getString("token", null), this.customer.getId(),
                    nameTextView.getText().toString(), phoneTextView.getText().toString());

            Toast.makeText(getActivity(), "Updated successfully!", Toast.LENGTH_SHORT).show();

        } else {
            Toast.makeText(getActivity(), "Empty field", Toast.LENGTH_SHORT).show();
        }
    }
}
