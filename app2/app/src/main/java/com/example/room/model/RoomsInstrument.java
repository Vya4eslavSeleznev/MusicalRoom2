package com.example.room.model;

public class RoomsInstrument {

    private int id;
    private Room room;
    private Instrument instrument;

    public RoomsInstrument(int id, Room room, Instrument instrument) {
        this.id = id;
        this.room = room;
        this.instrument = instrument;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Instrument getInstrument() {
        return instrument;
    }

    public void setInstrument(Instrument instrument) {
        this.instrument = instrument;
    }
}
