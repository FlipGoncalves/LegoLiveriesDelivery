package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.User;
import tqs.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserService {    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        log.info("Getting All Users");

        return userRepository.findAll();
    }

    public User login(String email) throws UserNotFoundException {
        log.info("Getting User with email: {}", email);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            throw new UserNotFoundException("User with email " + email + " was not found");
        }

        log.info("Got User: {}", user);
        return user.get();
    }

    public User register(RegisterDTO user) throws UserAlreadyExistsException {
        log.info("Registering User: {}", user);

        User registerUser = new User();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User already exists: " + user.toString());
        }

        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setUsername(user.getUsername());

        userRepository.saveAndFlush(registerUser);
        log.info("User Registered: {}", registerUser);

        return registerUser;
    }
}
