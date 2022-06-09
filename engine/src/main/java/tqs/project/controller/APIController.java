package tqs.project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import tqs.project.datamodels.RegisterDTO;
import tqs.project.datamodels.StatisticDTO;
import tqs.project.exceptions.BadRequestException;
import tqs.project.model.User;
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
    
    @GetMapping("/riders")
    public ResponseEntity<Map<String, Object>> getData() {
        log.info("GET Request -> All Riders Data");

        List<Rider> riders = riderservice.getAllData();

        Map<String, Object> mapper = new HashMap<>();
        mapper.put("riders", riders);

        return new ResponseEntity<>(mapper, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<Map<String, Object>> getAllOrders() {
        log.info("GET Request -> All Orders Data");

        List<Order> orders = orderservice.getAllOrders();
        Order o1 = new Order(1, "Filipe", new Date(), 1, 0);
        Order o2 = new Order(2, "Goncas", new Date(), 1, 0);
        o1.setStore(new Store(1, "Loja 1", new BigDecimal(1), new BigDecimal(1)));
        o1.setAddress(new Address("Street 1", "postalCode 1", "City 1", "Country 1"));
        orders.add(o1);
        o2.setStore(new Store(2, "Loja 2", new BigDecimal(2), new BigDecimal(2)));
        o2.setAddress(new Address("Street 2", "postalCode 2", "City 2", "Country 2"));
        orders.add(o2);

        Map<String, Object> mapper = new HashMap<>();
        mapper.put("orders", orders);

        return new ResponseEntity<>(mapper, HttpStatus.OK);
    }

    @GetMapping("/login/{email}")
    public ResponseEntity<Object> getUserinLogin(@PathVariable String email) throws BadRequestException {
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
    public ResponseEntity<Object> insertUserinRegister(@RequestBody Map<String, Object> user) throws BadRequestException {
        log.info("POST Request -> User Data for register: {}", user);

        RegisterDTO reg = new RegisterDTO((String) user.get("username"), (String) user.get("email"), (String) user.get("password"));

        User userReg = userservice.register(reg);

        if (userReg == null) {
            log.info("Register -> User already exists");
            throw new BadRequestException("User already exists");
        }

        log.info("Register -> User registered");
        return new ResponseEntity<>(userReg, HttpStatus.OK);
    }

    @GetMapping("/statistics")
    public ResponseEntity<Object> getStatistics() {
        log.info("GET Request -> Statistic data");

        StatisticDTO stats = new StatisticDTO();

        stats.setNumorders(orderservice.getAllOrders().size());
        stats.setNumriders(riderservice.getAllData().size());
        stats.setCompletedorders(2);
        stats.setSales(50);

        return new ResponseEntity<>(stats, HttpStatus.OK);
    }

    @PostMapping("/addrider")
    public ResponseEntity<Object> addRider(@RequestBody Map<String, Object> rider) {
        log.info("POST Request -> Rider data: {}", rider);

        Rider riderSaved = riderservice.insertRider(rider);

        return new ResponseEntity<>(riderSaved, HttpStatus.OK);
    }
}