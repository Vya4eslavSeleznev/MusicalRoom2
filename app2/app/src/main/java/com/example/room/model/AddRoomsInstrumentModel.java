package com.example.room.model;

public class AddRoomsInstrumentModel {

    private Long roomId;
    private Long instrumentId;

    public AddRoomsInstrumentModel(Long roomId, Long instrumentId) {
        this.roomId = roomId;
        this.instrumentId = instrumentId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Long getInstrumentId() {
        return instrumentId;
    }

    public void setInstrumentId(Long instrumentId) {
        this.instrumentId = instrumentId;
    }
}
