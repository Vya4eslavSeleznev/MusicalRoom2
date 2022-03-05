package com.example.room.presenter.fragment;

import android.content.SharedPreferences;

import com.example.room.model.gateways.Gateway;
import com.example.room.model.Customer;

public class ProfilePresenter {

    private final Gateway gateway;
    private final View equipmentFragment;

    public ProfilePresenter(View equipmentFragment) {
        this.gateway = new Gateway();
        this.equipmentFragment = equipmentFragment;
    }

    public Customer getCustomer(String token, int userId) {
        return gateway.getCustomer(token, userId);
    }

    public void setCustomer() {
        equipmentFragment.setCustomer(getToken(), getUserId());
    }

    public void updateCustomer(String token, int customerId, String name, String phone) {
        gateway.updateCustomer(token, customerId, name, phone);
    }

    public void refreshEventLogic() {
        equipmentFragment.refreshEventLogic(getToken());
    }

    private String getToken() {
        return equipmentFragment.getSharedPreferences().getString("token", null);
    }

    private int getUserId() {
        return equipmentFragment.getSharedPreferences().getInt("userId", 0);
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void refreshEventLogic(String token);
        void setCustomer(String token, int userId);
    }
}
