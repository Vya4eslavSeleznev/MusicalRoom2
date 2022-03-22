package main.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import main.model.RegisterModel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  void register_statusOk() throws Exception {
    RegisterModel registerModel = new RegisterModel("login", "password", "1234", "name");

    RequestBuilder request = MockMvcRequestBuilders.post("/auth/register")
      .contentType(MediaType.APPLICATION_JSON)
      .content(this.objectMapper.writeValueAsString(registerModel));

    this.mvc.perform(request)
      .andExpect(status().isOk());
  }
}
