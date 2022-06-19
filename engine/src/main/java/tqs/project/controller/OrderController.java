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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.datamodels.OrderDTO;
import tqs.project.model.Order;
import tqs.project.service.OrderService;

@RestController
@RequestMapping("/api/orders")
@Validated
@CrossOrigin
public class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService orderService;

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        log.info("GET Request -> All Orders Data");

        List<Order> orders = orderService.getAllOrders();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> makeOrder(@RequestBody OrderDTO orderDTO){

        long orderId = -1;
        try {
            orderId = orderService.makeOrder(orderDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        String response = "{\"orderId\" : " + orderId + " }";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
