package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.*;
import main.model.InstrumentModel;
import main.model.RoomInstrumentModel;
import main.model.RoomModel;
import main.repository.InstrumentRepository;
import main.repository.ReservationRepository;
import main.repository.RoomInstrumentRepository;
import main.repository.RoomRepository;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
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
  private ReservationRepository reservationRepository;

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

    room.setId(2L);
    instrument.setId(3L);
  }

  @Test
  @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
  public void addInstrument_statusOk() throws Exception {
    InstrumentModel instrumentModel = new InstrumentModel();
    instrumentModel.setName("TestName");
    instrumentModel.setDescription("TestDescription");

    RequestBuilder request = MockMvcRequestBuilders.post("/data/instrument")
      .contentType(MediaType.APPLICATION_JSON)
      .content(this.objectMapper.writeValueAsString(instrumentModel));

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
      .content(this.objectMapper.writeValueAsString(roomModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "admin", password = "password", roles = "ADMIN")
  public void addRoomInstrument_statusOk() throws Exception {
    //this.room.setId(2L);
    //this.instrument.setId(3L);

    RoomInstrumentModel roomInstrumentModel = new RoomInstrumentModel();
    roomInstrumentModel.setRoomId(this.room.getId());
    roomInstrumentModel.setInstrumentId(this.instrument.getId());

    when(this.roomRepository.findById(this.room.getId())).thenReturn(java.util.Optional.of(this.room));
    when(this.instrumentRepository.findById(this.instrument.getId())).thenReturn(java.util.Optional.of(this.instrument));

    RequestBuilder request = MockMvcRequestBuilders.post("/data/room_instrument")
      .contentType(MediaType.APPLICATION_JSON)
      .content(this.objectMapper.writeValueAsString(roomInstrumentModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void deleteRoomInstrument_statusOk() throws Exception {
    final long roomInstrumentId = 1L;
    RoomInstrument roomInstrument = new RoomInstrument();
    roomInstrument.setId(roomInstrumentId);

    roomInstrument.setRoom(this.room);
    roomInstrument.setInstrument(this.instrument);

    when(this.roomInstrumentRepository.findById(roomInstrumentId)).thenReturn(java.util.Optional.of(roomInstrument));

    RequestBuilder request = MockMvcRequestBuilders.delete("/data/room_instrument/{id}", String.valueOf(roomInstrumentId));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void deleteRoom_statusOk() throws Exception {
    List<Reservation> reservations = new ArrayList<>();
    List<RoomInstrument> roomInstruments = new ArrayList<>();

    reservations.add(new Reservation(new java.sql.Date(convertToDate().getTime()), room,
      new Customer("name", "1234", new User("userName", "password", "USER"))));

    roomInstruments.add(new RoomInstrument(this.room, this.instrument));

    when(roomRepository.findById(this.room.getId())).thenReturn(java.util.Optional.ofNullable(this.room));
    when(roomInstrumentRepository.findAll()).thenReturn(roomInstruments);
    when(reservationRepository.findAll()).thenReturn(reservations);

    RequestBuilder request = MockMvcRequestBuilders.delete("/data/room/{id}", String.valueOf(this.room.getId()));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void getAllInstrument_statusOk() throws Exception {
    final long instrumentId = 4L;

    List<Instrument> instruments = new ArrayList<>();
    instruments.add(this.instrument);
    instruments.get(0).setId(instrumentId);

    when(this.instrumentRepository.findAll()).thenReturn(instruments);

    RequestBuilder request = MockMvcRequestBuilders.get("/data/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(instrumentId))
      .andExpect(jsonPath("[0].name").value(this.instrument.getName()))
      .andExpect(jsonPath("[0].description").value(this.instrument.getDescription()));
  }




  private java.util.Date convertToDate() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String dateToParse = "25-08-2022";
    return formatter.parse(dateToParse);
  }
}
