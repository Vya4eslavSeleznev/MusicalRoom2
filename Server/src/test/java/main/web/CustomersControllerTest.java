package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.entity.Customer;
import main.entity.User;
import main.model.CustomerModel;
import main.repository.CustomerRepository;
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
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CustomersControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private CustomerRepository repository;

  @Autowired
  ObjectMapper objectMapper;

  private final long customerId = 18L;
  private final long userId = 17L;
  private Customer customer;
  private final long fakeCustomerId = 1L;

  @BeforeEach
  public void setUp() {
    User user = new User("TestUser", "1234", "USER");
    user.setId(userId);

    this.customer = new Customer("name", "+79643423523", user);
    this.customer.setId(customerId);
  }

  @Test
  public void updateCustomer_realId_statusOk() throws Exception {
    CustomerModel customerModel = new CustomerModel();
    customerModel.setName("UpdatedName");
    customerModel.setPhone("4444");
    customerModel.setUserId(userId);

    when(this.repository.findById(this.customerId)).thenReturn(Optional.of(this.customer));

    this.customer.setName(customerModel.getName());
    this.customer.setPhone(customerModel.getPhone());
    this.customer.setId(customerModel.getUserId());

    RequestBuilder request = MockMvcRequestBuilders.post("/customers/{id}", String.valueOf(this.customerId))
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(this.customer));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }

  @Test
  public void updateCustomer_fakeId_customerNotFound() throws Exception {
    when(this.repository.findById(this.customerId)).thenReturn(Optional.of(this.customer));

    RequestBuilder request = MockMvcRequestBuilders.post("/customers/{id}", String.valueOf(fakeCustomerId))
      .contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(this.customer));

    this.mvc.perform(request)
      .andExpect(status().isNotFound());
  }

  @Test
  public void getUserIdByCustomerId_realCustomerId_statusOk() throws Exception {
    when(this.repository.findById(this.customerId)).thenReturn(java.util.Optional.of(this.customer));

    RequestBuilder request = MockMvcRequestBuilders.get("/customers/{id}/user", String.valueOf(this.customerId));
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(content().string(containsString(String.valueOf(this.userId))));
  }

  @Test
  public void getUserIdByCustomerId_fakeCustomerId_userNotFound() throws Exception {
    when(this.repository.findById(this.customerId)).thenReturn(java.util.Optional.of(this.customer));

    RequestBuilder request = MockMvcRequestBuilders.get("/customers/{id}/user", String.valueOf(this.fakeCustomerId));
    this.mvc.perform(request)
      .andExpect(status().isNotFound());
  }

  @Test
  public void getAllCustomers_statusOk() throws Exception {
    List<Customer> customers = new ArrayList<>();
    customers.add(this.customer);

    when(this.repository.findAll()).thenReturn(customers);

    RequestBuilder request = MockMvcRequestBuilders.get("/customers/all");
    this.mvc.perform(request)
      .andExpect(status().isOk())
      .andExpect(jsonPath("[0].id").value(this.customer.getId()))
      .andExpect(jsonPath("[0].name").value(this.customer.getName()))
      .andExpect(jsonPath("[0].phone").value(this.customer.getPhone()));
  }

  @Test
  public void a() {

  }
}
