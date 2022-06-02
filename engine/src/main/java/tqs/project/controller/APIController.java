package tqs.project.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.exceptions.BadRequestException;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.model.UserDTO;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;
import tqs.project.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
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

    @GetMapping("/login")
    public ResponseEntity<User> getUserinLogin(@RequestParam(value = "email", required = true) String email) throws BadRequestException {
        log.info("GET Request -> User Data for login: {}", email);

        User user = userservice.getUser(email);

        if (user == null) {
            throw new BadRequestException("ERROR getting User data in login");
        }

        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<User> insertUserinRegister(@RequestBody UserDTO user) throws BadRequestException {
        log.info("PUT Request -> User Data for login: {}", user);

        User userReg = userservice.register(user);

        if (userReg == null) {
            throw new BadRequestException("ERROR getting User data in login");
        }

        return new ResponseEntity<>(userReg, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrders() throws BadRequestException {
        log.info("GET Request -> All Orders Data");

        List<Order> orders = orderservice.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}