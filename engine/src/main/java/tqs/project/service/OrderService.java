package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.OrderDTO;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Store;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.StoreRepository;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRep;

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private AddressRepository addressRep;

    public List<Order> getAllOrders() {
        log.info("Getting All Orders Data");

        List<Order> orders = orderRep.findAll();

        return orders;
    }

    public List<Order> getAllOrdersByStatus(int status){
        log.info("Getting All Orders By Status " + status);

        List<Order> orders = orderRep.findByStatus(status);

        return orders;
    }

    public List<Order> getAllOrdersByStoreIdAndStatus(long storeId, int status){
        log.info("Getting All Orders By Status " + status + " and StoreId " + storeId);

        List<Order> orders = orderRep.findByStoreStoreIdAndStatus(storeId, status);

        return orders;
    }

    public List<Order> getAllOrdersByStoreId(long storeId){
        log.info("Getting All Orders By StoreId " + storeId);

        List<Order> orders = orderRep.findByStoreStoreId(storeId);

        return orders;
    }

    public Long makeOrder(OrderDTO orderDTO) throws StoreNotFoundException{

        Order order = new Order();

        Optional<Store> store = storeRep.findByName(orderDTO.getStoreName());

        if (store.isEmpty()){
            throw new StoreNotFoundException("Store with name " + orderDTO.getStoreName() + " not found");
        }

        order.setStore(store.get());
        Address address = new Address();

        Optional<Address> addressOptional = addressRep.findByLatitudeAndLongitude(orderDTO.getAddress().getLatitude(), orderDTO.getAddress().getLongitude());

        if (addressOptional.isPresent()){
            address = addressOptional.get();
        } else{
            address.convertDTOtoObject(orderDTO.getAddress());
            address = addressRep.saveAndFlush(address);
        }

        order.setAddress(address);

        order.setClientName(orderDTO.getClientName());

        order.setTimeOfDelivery(orderDTO.getTimeOfDelivery());

        order = orderRep.saveAndFlush(order);

        return order.getOrderId();
    }
}
