package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Manager;
import tqs.project.model.User;
import tqs.project.repositories.ManagerRepository;
import tqs.project.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ManagerService {    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ManagerRepository rep;

    public Manager getUser(String email) {
        log.info("Getting User with email: {}", email);

        Manager user = rep.findByUserEmail(email);

        log.info("Got User: {}", user);
        return user;
    }

    public Manager register(RegisterDTO user) throws UserAlreadyExistsException {
        log.info("Registering User: {}", user);

        Manager registerManager = new Manager();

        if (rep.findByUserEmail(user.getEmail()) != null) {
            throw new UserAlreadyExistsException("User already exists: " + user.toString());
        }

        User registerUser = new User();

        registerUser.setEmail(user.getEmail());
        registerUser.setPassword(user.getPassword());
        registerUser.setUsername(user.getUsername());

        registerManager.setUser(registerUser);
        registerManager = rep.save(registerManager);
        log.info("User Registered: {}", registerManager);

        return registerManager;
    }
}
