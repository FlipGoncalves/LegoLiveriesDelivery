package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.model.User;
import tqs.project.model.UserDTO;
import tqs.project.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository rep;

    public User getUser(String email) {
        log.info("Getting User with email: {}", email);

        User user = rep.findByEmail(email);

        return user;
    }

    public User insertUser(User user) {
        log.info("Inserting User");

        return rep.save(user);
    }

    public User register(UserDTO user) {
        log.info("Registering User: {}", user);

        User registerUser = new User();

        if (rep.findByEmail(user.getEmail()) != null) {
            return null;
        }

        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setUsername(user.getUsername());

        return rep.save(registerUser);
    }
}
