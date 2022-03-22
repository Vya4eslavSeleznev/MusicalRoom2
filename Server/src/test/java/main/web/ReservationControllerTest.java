package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.Customer;
import main.entity.Reservation;
import main.entity.Room;
import main.entity.User;
import main.exception.EntityNotFoundException;
import main.repository.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
    Convert convert = new Convert();
    this.reservation = new Reservation(new java.sql.Date(convert.convertToDate("25-08-2022").getTime()), room, customer);
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

  @Test
  public void getReservations_statusOk() throws Exception {
    List<Reservation> reservations = new ArrayList<>();
    reservations.add(this.reservation);

    when(this.reservationRepository.findAll()).thenReturn(reservations);

    //String jsonRoom = this.objectMapper.writeValueAsString(this.room);
    //String jsonCustomer = this.objectMapper.writeValueAsString(this.customer);

    RequestBuilder request = MockMvcRequestBuilders.get("/reservations/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(this.reservation.getId()))
      .andExpect(jsonPath("[0].date").value(this.reservation.getDate().toString()))
      .andExpect(jsonPath("[0].room.name").value(this.reservation.getRoom().getName()))
      .andExpect(jsonPath("[0].room.description").value(this.reservation.getRoom().getDescription()))
      .andExpect(jsonPath("[0].room.price").value(this.reservation.getRoom().getPrice()))
      .andExpect(jsonPath("[0].customer.name").value(this.reservation.getCustomer().getName()))
      .andExpect(jsonPath("[0].customer.phone").value(this.reservation.getCustomer().getPhone()))
      .andExpect(jsonPath("[0].customer.userId").value(this.reservation.getCustomer().getUserId()));
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
}
