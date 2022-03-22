package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import main.entity.*;
import main.exception.EntityNotFoundException;
import main.model.ReservationModel;
import main.repository.CustomerRepository;
import main.repository.ReservationRepository;
import main.repository.RoomRepository;
import org.json.JSONObject;
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
class ReservationControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ReservationRepository reservationRepository;

  /*@MockBean
  private CustomerRepository customerRepository;

  @MockBean
  private RoomRepository roomRepository;*/

  @Autowired
  ObjectMapper objectMapper;

  private Room room;
  private Customer customer;
  private final long reservationId = 4L;
  private Reservation reservation;
  private List<Reservation> reservations;

  @BeforeEach
  public void setUp() throws Exception {
    this.room = new Room("TestName", "TestDescription", 123L);
    this.customer = new Customer("TestName", "444", new User("TestUserName", "Password", "USER"));
    this.reservation = new Reservation(new java.sql.Date(convertToDate().getTime()), room, customer);
    this.reservations = new ArrayList<>();

    this.room.setId(4L);
    this.customer.setId(5L);
    this.customer.getUser().setId(6L);
    this.customer.setUserId(6L);
    this.reservation.setId(6L);
    this.reservation.setConfirmed(false);
    this.reservations.add(this.reservation);
  }


  /*@Test
  public void addReservation() throws Exception {
    ReservationModel reservationModel = new ReservationModel();
    reservationModel.setDate("25-08-2022");
    reservationModel.setRoomId(room.getId());
    reservationModel.setCustomerId(customer.getId());
    reservationModel.setConfirmed(false);

    when(customerRepository.findById(reservation.getCustomer().getId())).thenReturn(
      java.util.Optional.ofNullable(customer));

    when(roomRepository.findById(reservation.getRoom().getId())).thenReturn(java.util.Optional.ofNullable(room));

    RequestBuilder request = MockMvcRequestBuilders.post("/reservations/add")
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(reservationModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }*/




  @Test
  public void changeConfirmationStatus_realReservationId_statusOk() throws Exception {
    when(this.reservationRepository.findById(this.reservation.getId())).thenReturn(java.util.Optional.of(this.reservation));

    boolean currentStatus = this.reservation.isConfirmed();
    this.reservation.setConfirmed(!currentStatus);

    RequestBuilder request = MockMvcRequestBuilders.put("/reservations/{id}", String.valueOf(this.reservation.getId()))
      .contentType(MediaType.APPLICATION_JSON)
      .content(this.objectMapper.writeValueAsString(this.reservation));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void changeConfirmationStatus_fakeReservationId_reservationNotFound() throws Exception {
    when(this.reservationRepository.findById(this.reservation.getId())).thenThrow(new EntityNotFoundException());

    boolean currentStatus = this.reservation.isConfirmed();
    this.reservation.setConfirmed(!currentStatus);

    RequestBuilder request = MockMvcRequestBuilders.put("/reservations/{id}", String.valueOf(this.reservation.getId()));

    this.mvc.perform(request)
      .andExpect(status().isNotFound());
  }

  @Test
  public void deleteReservation_realReservationId_statusOk() throws Exception {
    when(this.reservationRepository.findById(this.reservation.getId())).thenReturn(java.util.Optional.of(this.reservation));

    RequestBuilder request = MockMvcRequestBuilders.delete("/reservations/{id}", String.valueOf(this.reservation.getId()));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void deleteReservation_fakeReservationId_reservationNotFound() throws Exception {
    when(this.reservationRepository.findById(this.reservation.getId())).thenReturn(java.util.Optional.of(this.reservation));

    long fakeReservationId = 7L;
    RequestBuilder request = MockMvcRequestBuilders.delete("/reservations/{id}", String.valueOf(fakeReservationId));

    this.mvc.perform(request)
      .andExpect(status().isNotFound());
  }




  //TODO: JSON
  @Test
  public void getReservations_statusOk() throws Exception {
    List<Reservation> reservations = new ArrayList<>();
    reservations.add(this.reservation);

    when(this.reservationRepository.findAll()).thenReturn(reservations);

    String jsonRoom = this.objectMapper.writeValueAsString(this.room);
    String jsonCustomer = this.objectMapper.writeValueAsString(this.customer);

    RequestBuilder request = MockMvcRequestBuilders.get("/reservations/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(this.reservation.getId()))
      .andExpect(jsonPath("[0].date").value(this.reservation.getDate().toString()))
      .andExpect(jsonPath("[0].room").value(jsonRoom))
      .andExpect(jsonPath("[0].customer").value(jsonCustomer));
  }





  /*@Test
  public void deleteCustomerReservations() throws Exception {
    final long customerId = 8L;

    when(reservationRepository.findAll()).thenReturn(this.reservations);

    RequestBuilder request = MockMvcRequestBuilders.delete("/customer/{id}", customerId);

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }
*/



  private java.util.Date convertToDate() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String dateToParse = "25-08-2022";
    return formatter.parse(dateToParse);
  }
}
