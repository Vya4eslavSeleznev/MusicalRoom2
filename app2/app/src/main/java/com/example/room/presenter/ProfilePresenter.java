package com.example.room.presenter;

import android.content.SharedPreferences;

import com.example.room.gateways.Gateway;
import com.example.room.model.Customer;

public class ProfilePresenter {

    private Gateway gateway;
    private View equipmentFragment;

    public ProfilePresenter(View equipmentFragment) {
        this.gateway = new Gateway();
        this.equipmentFragment = equipmentFragment;
    }

    public Customer getCustomer(String token, int userId) {
        return gateway.getCustomer(token, userId);
    }

    public void updateCustomer(String token, int customerId, String name, String phone) {
        gateway.updateCustomer(token, customerId, name, phone);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void refreshEventLogic();
    }
}
