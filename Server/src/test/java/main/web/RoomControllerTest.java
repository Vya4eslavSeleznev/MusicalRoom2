package main.web;

import main.entity.Instrument;
import main.entity.Room;
import main.entity.RoomInstrument;
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
  private List<RoomInstrument> roomInstruments;
  private Instrument instrument;

  @BeforeEach
  public void setUp() {
    this.room = new Room("name", "description", 123L);
    this.instrument = new Instrument("name", "description");

    this.room.setId(3L);
    this.instrument.setId(4L);

    this.rooms = new ArrayList<>();
    this.roomInstruments = new ArrayList<>();

    this.roomInstruments.add(new RoomInstrument(this.room, this.instrument));
    this.roomInstruments.get(0).setId(3L);
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
  public void getAllRoomsInstruments_statusOk() throws Exception {
    when(this.roomInstrumentRepository.findAll()).thenReturn(this.roomInstruments);

    RequestBuilder request = MockMvcRequestBuilders.get("/rooms/instruments");

    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(this.roomInstruments.get(0).getId()))
      .andExpect(jsonPath("[0].room.name").value(this.room.getName()))
      .andExpect(jsonPath("[0].room.description").value(this.room.getDescription()))
      .andExpect(jsonPath("[0].room.price").value(this.room.getPrice()))
      .andExpect(jsonPath("[0].instrument.name").value(this.instrument.getName()))
      .andExpect(jsonPath("[0].instrument.description").value(this.instrument.getDescription()));
  }

  @Test
  public void getInstrumentsByRoomId_statusOk() throws Exception {
    when(this.roomInstrumentRepository.findAll()).thenReturn(this.roomInstruments);

    RequestBuilder request = MockMvcRequestBuilders.get("/rooms/{id}/instruments",
      String.valueOf(this.roomInstruments.get(0).getRoom().getId()));

    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(this.roomInstruments.get(0).getInstrument().getId()))
      .andExpect(jsonPath("[0].name").value(this.roomInstruments.get(0).getInstrument().getName()))
      .andExpect(jsonPath("[0].description").value(this.roomInstruments.get(0).getInstrument().getDescription()));
  }
}
