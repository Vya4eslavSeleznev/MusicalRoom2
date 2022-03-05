package com.example.room.model;

import java.util.List;

public class CustomerData {

    private final List<Reservation> reservations;
    private final int customerId;

    public CustomerData(List<Reservation> reservations, int customerId) {
        this.reservations = reservations;
        this.customerId = customerId;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public int getCustomerId() {
        return customerId;
    }
}
