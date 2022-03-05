package com.example.room.model.gateways;

import android.os.StrictMode;

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
import com.example.room.model.RoomsInstrument;
import com.example.room.model.Token;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Gateway {

    private final BackendService service;

    public Gateway() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.15.101:8080")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(BackendService.class);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);
    }

    public Token getToken(String userName, String password) {
        Call<Token> token = service.signIn(new Credentials(userName, password));
        try {
            Response<Token> response = token.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reservation> getReservations(String token, int userId) {
        Call<List<Reservation>> reservations = service.getCustomerReservations(token, userId);

        try {
            Response<List<Reservation>> response = reservations.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Instrument> getRoomsInstrument(String token, int roomId) {
        Call<List<Instrument>> roomsInstrument = service.getRoomsInstrument(token, roomId);

        try {
            Response<List<Instrument>> response = roomsInstrument.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateCustomer(String token, int customerId, String name, String phone) {
        Call<Void> customer = service.updateCustomer(token, customerId, new Customer(customerId, name, phone));

        try {
            customer.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Customer getCustomer(String token, int userId) {
        Call<Customer> customer = service.getCustomer(token, userId);

        try {
            Response<Customer> response = customer.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<Reservation> getCustomerReservation(String token, int customerId) {
        Call<List<Reservation>> reservations = service.getCustomerReservation(token, customerId);

        try {
            Response<List<Reservation>> response = reservations.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteCustomerReservations(String token, int customerId) {
        Call<Void> reservations = service.deleteCustomerReservations(token, customerId);

        try {
            reservations.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteReservation(String token, int customerId) {
        Call<Void> reservation = service.deleteReservation(token, customerId);

        try {
            reservation.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Room> getAllRooms(String token) {
        Call<List<Room>> rooms = service.getAllRooms(token);

        try {
            Response<List<Room>> response = rooms.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addReservation(String token, String date, int roomId, int customerId) {
        Call<Void> reservation = service.addReservation(token, new ReservationCreate(date, roomId, customerId));
        try {
            reservation.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addCustomer(String token, String login, String password, String name, String phone) {
        Call<Void> registration = service.addCustomer(token, new Registration(login, password, name, phone));
        try {
            registration.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addRoom(String token, String name, String description, Long price) {
        Call<Void> room = service.addRoom(token, new AddRoomModel(name, description, price));
        try {
            room.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addInstrument(String token, String name, String description) {
        Call<Void> instrument = service.addInstrument(token, new AddInstrumentModel(name, description));
        try {
            instrument.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Instrument> getAllInstruments(String token) {
        Call<List<Instrument>> instruments = service.getAllInstruments(token);

        try {
            Response<List<Instrument>> response = instruments.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void addRoomsInstrument(String token, Long roomId, Long instrumentId) {
        Call<Void> roomsInstrument = service.addRoomsInstrument(token, new AddRoomsInstrumentModel(roomId, instrumentId));
        try {
            roomsInstrument.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Customer> getAllCustomers(String token) {
        Call<List<Customer>> customers = service.getAllCustomers(token);

        try {
            Response<List<Customer>> response = customers.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteRoom(String token, int roomId) {
        Call<Void> room = service.deleteRoom(token, roomId);

        try {
            room.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteInstrument(String token, int roomId) {
        Call<Void> instrument = service.deleteInstrument(token, roomId);

        try {
            instrument.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void confirmReservation(String token, int reservationId) {
        Call<Void> reservation = service.confirmReservation(token, reservationId);

        try {
            reservation.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Reservation> getAllReservations(String token) {
        Call<List<Reservation>> reservations = service.getAllReservations(token);

        try {
            Response<List<Reservation>> response = reservations.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<RoomsInstrument> getAllRoomsInstrument(String token) {
        Call<List<RoomsInstrument>> roomsInstrument = service.getAllRoomsInstrument(token);

        try {
            Response<List<RoomsInstrument>> response = roomsInstrument.execute();

            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void deleteRoomsInstrument(String token, int roomId) {
        Call<Void> roomsInstrument = service.deleteRoomsInstrument(token, roomId);

        try {
            roomsInstrument.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
