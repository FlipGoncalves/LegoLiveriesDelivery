package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.ManagerAlreadyExistsException;
import tqs.project.exceptions.ManagerNotFoundException;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Manager;
import tqs.project.model.User;
import tqs.project.repository.ManagerRepository;
import tqs.project.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ManagerService {    
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Manager> getAllManagers(){
        log.info("Getting All Managers");

        return managerRepository.findAll();
    }

    public Manager login(String email) throws ManagerNotFoundException {
        log.info("Getting Manager with email: {}", email);

        Optional<Manager> manager = managerRepository.findByUserEmail(email);

        if (manager.isEmpty()){
            throw new ManagerNotFoundException("Manager with email " + email + " was not found");
        }

        return manager.get();
    }

    public Manager register(RegisterDTO dto) throws ManagerAlreadyExistsException {
        log.info("Registering User: {}", dto);

        Manager manager = new Manager();

        if (managerRepository.findByUserEmail(dto.getEmail()).isPresent()) {
            throw new ManagerAlreadyExistsException("Manager already exists: " + dto.toString());
        }

        User user = createOrGetUser(dto);

        manager.setUser(user);

        manager = managerRepository.saveAndFlush(manager);

        user.setManager(manager);

        return manager;
    }

    public User createOrGetUser(RegisterDTO dto){
        Optional<User> userOptional = userRepository.findByEmail(dto.getEmail());
        
        User user = new User();
        
        if (userOptional.isEmpty()) {
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user = userRepository.saveAndFlush(user);
        } else{
            user = userOptional.get();
        }

        return user;
    }
}
