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
import restapi.tqs.Models.Order;
import restapi.tqs.Service.OrderService;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@CrossOrigin
@RequestMapping("/order")
public class OrderController {
    
    private static final Logger log = LoggerFactory.getLogger(LegoController.class);

    @Autowired
    private OrderService service;

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long orderId){

        Order order = service.getOrderById(orderId);

        if(order == null){
            return new ResponseEntity<>(order, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/client/{clientId}")
    public ResponseEntity<List<Order>> getClientOrders(@PathVariable long clientId){

        List<Order> order = service.getClientOrders(clientId);

        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Order> makeOrder(@RequestBody OrderDTO orderDTO){
        
        Order order = service.makeOrder(orderDTO);

        if(order == null){
            return new ResponseEntity<>(order, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }

}
