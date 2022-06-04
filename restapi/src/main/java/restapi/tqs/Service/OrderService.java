package restapi.tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.DataModels.OrderLegoDTO;
import restapi.tqs.Exceptions.AddressNotFoundException;
import restapi.tqs.Exceptions.BadScheduledTimeOfDeliveryException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Exceptions.LegoNotFoundException;
import restapi.tqs.Models.Address;
import restapi.tqs.Models.Client;
import restapi.tqs.Models.Lego;
import restapi.tqs.Models.Order;
import restapi.tqs.Models.OrderLego;
import restapi.tqs.Models.OrderLegoId;
import restapi.tqs.Repositories.AddressRepository;
import restapi.tqs.Repositories.ClientRepository;
import restapi.tqs.Repositories.LegoRepository;
import restapi.tqs.Repositories.OrderLegoRepository;
import restapi.tqs.Repositories.OrderRepository;
import restapi.tqs.Repositories.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    LegoRepository legoRepository;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    OrderLegoRepository orderLegoRepository;

    public List<Order> getAllOrders(){

        List<Order> orders = orderRepository.findAll();
        
        return orders;
    }

    public Order getOrderById(long orderId){
        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isEmpty()){
            return null;
        }

        return order.get();
    }

    public List<Order> getClientOrders(long clientId){

        Optional<Client> client = clientRepository.findById(clientId);

        if (client.isEmpty()){
            return new ArrayList<>();
        }

        //The Pageable here can be used later on for filtering and sorting by date, name, etc.
        List<Order> orders = orderRepository.findAllByClient(client.get(), Pageable.unpaged());

        return orders;
    }
    
    public Order makeOrder(OrderDTO orderDTO) throws BadScheduledTimeOfDeliveryException, ClientNotFoundException, AddressNotFoundException, LegoNotFoundException{

        Order order = new Order();

        if (orderDTO.getScheduledtimeOfDelivery() >= 2400 || orderDTO.getScheduledtimeOfDelivery() < 0){
            throw new BadScheduledTimeOfDeliveryException("The ScheduledTimeOfDelivery " + orderDTO.getScheduledtimeOfDelivery() + ". It needs to be between 0000 and 2400");
        }
        order.setScheduledtimeOfDelivery(orderDTO.getScheduledtimeOfDelivery());

        Optional<Client> client = clientRepository.findById(orderDTO.getClientId());
        
        if (client.isEmpty()){
            throw new ClientNotFoundException("The client with id " + orderDTO.getClientId() + " was not found.");
        }

        order.setClient(client.get());

        Optional<Address> address = addressRepository.findById(orderDTO.getAddressId());

        if (address.isEmpty()){
            throw new AddressNotFoundException("The address with id " + orderDTO.getAddressId() + " was not found.");
        }

        order.setAddress(address.get());

        double totalPrice = 0;

        Map<Long,Lego> orderLegoMap = new HashMap<>();

        for (OrderLegoDTO legoDTO : orderDTO.getLegos()) {
            Optional<Lego> lego = legoRepository.findById(legoDTO.getLegoId());

            if(lego.isEmpty()){
                throw new LegoNotFoundException("The lego with id " + legoDTO.getLegoId() + " was not found.");
            }
            orderLegoMap.put(legoDTO.getLegoId(), lego.get());
            totalPrice += legoDTO.getQuantity() * legoDTO.getLegoPrice();
        }

        order.setTotalPrice(totalPrice);

        Set<OrderLego> setOrderLegos = new HashSet<>();

        for (OrderLegoDTO legoDTO : orderDTO.getLegos()) {

            Lego lego = orderLegoMap.get(legoDTO.getLegoId());

            OrderLego orderLego = new OrderLego();
            orderLego.setId(new OrderLegoId(order.getOrderId(), lego.getLegoId()));
            orderLego.setLego(lego);
            orderLego.setOrder(order);
            orderLego.setQuantity(legoDTO.getQuantity());
            orderLego.setPrice(legoDTO.getLegoPrice());
            setOrderLegos.add(orderLego);
            orderLegoRepository.save(orderLego);
        }

        order.setOrderLego(setOrderLegos);

        Order finalOrder = orderRepository.save(order);

        return finalOrder;
    }
}
