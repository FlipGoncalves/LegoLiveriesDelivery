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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.datamodels.OrderDTO;
import tqs.project.exceptions.InvalidStatusException;
import tqs.project.exceptions.OrderNotFoundException;
import tqs.project.exceptions.OrderNotUpdatedException;
import tqs.project.exceptions.StoreNotFoundException;
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
    public ResponseEntity<Object> makeOrder(@RequestBody OrderDTO orderDTO){

        Order order;
        try {
            order = orderService.makeOrder(orderDTO);
            order = orderService.setRider(order.getOrderId());
        } catch (StoreNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String response = "{\"orderId\" : " + order.getOrderId() + " }";
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/{orderId}/{status}")
    public ResponseEntity<Order> updateOrderStatus(@PathVariable long orderId, @PathVariable int status){
        log.info("Updating order status");

        try {
            orderService.updateOrderStatus(orderId, status);
        } catch (InvalidStatusException | OrderNotFoundException | OrderNotUpdatedException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }


        return new ResponseEntity<>(HttpStatus.OK);
    }
}
