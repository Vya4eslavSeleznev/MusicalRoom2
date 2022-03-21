package main;

import main.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import main.repository.*;

import java.sql.Date;

@Component
public class TestDataInit implements CommandLineRunner {
    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    InstrumentRepository instrumentRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    RoomInstrumentRepository roomInstrumentRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder pwdEncoder;

    @Override
    public void run(String[] args) throws Exception {
        Instrument instrument1 = new Instrument("instrument_name1", "description1");
        Instrument instrument2 = new Instrument("instrument_name2", "description2");
        Instrument instrument3 = new Instrument("instrument_name3", "description3");
        Instrument instrument4 = new Instrument("instrument_name4", "description4");
        Instrument instrument5 = new Instrument("instrument_name5", "description5");

        Room room1 = new Room("room1", "description1", 101L);
        Room room2 = new Room("room2", "description2", 102L);
        Room room3 = new Room("room3", "description3", 103L);
        Room room4 = new Room("room4", "description4", 104L);
        Room room5 = new Room("room5", "description5", 105L);

        roomRepository.save(room1);
        roomRepository.save(room2);
        roomRepository.save(room3);
        roomRepository.save(room4);
        roomRepository.save(room5);

        instrumentRepository.save(instrument1);
        instrumentRepository.save(instrument2);
        instrumentRepository.save(instrument3);
        instrumentRepository.save(instrument4);
        instrumentRepository.save(instrument5);

        roomInstrumentRepository.save(new RoomInstrument(room1, instrument1));
        roomInstrumentRepository.save(new RoomInstrument(room2, instrument2));
        roomInstrumentRepository.save(new RoomInstrument(room3, instrument3));
        roomInstrumentRepository.save(new RoomInstrument(room4, instrument4));
        roomInstrumentRepository.save(new RoomInstrument(room5, instrument5));

        User admin = new User("admin", pwdEncoder.encode("password"), "ADMIN");
        userRepository.save(admin);

        User customerUser = new User("customer", pwdEncoder.encode("password"), "USER");
        userRepository.save(customerUser);

        Customer customer = new Customer("name", "+79643423523", customerUser);
        customerRepository.save(customer);

        /*Reservation reservation1 = new Reservation(Date.valueOf("2021-12-10"), room1, customer);
        Reservation reservation2 = new Reservation(Date.valueOf("2021-12-11"), room2, customer);
        Reservation reservation3 = new Reservation(Date.valueOf("2021-12-12"), room3, customer);
        Reservation reservation4 = new Reservation(Date.valueOf("2021-12-13"), room4, customer);
        Reservation reservation5 = new Reservation(Date.valueOf("2021-12-14"), room5, customer);
        Reservation reservation6 = new Reservation(Date.valueOf("2021-12-15"), room1, customer);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);
        reservationRepository.save(reservation3);
        reservationRepository.save(reservation4);
        reservationRepository.save(reservation5);
        reservationRepository.save(reservation6);*/
    }
}
