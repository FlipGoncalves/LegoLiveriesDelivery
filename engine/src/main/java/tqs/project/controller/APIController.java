package tqs.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tqs.project.model.Order;
import tqs.project.model.Rider;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import tqs.project.exceptions.BadRequestException;
import tqs.project.model.User;
import tqs.project.model.RegisterDTO;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;
import tqs.project.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin(origins = "*")
public class APIController {
    private static final Logger log = LoggerFactory.getLogger(APIController.class);

    @Autowired
    private UserService userservice;

    @Autowired
    private RiderService riderservice;

    @Autowired
    private OrderService orderservice;
    
    @GetMapping("/all_riders")
    public ResponseEntity<List<Rider>> getData() {
        log.info("GET Request -> All Riders Data");

        List<Rider> riders = riderservice.getAllData();

        return new ResponseEntity<>(riders, HttpStatus.OK);
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<User> getUserinLogin(@PathVariable String email) throws BadRequestException {
        log.info("GET Request -> User Data for login email: {}", email);

        User userLog = userservice.getUser(email);

        if (userLog == null) {
            log.info("Login -> User does not exist");
            throw new BadRequestException("User does not exist");
        }

        log.info("Login -> User logged in");
        return new ResponseEntity<>(userLog, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> insertUserinRegister(@RequestBody RegisterDTO user) throws BadRequestException {
        log.info("POST Request -> User Data for register: {}", user);

        User userReg = userservice.register(user);

        if (userReg == null) {
            log.info("Register -> User already exists");
            throw new BadRequestException("User already exists");
        }

        log.info("Register -> User registered");
        return new ResponseEntity<>(userReg, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("GET Request -> All Orders Data");

        List<Order> orders = orderservice.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}