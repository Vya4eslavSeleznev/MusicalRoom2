package main.service;

import main.entity.Reservation;

import java.util.List;

public interface ReservationService {
    void add(Reservation customer);

    void delete(long id);

    void deleteAllByCustomer(long id);

    List<Reservation> getAllByCustomer(long id);

    List<Reservation> getAll();

    Reservation getById(long id);
}
