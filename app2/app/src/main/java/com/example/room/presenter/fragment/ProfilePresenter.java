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
        equipmentFragment.setCustomer(getSharedPreferences().getString("token", null),
                getSharedPreferences().getInt("userId", 0));
    }

    public void updateCustomer(String token, int customerId, String name, String phone) {
        gateway.updateCustomer(token, customerId, name, phone);
    }

    public void refreshEventLogic() {
        equipmentFragment.refreshEventLogic(getSharedPreferences().getString("token", null));
    }

    public SharedPreferences getSharedPreferences() {
        return equipmentFragment.getSharedPreferences();
    }

    public interface View {

        SharedPreferences getSharedPreferences();
        void refreshEventLogic(String token);
        void setCustomer(String token, int userId);
    }
}
