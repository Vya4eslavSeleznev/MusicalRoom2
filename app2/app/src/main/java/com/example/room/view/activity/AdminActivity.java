package com.example.room.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import com.example.room.R;
import com.example.room.databinding.ActivityAdminBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AdminActivity extends AppCompatActivity {

    private ActivityAdminBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        BottomNavigationView adminNavView = findViewById(R.id.admin_nav_view);

        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_customer,
                R.id.navigation_room,
                R.id.navigation_instrument,
                R.id.navigation_rooms_instrument)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_admin_activity);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.adminNavView, navController);
    }
}
