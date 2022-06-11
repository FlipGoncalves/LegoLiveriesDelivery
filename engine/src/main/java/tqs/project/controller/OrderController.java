package tqs.project.controller;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Store;
import tqs.project.service.OrderService;

@RestController
@RequestMapping("/api/order")
@Validated
@CrossOrigin
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private OrderService orderservice;

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("GET Request -> All Orders Data");

        List<Order> orders = orderservice.getAllOrders();
        Order o1 = new Order(1, "Filipe", new Date(), 1, 0);
        Order o2 = new Order(2, "Goncas", new Date(), 1, 0);
        o1.setStore(new Store(1, "Loja 1"));
        o1.setAddress(new Address("Street 1", "postalCode 1", "City 1", "Country 1", new BigDecimal(1), new BigDecimal(1)));
        orders.add(o1);
        o2.setStore(new Store(2, "Loja 2"));
        o2.setAddress(new Address("Street 2", "postalCode 2", "City 2", "Country 2", new BigDecimal(2), new BigDecimal(2)));
        orders.add(o2);

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Order> insertOrder(){
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
