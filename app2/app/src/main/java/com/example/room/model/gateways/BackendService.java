package com.example.room.model.gateways;

import com.example.room.model.AddInstrumentModel;
import com.example.room.model.AddRoomModel;
import com.example.room.model.AddRoomsInstrumentModel;
import com.example.room.model.Credentials;
import com.example.room.model.Customer;
import com.example.room.model.Instrument;
import com.example.room.model.Registration;
import com.example.room.model.Reservation;
import com.example.room.model.ReservationCreate;
import com.example.room.model.Room;
import com.example.room.model.Token;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface BackendService {
    @POST("/auth/signin")
    Call<Token> signIn(@Body Credentials credentials);

    @GET("/reservations/customer/{id}")
    Call<List<Reservation>> getCustomerReservations(@Header("Authorization") String token, @Path("id") int id);

    @GET("/rooms/{id}/instruments")
    Call<List<Instrument>> getRoomsInstrument(@Header("Authorization") String token, @Path("id") int id);

    @POST("/customers/{id}")
    Call<Void> updateCustomer(@Header("Authorization") String token, @Path("id") int id, @Body Customer customer);

    @GET("/customers/{id}")
    Call<Customer> getCustomer(@Header("Authorization") String token, @Path("id") int id);

    @GET("/reservations/customer/{id}")
    Call<List<Reservation>> getCustomerReservation(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("/reservations/customer/{id}")
    Call<Void> deleteCustomerReservations(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("/reservations/{id}")
    Call<Void> deleteReservation(@Header("Authorization") String token, @Path("id") int id);

    @GET("/rooms/all")
    Call<List<Room>> getAllRooms(@Header("Authorization") String token);

    @POST("/reservations/add")
    Call<Void> addReservation(@Header("Authorization") String token, @Body ReservationCreate reservationCreate);

    @POST("/auth/register")
    Call<Void> addCustomer(@Header("Authorization") String token, @Body Registration Registration);

    @POST("/data/room")
    Call<Void> addRoom(@Header("Authorization") String token, @Body AddRoomModel room);

    @POST("/data/instrument")
    Call<Void> addInstrument(@Header("Authorization") String token, @Body AddInstrumentModel instrument);

    @GET("/data/all")
    Call<List<Instrument>> getAllInstruments(@Header("Authorization") String token);

    @POST("/data/room_instrument")
    Call<Void> addRoomsInstrument(@Header("Authorization") String token, @Body AddRoomsInstrumentModel roomsInstrument);

    @GET("/customers/all")
    Call<List<Customer>> getAllCustomers(@Header("Authorization") String token);

    @DELETE("/data/room/{id}")
    Call<Void> deleteRoom(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("/data/instrument/{id}")
    Call<Void> deleteInstrument(@Header("Authorization") String token, @Path("id") int id);

    @PUT("/reservations/{id}")
    Call<Void> confirmReservation(@Header("Authorization") String token, @Path("id") int id);

    @GET("/reservations/all")
    Call<List<Reservation>> getAllReservations(@Header("Authorization") String token);
}
