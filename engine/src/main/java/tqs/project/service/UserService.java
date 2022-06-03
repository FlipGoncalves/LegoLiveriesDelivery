package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.model.User;
import tqs.project.model.RegisterDTO;
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

        log.info("Got User: {}", user);
        return user;
    }

    public User register(RegisterDTO user) {
        log.info("Registering User: {}", user);

        User registerUser = new User();

        if (rep.findByEmail(user.getEmail()) != null) {
            log.info("User already exists: {}", user);
            return null;
        }

        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setUsername(user.getUsername());

        rep.save(registerUser);
        log.info("User Registered: {}", registerUser);

        return registerUser;
    }
}
