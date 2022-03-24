package main.web;

import main.entity.Customer;
import main.entity.User;
import main.model.RegisterModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import main.repository.UserRepository;
import main.service.CustomerService;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserRepository userRepository;

    CustomerService customerService;

    @Autowired
    PasswordEncoder pwdEncoder;

    @PostMapping()
    public void register(@RequestBody RegisterModel registerModel) {
        try {
            User user = new User(
                    registerModel.getUserName(),
                    pwdEncoder.encode(registerModel.getPassword()) ,
                    "USER");
            userRepository.save(user);
            customerService.add(new Customer(registerModel.getName(), registerModel.getPhone(), user));
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exist");
        }
    }
}
