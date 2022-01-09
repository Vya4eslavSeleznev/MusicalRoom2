package com.example.room.model;

public class ReservationCreate {

    private String date;
    private int roomId;
    private int customerId;

    public ReservationCreate(String date, int roomId, int customerId) {
        this.date = date;
        this.roomId = roomId;
        this.customerId = customerId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }
}
