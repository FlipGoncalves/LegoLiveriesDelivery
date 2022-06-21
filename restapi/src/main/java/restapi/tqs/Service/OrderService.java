package restapi.tqs.Service;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.DataModels.MakeOrderDTO;
import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.DataModels.OrderLegoDTO;
import restapi.tqs.Exceptions.AddressNotFoundException;
import restapi.tqs.Exceptions.BadOrderLegoDTOException;
import restapi.tqs.Exceptions.BadOrderLegoListException;
import restapi.tqs.Exceptions.BadScheduledTimeOfDeliveryException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Exceptions.InvalidStatusException;
import restapi.tqs.Exceptions.LegoNotFoundException;
import restapi.tqs.Exceptions.OrderNotCreatedException;
import restapi.tqs.Exceptions.OrderNotFoundException;
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

    private String engineURL = "http://engine:9001/api/order";

    public List<Order> getAllOrders(){
        log.info("Getting all orders");

        List<Order> orders = orderRepository.findAll();
        
        return orders;
    }

    public Order getOrderById(long orderId) throws OrderNotFoundException{
        log.info("Getting order by id {}", orderId);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isEmpty()){
            log.info("The order was not found. ID: {}", orderId);
            throw new OrderNotFoundException("The order was not found. ID: " + orderId);
        }

        log.info("Returning Order");
        return order.get();
    }

    public List<Order> getClientOrders(long clientId) throws ClientNotFoundException{
        log.info("Getting orders of client with clientId " + clientId);

        Optional<Client> client = clientRepository.findById(clientId);

        if (client.isEmpty()){
            log.info("The client was not found. ID: {}", clientId);
            throw new ClientNotFoundException("The client was not found. ID: " + clientId);
        }

        //The Pageable here can be used later on for filtering and sorting by date, name, etc.
        List<Order> orders = orderRepository.findAllByClient(client.get(), Pageable.unpaged());

        log.info("Returning Orders");
        return orders;
    }
    
    @Transactional(rollbackFor = {BadScheduledTimeOfDeliveryException.class, ClientNotFoundException.class, AddressNotFoundException.class, LegoNotFoundException.class, BadOrderLegoDTOException.class, BadOrderLegoListException.class, OrderNotCreatedException.class})
    public Order makeOrder(OrderDTO orderDTO) throws BadScheduledTimeOfDeliveryException, ClientNotFoundException, AddressNotFoundException, LegoNotFoundException, BadOrderLegoDTOException, BadOrderLegoListException, OrderNotCreatedException{
        log.info("Making new order");

        Order order = new Order();

        order = orderRepository.saveAndFlush(order);

        if (orderDTO.getScheduledTimeOfDelivery() >= 2400 || orderDTO.getScheduledTimeOfDelivery() < 0){
            log.info("TimeException");
            throw new BadScheduledTimeOfDeliveryException("The ScheduledTimeOfDelivery " + orderDTO.getScheduledTimeOfDelivery() + ". It needs to be between 0000 and 2400");
        }
        order.setScheduledTimeOfDelivery(orderDTO.getScheduledTimeOfDelivery());

        Optional<Client> client = clientRepository.findById(orderDTO.getClientId());
        
        if (client.isEmpty()){
            log.info("ClientException");
            throw new ClientNotFoundException("The client with id " + orderDTO.getClientId() + " was not found.");
        }

        order.setClient(client.get());
        client.get().getOrders().add(order);

        Optional<Address> addressOptional = addressRepository.findByLatitudeAndLongitude(orderDTO.getAddress().getLatitude(), orderDTO.getAddress().getLongitude());
        Address address = new Address();

        if (addressOptional.isPresent()){
            address = addressOptional.get();
        } else{
            address.convertDTOtoObject(orderDTO.getAddress());
            address = addressRepository.saveAndFlush(address);
        }

        order.setAddress(address);
        address.getOrders().add(order);

        try{
            orderDTO.getLegos();
        }catch(NullPointerException e){
            log.info("OrderLegoList1");
            throw new BadOrderLegoListException("The orderDTO has a null list of orderLegoDTO: " + orderDTO);
        }

        if (orderDTO.getLegos().isEmpty()){
            log.info("OrderLegoList2");
            throw new BadOrderLegoListException("The orderDTO has an empty list of orderLegoDTO: " + orderDTO);
        }

        double totalPrice = 0;

        for (OrderLegoDTO orderLegoDTO : orderDTO.getLegos()) {
            Optional<Lego> lego = legoRepository.findById(orderLegoDTO.getLegoId());

            if(lego.isEmpty()){
                log.info("Lego");
                throw new LegoNotFoundException("The lego with id " + orderLegoDTO.getLegoId() + " was not found.");
            }

            if (orderLegoDTO.getQuantity() <= 0 || orderLegoDTO.getLegoPrice() <= 0){
                throw new BadOrderLegoDTOException("The orderDTo is invalid: " + orderLegoDTO.toString());
            }

            OrderLego orderLego = new OrderLego();
            orderLego.setId(new OrderLegoId(order.getOrderId(), lego.get().getLegoId()));
            orderLego.setLego(lego.get());
            orderLego.setOrder(order);
            orderLego.setQuantity(orderLegoDTO.getQuantity());
            orderLego.setPrice(orderLegoDTO.getLegoPrice());
            orderLegoRepository.saveAndFlush(orderLego);

            order.getOrderLego().add(orderLego);
            lego.get().getOrderLego().add(orderLego);

            totalPrice += orderLegoDTO.getQuantity() * orderLegoDTO.getLegoPrice();
        }

        order.setTotalPrice(totalPrice);

        log.info("BEFORE HTTP CALL");

        AddressDTO addressDTO = orderDTO.getAddress();

        MakeOrderDTO makeOrderDTO = new MakeOrderDTO(client.get().getUser().getUsername(), orderDTO.getScheduledTimeOfDelivery(), "Legoliveries", addressDTO);

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
            .uri(this.engineURL)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(makeOrderDTO))
            .retrieve()
            .onStatus(
                status -> status.value() == 400,
                clientResponse -> Mono.empty()
            )
            .toEntity(String.class)
            .block();

        log.info("AFTER CALL");

        if (responseSpec == null){
            throw new OrderNotCreatedException("The order was not created in the engine: " + orderDTO);
        }
        
        if (responseSpec.getStatusCode().value() != 201){
            log.info("Order NOT CREATED1");

            throw new OrderNotCreatedException("The order was not created in the engine: " + orderDTO);
        }
        
        String response = responseSpec.getBody();
        log.info(response);
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode map = objectMapper.readTree(response);
            order.setExternalOrderId(map.get("orderId").asLong());
        } catch (JsonProcessingException e) {
            throw new OrderNotCreatedException("The order was not created in the engine: " + orderDTO);
        }
        
        order.setDate(new Date());
        
        return order;
    }

    public Order updateOrderStatus(long externalOrderId, int status) throws InvalidStatusException, OrderNotFoundException{

        if (status < 1 || status > 2){
            throw new InvalidStatusException("The status " + status + " is invalid");
        }

        Optional<Order> order = orderRepository.findByExternalOrderId(externalOrderId);

        if (order.isEmpty()){
            throw new OrderNotFoundException("Order with externalOrderId " + externalOrderId + " was not found");
        }

        Order orderUpdated = order.get();

        orderUpdated.setOrderStatus(status);

        return orderUpdated;
    }

    public void setEngineURL(String url){
        this.engineURL = url;
    }

    public String getEngineURL(){
        return this.engineURL;
    }
}