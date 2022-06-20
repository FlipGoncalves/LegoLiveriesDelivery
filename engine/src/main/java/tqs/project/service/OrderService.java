package tqs.project.service;

import java.time.Duration;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import tqs.project.datamodels.OrderDTO;
import tqs.project.exceptions.InvalidStatusException;
import tqs.project.exceptions.OrderNotUpdatedException;
import tqs.project.exceptions.OrderNotFoundException;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Store;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.StoreRepository;

@Service
public class OrderService {    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private OrderRepository orderRep;

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private AddressRepository addressRep;

    private String engineURL = "legoliveries:8080/order";


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

    @Transactional(rollbackFor = {InvalidStatusException.class, OrderNotFoundException.class})
    public Order updateOrderStatus(long orderId, int orderStatus) throws InvalidStatusException, OrderNotFoundException, OrderNotUpdatedException{

        if (orderStatus != 1 || orderStatus != 2){
            throw new InvalidStatusException("The status " + orderStatus + " is invalid");
        }

        Optional<Order> order = orderRep.findByExternalOrderId(orderId);

        if (order.isEmpty()){
            throw new OrderNotFoundException("Order with externalOrderId " + orderId + " was not found");
        }

        Order orderUpdated = order.get();

        orderUpdated.setStatus(orderStatus);

        HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 10000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn -> 
            conn.addHandlerLast(new ReadTimeoutHandler(101000, TimeUnit.MILLISECONDS))
            .addHandlerLast(new WriteTimeoutHandler(10000, TimeUnit.MILLISECONDS)));

        WebClient webClient = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();

        ResponseEntity<String> responseSpec = webClient.post()
            .uri(this.engineURL + "/" + orderId + "/" + orderStatus)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(
                status -> status.value() == 400,
                clientResponse -> Mono.empty()
            )
            .toEntity(String.class)
            .block();

        if (responseSpec == null){
            throw new OrderNotUpdatedException("The order was not updated in the engine: ");
        }
        
        if (responseSpec.getStatusCode().value() != 200){

            throw new OrderNotUpdatedException("The order was not updated in the engine: ");
        }

        return orderUpdated;
    }

    public String getEngineURL() {
        return this.engineURL;
    }

    public void setEngineURL(String engineURL) {
        this.engineURL = engineURL;
    }

}
