package com.example.room.model;

import java.sql.Date;

public class Reservation {

    private int id;
    private Date date;
    private Room room;
    private Customer customer;

    public Reservation(int id, Date date, Room room, Customer customer) {
        this.id = id;
        this.date = date;
        this.room = room;
        this.customer = customer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
