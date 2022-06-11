package tqs.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.model.Order;
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

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}
