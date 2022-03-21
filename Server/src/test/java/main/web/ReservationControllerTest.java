package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.*;
import main.repository.ReservationRepository;
import org.junit.Before;
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

  @Autowired
  ObjectMapper objectMapper;

  private Room room;
  private Customer customer;
  private final long reservationId = 4L;
  private final long fakeReservationId = 7L;
  private Reservation reservation;

  @BeforeEach
  public void setUp() throws Exception {
    this.room = new Room("TestName", "TestDescription", 123L);
    this.customer = new Customer("TestName", "444", new User("TestUserName", "Password", "USER"));
    this.reservation = new Reservation(new java.sql.Date(convertToDate().getTime()), room, customer);;
  }

  @Test
  public void changeConfirmationStatus_realReservationId_statusOk() throws Exception {
    reservation.setId(reservationId);
    reservation.setConfirmed(false);

    when(reservationRepository.findById(reservationId)).thenReturn(java.util.Optional.of(reservation));

    boolean currentStatus = reservation.isConfirmed();
    reservation.setConfirmed(!currentStatus);

    RequestBuilder request = MockMvcRequestBuilders.put("/reservations/{id}", String.valueOf(reservationId))
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(reservation));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  /*@Test
  public void changeConfirmationStatus_fakeReservationId_reservationNotFound() throws Exception {
    final long reservationId = 4L;
    final long fakeReservationId = 7L;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    java.util.Date date = formatter.parse("25-08-2022");
    java.sql.Date dateInMs = new java.sql.Date(date.getTime());

    Room room = new Room("TestName", "TestDescription", 123L);
    Customer customer = new Customer("TestName", "444", new User("TestUserName", "Password", "USER"));
    Reservation reservation = new Reservation(dateInMs, room, customer);
    reservation.setId(reservationId);
    reservation.setConfirmed(false);

    when(reservationRepository.findById(reservationId)).thenReturn(java.util.Optional.of(reservation));

    boolean currentStatus = reservation.isConfirmed();
    reservation.setConfirmed(!currentStatus);

    RequestBuilder request = MockMvcRequestBuilders.put("/reservations/{id}", String.valueOf(fakeReservationId));

    this.mvc.perform(request)
      .andExpect(status().isNotFound());
  }*/


  @Test
  public void deleteReservation_realReservationId_statusOk() throws Exception {
    reservation.setId(reservationId);

    when(reservationRepository.findById(reservationId)).thenReturn(java.util.Optional.of(reservation));

    RequestBuilder request = MockMvcRequestBuilders.delete("/reservations/{id}", String.valueOf(reservationId));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }



  @Test
  public void deleteReservation_fakeReservationId_reservationNotFound() throws Exception {
    reservation.setId(reservationId);

    when(reservationRepository.findById(reservationId)).thenReturn(java.util.Optional.of(reservation));

    RequestBuilder request = MockMvcRequestBuilders.delete("/reservations/{id}", String.valueOf(fakeReservationId));

    this.mvc.perform(request)
      .andExpect(status().isNotFound());
  }



  @Test
  public void getReservations_statusOk() throws Exception {
    List<Reservation> reservations = new ArrayList<>();
    reservations.add(reservation);
    reservations.get(0).setId(reservationId);

    when(reservationRepository.findAll()).thenReturn(reservations);

    RequestBuilder request = MockMvcRequestBuilders.get("/reservations/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(reservationId))
      //.andExpect(jsonPath("[0].date").value(reservation.getDate()))
      .andExpect(jsonPath("[0].room.name").value(room.getName()))
      .andExpect(jsonPath("[0].customer.name").value(customer.getName()));
  }

  private java.util.Date convertToDate() throws ParseException {
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");

    String dateToParse = "25-08-2022";
    return formatter.parse(dateToParse);
  }
}
