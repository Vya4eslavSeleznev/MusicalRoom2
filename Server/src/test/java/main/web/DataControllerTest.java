package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.Instrument;
import main.entity.Room;
import main.entity.RoomInstrument;
import main.model.InstrumentModel;
import main.model.RoomInstrumentModel;
import main.model.RoomModel;
import main.repository.InstrumentRepository;
import main.repository.RoomInstrumentRepository;
import main.repository.RoomRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
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
class DataControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private InstrumentRepository instrumentRepository;

  @MockBean
  private RoomRepository roomRepository;

  @MockBean
  private RoomInstrumentRepository roomInstrumentRepository;

  @Autowired
  ObjectMapper objectMapper;

  private Room room;
  private Instrument instrument;

  @BeforeEach
  public void setUp() {
    this.room = new Room("TestRoomName", "TestDescription", 4L);
    this.instrument = new Instrument("TestName", "TestDescription");
  }

  @Test
  @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
  public void addInstrument_statusOk() throws Exception {
    InstrumentModel instrumentModel = new InstrumentModel();
    instrumentModel.setName("TestName");
    instrumentModel.setDescription("TestDescription");

    RequestBuilder request = MockMvcRequestBuilders.post("/data/instrument")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(instrumentModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
  public void addRoom_statusOk() throws Exception {
    RoomModel roomModel = new RoomModel();
    roomModel.setName("TestName");
    roomModel.setDescription("TestDescription");
    roomModel.setPrice(444L);

    RequestBuilder request = MockMvcRequestBuilders.post("/data/room")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(roomModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
  public void addRoomInstrument_statusOk() throws Exception {
    room.setId(2L);
    instrument.setId(3L);

    RoomInstrumentModel roomInstrumentModel = new RoomInstrumentModel();
    roomInstrumentModel.setRoomId(room.getId());
    roomInstrumentModel.setInstrumentId(instrument.getId());

    when(roomRepository.findById(room.getId())).thenReturn(java.util.Optional.of(room));
    when(instrumentRepository.findById(instrument.getId())).thenReturn(java.util.Optional.of(instrument));

    RequestBuilder request = MockMvcRequestBuilders.post("/data/room_instrument")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(roomInstrumentModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void deleteRoomInstrument_statusOk() throws Exception {
    final long roomInstrumentId = 1L;
    RoomInstrument roomInstrument = new RoomInstrument();
    roomInstrument.setId(roomInstrumentId);

    roomInstrument.setRoom(room);
    roomInstrument.setInstrument(instrument);

    when(roomInstrumentRepository.findById(roomInstrumentId)).thenReturn(java.util.Optional.of(roomInstrument));

    RequestBuilder request = MockMvcRequestBuilders.delete("/data/room_instrument/{id}", String.valueOf(roomInstrumentId));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void getAllInstrument_statusOk() throws Exception {
    final long instrumentId = 4L;

    List<Instrument> instruments = new ArrayList<>();
    instruments.add(instrument);
    instruments.get(0).setId(instrumentId);

    when(instrumentRepository.findAll()).thenReturn(instruments);

    RequestBuilder request = MockMvcRequestBuilders.get("/data/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(instrumentId))
      .andExpect(jsonPath("[0].name").value(instrument.getName()))
      .andExpect(jsonPath("[0].description").value(instrument.getDescription()));
  }
}
