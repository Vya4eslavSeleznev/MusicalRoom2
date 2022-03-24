package main.web;

import main.entity.Customer;
import main.entity.Room;
import main.exception.EntityNotFoundException;
import main.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import main.service.CustomerService;
import main.service.RoomInstrumentService;

import javax.persistence.NoResultException;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    private CustomerService customerService;

    @PostMapping(value = "/{id}", consumes = "application/json")
    public void updateCustomer(@PathVariable("id") long id, @RequestBody CustomerModel customer) {
        try {
            customerService.update(id, customer);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/user")
    public ResponseEntity<Long> getUserId(@PathVariable("id") Long id) {
        try {
            Long userId = customerService.getById(id).getUser().getId();
            return new ResponseEntity<>(userId, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getCustomer(@PathVariable("id") Long id) {
        try {
            Customer customer = customerService.getByUserId(id);
            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (EntityNotFoundException | NoResultException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAll();
        return new ResponseEntity<>(customers, HttpStatus.OK);
    }

    @Autowired
    void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }
}
