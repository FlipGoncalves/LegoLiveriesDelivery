package restapi.tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.UserAlreadyExistsException;
import restapi.tqs.Exceptions.UserNotFoundException;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.UserRepository;

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
        log.info("Getting all Users");

        return userRepository.findAll();
    }

    public User login(String email) throws UserNotFoundException {
        log.info("Getting User with email: {}", email);

        Optional<User> user = userRepository.findByEmail(email);

        if (user.isEmpty()){
            log.info("User with email {} was not found", email);
            throw new UserNotFoundException("User with email " + email + " was not found");
        }

        log.info("User found: {}", user);
        return user.get();
    }

    public User register(RegisterDTO user) throws UserAlreadyExistsException {
        log.info("Registering User: {}", user);

        User registerUser = new User();

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            log.info("User already exists: {}", user.toString());
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
