package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tqs.project.datamodels.OrderDTO;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.RiderRepository;
import tqs.project.repository.StoreRepository;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderService {    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private SecureRandom rand = new SecureRandom();

    @Autowired
    private OrderRepository orderRep;

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private AddressRepository addressRep;

    @Autowired
    private RiderRepository riderRep;

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

    @Transactional(rollbackFor = {StoreNotFoundException.class})
    public Order makeOrder(OrderDTO orderDTO) throws StoreNotFoundException{

        Order order = new Order();

        order = orderRep.saveAndFlush(order);

        Optional<Store> store = storeRep.findByName(orderDTO.getStoreName());

        if (store.isEmpty()){
            throw new StoreNotFoundException("Store with name " + orderDTO.getStoreName() + " not found");
        }

        order.setStore(store.get());
        store.get().getOrders().add(order);

        Address address = new Address();

        Optional<Address> addressOptional = addressRep.findByLatitudeAndLongitude(orderDTO.getAddress().getLatitude(), orderDTO.getAddress().getLongitude());

        if (addressOptional.isPresent()){
            address = addressOptional.get();
        } else{
            address.convertDTOtoObject(orderDTO.getAddress());
            address = addressRep.saveAndFlush(address);
        }

        order.setAddress(address);
        address.getOrders().add(order);

        order.setClientName(orderDTO.getClientName());
        
        order.setStatus(0);

        order.setTimeOfDelivery(orderDTO.getTimeOfDelivery());

        return order;
    }

    public Order setRider(Long orderId) {

        Order order = orderRep.getById(orderId);

        // set rider
        Rider rider = new Rider();

        List<Rider> riders = riderRep.findAll();

        int max = 0;
        Rider max_rider = null;
        for (Rider rid: riders) {
            int numBillings = rand.nextInt(16);
            int billing = 0;
            for (int i = 0; i < numBillings; i++) {
                billing += rand.nextInt(5) + 1;
            }
            if (billing > max) {
                max_rider = rid;
                max = billing;
            }
        }

        order.setRider(max_rider);
        rider.addOrder(order);

        return order;
    }
}
