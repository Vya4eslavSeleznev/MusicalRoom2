package main.web;

import main.entity.Customer;
import main.entity.Instrument;
import main.entity.Room;
import main.entity.RoomInstrument;
import main.exception.EntityNotFoundException;
import main.repository.CustomerRepository;
import main.repository.RoomInstrumentRepository;
import main.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class RoomControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private RoomRepository roomRepository;

  @MockBean
  private RoomInstrumentRepository roomInstrumentRepository;

  private Room room;
  private List<Room> rooms;

  @BeforeEach
  public void setUp() {
    this.room = new Room("name", "description", 123L);
    this.room.setId(4L);

    this.rooms = new ArrayList<>();
  }

  @Test
  void getAllRooms_statusOk() throws Exception {
    this.rooms.add(room);

    when(this.roomRepository.findAll()).thenReturn(this.rooms);

    RequestBuilder request = MockMvcRequestBuilders.get("/rooms/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(this.room.getId()))
      .andExpect(jsonPath("[0].name").value(this.room.getName()))
      .andExpect(jsonPath("[0].description").value(this.room.getDescription()));
  }

  @Test
  public void getInstrumentsByRoomId_statusOk() throws Exception {
    List<RoomInstrument> roomInstruments = new ArrayList<>();
    Instrument instrument = new Instrument("name", "description");

    this.room.setId(3L);
    instrument.setId(4L);

    roomInstruments.add(new RoomInstrument(this.room, instrument));

    when(this.roomInstrumentRepository.findAll()).thenReturn(roomInstruments);

    RequestBuilder request = MockMvcRequestBuilders.get("/rooms/{id}/instruments",
      String.valueOf(roomInstruments.get(0).getRoom().getId()));

    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(roomInstruments.get(0).getInstrument().getId()))
      .andExpect(jsonPath("[0].name").value(roomInstruments.get(0).getInstrument().getName()))
      .andExpect(jsonPath("[0].description").value(roomInstruments.get(0).getInstrument().getDescription()));
  }
}
