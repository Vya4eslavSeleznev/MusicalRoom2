package com.example.room.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.room.R;
import com.example.room.adapter.CustomerAdapter;
import com.example.room.model.gateways.Gateway;
import com.example.room.model.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomerActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> customerName, customerPhone;
    private CustomerAdapter customerAdapter;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        recyclerView = findViewById(R.id.customer_recyclerview);
        emptyImageView = findViewById(R.id.empty_customer_imageView);
        emptyTextView = findViewById(R.id.empty_customer_textView);

        customerName = new ArrayList<>();
        customerPhone = new ArrayList<>();

        Gateway gateway = new Gateway();
        String token = getToken();

        List<Customer> customers = gateway.getAllCustomers(token);

        setCustomers(customers);
        setDataInRecycleView();
    }

    private String getToken() {
        SharedPreferences preferences = this.getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        return preferences.getString("token", null);
    }

    private void setCustomers(List<Customer> customers) {
        if(customers.size() == 0) {
            emptyImageView.setVisibility(View.VISIBLE);
            emptyTextView.setVisibility(View.VISIBLE);
        } else {
            for(int i = 0; i <= customers.size() - 1; i++) {
                customerName.add(customers.get(i).getName());
                customerPhone.add(customers.get(i).getPhone());
            }

            emptyImageView.setVisibility(View.GONE);
            emptyTextView.setVisibility(View.GONE);
        }
    }

    private void setDataInRecycleView() {
        customerAdapter = new CustomerAdapter(CustomerActivity.this,
                customerName, customerPhone);
        recyclerView.setAdapter(customerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(CustomerActivity.this));
    }
}