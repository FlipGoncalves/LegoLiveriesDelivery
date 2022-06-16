package restapi.tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.UserAlreadyExistsException;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.UserRepository;

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

    public User register(RegisterDTO user) throws UserAlreadyExistsException {
        log.info("Registering User: {}", user);

        User registerUser = new User();

        if (rep.findByEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists: " + user.toString());
        }

        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setUsername(user.getUsername());

        registerUser = rep.save(registerUser);
        log.info("User Registered: {}", registerUser);

        return registerUser;
    }
}
