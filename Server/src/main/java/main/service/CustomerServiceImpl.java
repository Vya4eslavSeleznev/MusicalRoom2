package main.service;

import main.entity.Customer;
import main.entity.Room;
import main.entity.User;
import main.exception.EntityNotFoundException;
import main.model.CustomerModel;
import org.springframework.beans.factory.annotation.Autowired;
import main.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerRepository customerRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void delete(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new EntityNotFoundException("Customer not found");
        }
        customerRepository.delete(customer.get());
    }

    @Override
    public void update(long id, CustomerModel customer) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (!customerOptional.isPresent()) {
            throw new EntityNotFoundException("Customer not found");
        }
        Customer customerOld = customerOptional.get();
        customerOld.setName(customer.getName());
        customerOld.setPhone(customer.getPhone());
        customerRepository.save(customerOld);
    }

    @Override
    public Customer getById(long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (!customer.isPresent()) {
            throw new EntityNotFoundException("Customer not found");
        }
        return customer.get();
    }

    @Override
    public Customer getByUserId(long id) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Customer> query = cb.createQuery(Customer.class);

        Root<Customer> customer = query.from(Customer.class);

        query.select(customer);

        Path<User> userId = customer.get("userId");

        query.where(cb.equal(userId, id));

        Customer result = entityManager.createQuery(query).getSingleResult();

        return result;
    }

    @Override
    public List<Customer> getAll() {
        return (List<Customer>) customerRepository.findAll();
    }
}
