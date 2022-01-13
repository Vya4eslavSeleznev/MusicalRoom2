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
import com.example.room.databinding.FragmentCustomerBinding;
import com.example.room.model.gateways.Gateway;
import com.example.room.view.activity.CustomerActivity;

public class CustomerFragment extends Fragment {

    private FragmentCustomerBinding binding;
    private Context context;
    private Button customersButton;
    private Button addCustomer;
    private TextView nameTextView;
    private TextView phoneTextView;
    private TextView loginTextView;
    private TextView passwordTextView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        context = getActivity();
        binding = FragmentCustomerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        addCustomer = root.findViewById(R.id.add_customer_button);
        customersButton = root.findViewById(R.id.view_customers_button);
        nameTextView = root.findViewById(R.id.customer_editName);
        phoneTextView = root.findViewById(R.id.customer_editPhone);
        loginTextView = root.findViewById(R.id.customer_editLogin);
        passwordTextView = root.findViewById(R.id.customer_editPassword);

        Gateway gateway = new Gateway();
        SharedPreferences preferences = getActivity().getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        String token = preferences.getString("token", null);


        addCustomer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!nameTextView.getText().toString().matches("") ||
                   !phoneTextView.getText().toString().matches("") ||
                   !loginTextView.getText().toString().matches("") ||
                   !passwordTextView.getText().toString().matches(""))
                {
                    gateway.addCustomer(token, loginTextView.getText().toString(), passwordTextView.getText().toString(),
                            nameTextView.getText().toString(), phoneTextView.getText().toString());

                    Toast.makeText(context, "Added successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, "Empty field", Toast.LENGTH_SHORT).show();
                }


            }
        });

        customersButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CustomerActivity.class);
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
}
