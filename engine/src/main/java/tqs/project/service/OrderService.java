package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.model.Order;
import tqs.project.repositories.OrderRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository rep;

    public List<Order> getAllOrders() {
        log.info("Getting All Orders Data");

        List<Order> orders = rep.findAll();

        return orders;
    }

    public List<Order> getAllOrdersByStatus(int status){
        log.info("Getting All Orders By Status " + status);

        List<Order> orders = rep.findByStatus(status);

        return orders;
    }

    public List<Order> getAllOrdersByStoreIdAndStatus(long storeId, int status){
        log.info("Getting All Orders By Status " + status + " and StoreId " + storeId);

        List<Order> orders = rep.findByStoreStoreIdAndStatus(storeId, status);

        return orders;
    }

    public List<Order> getAllOrdersByStoreId(long storeId){
        log.info("Getting All Orders By StoreId " + storeId);

        List<Order> orders = rep.findByStoreStoreId(storeId);

        return orders;
    }
}
