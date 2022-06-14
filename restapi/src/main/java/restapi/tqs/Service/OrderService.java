package restapi.tqs.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
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

    @Autowired
    private ObjectMapper objectMapper;

    public List<Order> getAllOrders(){
        log.info("Getting all orders");

        List<Order> orders = orderRepository.findAll();
        
        return orders;
    }

    public Order getOrderById(long orderId) throws OrderNotFoundException{
        log.info("Getting order by id " + orderId);

        Optional<Order> order = orderRepository.findById(orderId);

        if (order.isEmpty()){
            throw new OrderNotFoundException("The order was not found. ID: " + orderId);
        }

        return order.get();
    }

    public List<Order> getClientOrders(long clientId) throws ClientNotFoundException{
        log.info("Getting orders of client with clientId " + clientId);

        Optional<Client> client = clientRepository.findById(clientId);

        if (client.isEmpty()){
            throw new ClientNotFoundException("The client was not found. ID: " + clientId);
        }

        //The Pageable here can be used later on for filtering and sorting by date, name, etc.
        List<Order> orders = orderRepository.findAllByClient(client.get(), Pageable.unpaged());

        return orders;
    }
    
    public Order makeOrder(OrderDTO orderDTO) throws BadScheduledTimeOfDeliveryException, ClientNotFoundException, AddressNotFoundException, LegoNotFoundException, BadOrderLegoDTOException, BadOrderLegoListException, OrderNotCreatedException{
        log.info("Making new order");

        Order order = new Order();

        if (orderDTO.getScheduledTimeOfDelivery() >= 2400 || orderDTO.getScheduledTimeOfDelivery() < 0){
            throw new BadScheduledTimeOfDeliveryException("The ScheduledTimeOfDelivery " + orderDTO.getScheduledTimeOfDelivery() + ". It needs to be between 0000 and 2400");
        }
        order.setScheduledTimeOfDelivery(orderDTO.getScheduledTimeOfDelivery());

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

        try{
            orderDTO.getLegos();
        }catch(NullPointerException e){
            throw new BadOrderLegoListException("The orderDTO has a null list of orderLegoDTO: " + orderDTO);
        }

        if (orderDTO.getLegos().isEmpty()){
            throw new BadOrderLegoListException("The orderDTO has an empty list of orderLegoDTO: " + orderDTO);
        }

        double totalPrice = 0;

        Map<Long,Lego> orderLegoMap = new HashMap<>();

        for (OrderLegoDTO orderLegoDTO : orderDTO.getLegos()) {
            Optional<Lego> lego = legoRepository.findById(orderLegoDTO.getLegoId());

            if(lego.isEmpty()){
                throw new LegoNotFoundException("The lego with id " + orderLegoDTO.getLegoId() + " was not found.");
            }

            if (orderLegoDTO.getQuantity() <= 0 || orderLegoDTO.getLegoPrice() <= 0){
                throw new BadOrderLegoDTOException("The orderDTo is invalid: " + orderLegoDTO.toString());
            }
            orderLegoMap.put(orderLegoDTO.getLegoId(), lego.get());
            totalPrice += orderLegoDTO.getQuantity() * orderLegoDTO.getLegoPrice();
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

        AddressDTO addressDTO = new AddressDTO(address.get().getStreet(), address.get().getPostalCode(), address.get().getCity(), address.get().getCountry(), address.get().getLatitude(), address.get().getLongitude());

        MakeOrderDTO makeOrderDTO = new MakeOrderDTO(client.get().getUser().getUsername(), orderDTO.getScheduledTimeOfDelivery(), "Legoliveries", addressDTO);

        HttpClient httpClient = HttpClient.create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .responseTimeout(Duration.ofMillis(5000))
        .doOnConnected(conn -> 
            conn.addHandlerLast(new ReadTimeoutHandler(5000, TimeUnit.MILLISECONDS))
            .addHandlerLast(new WriteTimeoutHandler(5000, TimeUnit.MILLISECONDS)));

        WebClient webClient = WebClient.builder()
        .clientConnector(new ReactorClientHttpConnector(httpClient))
        .build();

        ResponseEntity<String> responseSpec = webClient.post()
            .uri("http://localhost:9001/api/order")
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(makeOrderDTO)
            .retrieve()
            .onStatus(
                status -> status.value() == 400,
                clientResponse -> Mono.empty()
            )
            .toEntity(String.class)
            .block();

        if (responseSpec.getStatusCode().value() == 400){
            throw new OrderNotCreatedException("The order was not created in the engine: " + orderDTO);
        }
        
        String response = responseSpec.getBody();
        Map<String, Object> map = null;
        try {
            map = objectMapper.readValue(response, new TypeReference<Map<String,Object>>(){});
        } catch (JsonProcessingException e) {
            throw new OrderNotCreatedException("The order was not created in the engine: " + orderDTO);
        }

        order.setExternalOrderId((long) map.get("orderId"));

        Order finalOrder = orderRepository.save(order);

        return finalOrder;
    }
}