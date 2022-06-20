package restapi.tqs.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.Exceptions.AddressNotFoundException;
import restapi.tqs.Exceptions.BadOrderLegoListException;
import restapi.tqs.Exceptions.BadOrderLegoDTOException;
import restapi.tqs.Exceptions.BadScheduledTimeOfDeliveryException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Exceptions.LegoNotFoundException;
import restapi.tqs.Exceptions.OrderNotCreatedException;
import restapi.tqs.Exceptions.OrderNotFoundException;
import restapi.tqs.Models.Order;
import restapi.tqs.Service.OrderService;

import java.util.List;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/orders")
public class OrderController {
    
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderService service;

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders(){
        log.info("Getting all orders");

        List<Order> orders = service.getAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Order> getOrderById(@PathVariable long orderId){
        log.info("Getting order by id " + orderId);

        Order order;
        try {
            order = service.getOrderById(orderId);
        } catch (OrderNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> getClientOrders(@PathVariable long clientId){
        log.info("Getting orders of client with clientId " + clientId);

        List<Order> order;
        try {
            order = service.getClientOrders(clientId);
        } catch (ClientNotFoundException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Order> makeOrder(@RequestBody OrderDTO orderDTO){
        log.info("Making new order");

        Order order = null;
        try {
            order = service.makeOrder(orderDTO);
        } catch (BadScheduledTimeOfDeliveryException | ClientNotFoundException | AddressNotFoundException
                | LegoNotFoundException | BadOrderLegoDTOException | BadOrderLegoListException | OrderNotCreatedException e) {
            log.info("Error Creating Order");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        
        log.info("Order Created");
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
}
