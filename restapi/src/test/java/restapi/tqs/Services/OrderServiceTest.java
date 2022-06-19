package restapi.tqs.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.DataModels.OrderLegoDTO;
import restapi.tqs.Exceptions.AddressNotFoundException;
import restapi.tqs.Exceptions.BadOrderLegoListException;
import restapi.tqs.Exceptions.BadOrderLegoDTOException;
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
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.AddressRepository;
import restapi.tqs.Repositories.ClientRepository;
import restapi.tqs.Repositories.LegoRepository;
import restapi.tqs.Repositories.OrderLegoRepository;
import restapi.tqs.Repositories.OrderRepository;
import restapi.tqs.Service.OrderService;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {
    
    @Mock(lenient = true)
    private LegoRepository legoRepository;

    @Mock(lenient = true)
    private OrderRepository orderRepository;

    @Mock(lenient = true)
    private OrderLegoRepository orderLegoRepository;

    @Mock(lenient = true)
    private AddressRepository addressRepository;

    @Mock(lenient = true)
    private ClientRepository clientRepository;

    @InjectMocks
    private OrderService service;

    public static MockWebServer mockBackEnd;

    Order order1, order2, order3;
    Client client1, client2;
    Address address1, address2;
    User user1, user2;
    OrderDTO orderDTO1, orderDTO2, orderDTO3;
    List<OrderLego> orderLegos1, orderLegos2, orderLegos3;
    Lego lego1, lego2, lego3;
    List<OrderLegoDTO> orderLegoDTO1, orderLegoDTO2, orderLegoDTO3;
    AddressDTO addressDTO1, addressDTO2, addressDTO3;

    @BeforeAll
    static void allSetUp() throws IOException{
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @BeforeEach
    void setUp(){
        
        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        service.setEngineURL(baseUrl);

        List<Object> array1 = buildUserAndClientObject(1);
        user1 = (User) array1.get(0);
        client1 = (Client) array1.get(1);

        List<Object> array2 = buildUserAndClientObject(2);
        user2 = (User) array2.get(0);
        client2 = (Client) array2.get(1);

        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);

        lego1 = buildLegoObject(1);
        lego2 = buildLegoObject(2);
        lego3 = buildLegoObject(3);

        orderLegos1 = buildOrderLegoList(lego1, lego2, lego3,1);
        orderLegos2 = buildOrderLegoList(lego1, lego2, lego3,2);
        orderLegos3 = buildOrderLegoList(lego1, lego2, lego3,3);

        order1 = buildAndSaveOrderObject(client1, address1, orderLegos1, 1);
        order2 = buildAndSaveOrderObject(client1, address1, orderLegos2, 1);
        order3 = buildAndSaveOrderObject(client2, address2, orderLegos3, 2);

        Set<Order> orders = client1.getOrders();
        orders.add(order1);
        client1.setOrders(orders);

        orders = client1.getOrders();
        orders.add(order2);
        client1.setOrders(orders);

        orders = client2.getOrders();
        orders.add(order3);
        client2.setOrders(orders);

        Mockito.when(orderRepository.findAll()).thenReturn(new ArrayList<Order>(Arrays.asList(order1,order2,order3)));

        Mockito.when(orderRepository.findById(1l)).thenReturn(Optional.of(order1));
        Mockito.when(orderRepository.findById(2l)).thenReturn(Optional.of(order2));
        Mockito.when(orderRepository.findById(3l)).thenReturn(Optional.of(order3));

        Mockito.when(clientRepository.findById(1l)).thenReturn(Optional.of(client1));
        Mockito.when(clientRepository.findById(2l)).thenReturn(Optional.of(client2));

        Mockito.when(addressRepository.findById(1l)).thenReturn(Optional.of(address1));
        Mockito.when(addressRepository.findById(2l)).thenReturn(Optional.of(address2));

        Mockito.when(legoRepository.findById(1l)).thenReturn(Optional.of(lego1));
        Mockito.when(legoRepository.findById(2l)).thenReturn(Optional.of(lego2));
        Mockito.when(legoRepository.findById(3l)).thenReturn(Optional.of(lego3));
        
        ArrayList<Order> array3 = new ArrayList<>();
        array3.add(order1);
        array3.add(order2);

        ArrayList<Order> array4 = new ArrayList<>();
        array4.add(order3);

        Mockito.when(orderRepository.findAllByClient(client1, Pageable.unpaged())).thenReturn(array3);
        Mockito.when(orderRepository.findAllByClient(client2, Pageable.unpaged())).thenReturn(array4);
    
        orderLegoDTO1 = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTO2 = buildOrderLegoDTO(1l,2l,3l,2);
        orderLegoDTO3 = buildOrderLegoDTO(1l,2l,3l,3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);

        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO1.getLatitude(), addressDTO1.getLongitude())).thenReturn(Optional.of(address1));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO2.getLatitude(), addressDTO2.getLongitude())).thenReturn(Optional.of(address2));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO3.getLatitude(), addressDTO3.getLongitude())).thenReturn(Optional.empty());

        orderDTO1 = new OrderDTO(1l, addressDTO1, 2100, orderLegoDTO1);
        orderDTO2 = new OrderDTO(1l, addressDTO1, 1500, orderLegoDTO2);
        orderDTO3 = new OrderDTO(2l, addressDTO2, 2000, orderLegoDTO3);
    }

    @AfterAll
    static void cleanUp() throws IOException{
        mockBackEnd.shutdown();
    }

    @Test
    void test_GetAllOrder_ReturnsCorrectOrders(){
        List<Order> result = service.getAllOrders();
        assertTrue(!result.isEmpty());
        assertEquals(3, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
        assertTrue(result.contains(order3));

    }

    @Test
    void test_GetClientOrderWithValidOrderId_ReturnsCorrectOrders() throws OrderNotFoundException{
        Order result = service.getOrderById(1l);
        assertTrue(result != null);
        assertEquals(order1, result);
    }

    @Test
    void test_GetClientOrderWithInvalidOrderId_ReturnsNull(){
        
        Mockito.when(orderRepository.findById(4l)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {service.getOrderById(4l);});
    }

    @Test
    void test_GetClientOrdersValidClientId_ReturnsCorrectOrders() throws ClientNotFoundException{
        List<Order> result = service.getClientOrders(1l);
        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
        assertTrue(!result.contains(order3));
    }

    @Test
    void test_GetClientOrdersInvalidClientId_ReturnsEmptyArray(){

        Mockito.when(clientRepository.findById(4l)).thenReturn(Optional.empty());

        assertThrows(ClientNotFoundException.class, () -> {service.getClientOrders(4l);});
    }

    @Test
    void test_MakeOrder_InvalidScheduledTimeofDelivery_ReturnsBadScheduledTimeofDeliveryException(){
        OrderDTO orderDTOTest1 = new OrderDTO(1l, addressDTO1, 2500, orderLegoDTO1);
        OrderDTO orderDTOTest2 = new OrderDTO(1l, addressDTO1, -2000, orderLegoDTO1);
        OrderDTO orderDTOTest3 = new OrderDTO(1l, addressDTO1, 2400, orderLegoDTO1);
        OrderDTO orderDTOTest4 = new OrderDTO(1l, addressDTO1, -1, orderLegoDTO1);


        assertThrows(BadScheduledTimeOfDeliveryException.class, () -> {service.makeOrder(orderDTOTest1);});
        assertThrows(BadScheduledTimeOfDeliveryException.class, () -> {service.makeOrder(orderDTOTest2);});
        assertThrows(BadScheduledTimeOfDeliveryException.class, () -> {service.makeOrder(orderDTOTest3);});
        assertThrows(BadScheduledTimeOfDeliveryException.class, () -> {service.makeOrder(orderDTOTest4);});

    }

    @Test
    void test_MakeOrder_ClientNotFound_ThrowsClientNotFoundException(){
        Mockito.when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());

        OrderDTO orderDTOTest1 = new OrderDTO(50l, addressDTO1, 2000, orderLegoDTO1);

        assertThrows(ClientNotFoundException.class, () -> {service.makeOrder(orderDTOTest1);});
    }

    @Test
    void test_MakeOrder_LegoNotFound_ThrowsLegoNotFoundException(){
        Mockito.when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());

        OrderDTO orderDTOTest1 = new OrderDTO(1l, addressDTO1, 2000, buildOrderLegoDTO(1l,2l,5l,1));

        assertThrows(LegoNotFoundException.class, () -> {service.makeOrder(orderDTOTest1);});
    }

    @Test
    void test_MakeOrder_EmptyListOfOrderLegoDTO_ThrowsBadOrderDTOException(){
        Mockito.when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());

        OrderDTO orderDTOTest1 = new OrderDTO(1l, addressDTO1, 2000, new ArrayList<>());

        assertThrows(BadOrderLegoListException.class, () -> {service.makeOrder(orderDTOTest1);});
    }

    @Test
    void test_MakeOrder_InvalidOrderLegoDTO_ThrowsBadOrderLegoDTOException(){
        Mockito.when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());

        List<OrderLegoDTO> orderLegoDTOs = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTOs.get(0).setQuantity(-3);

        OrderDTO orderDTOTest1 = new OrderDTO(1l, addressDTO1, 2000, orderLegoDTOs);

        assertThrows(BadOrderLegoDTOException.class, () -> {service.makeOrder(orderDTOTest1);});
    }

    @Test
    void test_MakeOrder_AllValid_ReturnsCorrectOrder() throws BadScheduledTimeOfDeliveryException, ClientNotFoundException, AddressNotFoundException, LegoNotFoundException, BadOrderLegoDTOException, BadOrderLegoListException, OrderNotCreatedException, InterruptedException, JsonProcessingException{
        
        Mockito.when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());

        String responseFromEngine = "{\"orderId\" : " + 1 + " }";

        mockBackEnd.enqueue(new MockResponse().setBody(responseFromEngine).setResponseCode(201));

        Order order = service.makeOrder(orderDTO1);

        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(order);
        System.out.println("depois " + json);

        RecordedRequest recordedRequest = mockBackEnd.takeRequest();

        System.out.println("RecordedREquest: " + recordedRequest);
        assertTrue(order != null);
        assertEquals(orderDTO1.getScheduledTimeOfDelivery(), order.getScheduledTimeOfDelivery());
        assertEquals(address1, order.getAddress());
        assertEquals(client1, order.getClient());
        assertEquals(orderLegos1.size(), order.getOrderLego().size());
        assertEquals(1, order.getExternalOrderId());
    }

    Order buildAndSaveOrderObject(Client client, Address address, List<OrderLego> orderLegos, long id){

        Order order = new Order();
        order.setOrderId(id);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 6, (int) id);
        Date date = calendar.getTime();

        double totalPrice = 0;

        for (OrderLego orderLego : orderLegos) {
            totalPrice += orderLego.getPrice() * orderLego.getQuantity();
        }

        order.setClient(client);
        order.setAddress(address);
        order.setDate(date);
        order.setScheduledTimeOfDelivery(2100);
        order.setRiderName("Paulo " + id);
        order.setOrderLego(new HashSet<>(orderLegos));
        order.setTotalPrice(totalPrice);

        return order;
    }

    Lego buildLegoObject(long id){
        Lego lego =  new Lego();
        lego.setLegoId(id);
        lego.setName("Lego " + id);
        lego.setImageUrl("URL " + id);
        lego.setPrice(10 + id);
        return lego;
    }
    
    ArrayList<Object> buildUserAndClientObject(long id){
        User user = new User();
        Client client = new Client();
        user.setUsername("Person " + id);
        user.setClient(client);
        client.setUser(user);
        user.setUserId(id);
        client.setClientId(id);
        ArrayList<Object> array = new ArrayList<>();
        array.add(user);
        array.add(client);
        return array;
    }

    Address buildAddressObject(long id){
        Address address = new Address();
        address.setAddressId(id);
        address.setLongitude(100 + id);
        address.setLatitude(50 + id);
        address.setStreet("Street " + id);
        address.setPostalCode("3810-24" + id);
        address.setCity("city " + id);
        address.setCountry("Country " + id);
        return address;
    }

    AddressDTO buildAddressDTO(long id){
        AddressDTO address = new AddressDTO();
        address.setLongitude(100 + id);
        address.setLatitude(50 + id);
        address.setStreet("Street " + id);
        address.setPostalCode("3810-24" + id);
        address.setCity("city " + id);
        address.setCountry("Country " + id);
        return address;
    }

    List<OrderLego> buildOrderLegoList(Lego lego1, Lego lego2, Lego lego3, long id){

        OrderLego orderLego1 = new OrderLego();
        orderLego1.setId(new OrderLegoId(id, lego1.getLegoId()));
        orderLego1.setLego(lego1);
        orderLego1.setPrice(lego1.getPrice());
        orderLego1.setQuantity(1 + (int) id);
        OrderLego orderLego2 = new OrderLego();
        orderLego2.setId(new OrderLegoId(id, lego2.getLegoId()));
        orderLego2.setLego(lego2);
        orderLego2.setPrice(lego2.getPrice());
        orderLego2.setQuantity(2 + (int) id);
        OrderLego orderLego3 = new OrderLego();
        orderLego3.setId(new OrderLegoId(id, lego3.getLegoId()));
        orderLego3.setLego(lego3);
        orderLego3.setPrice(lego3.getPrice());
        orderLego3.setQuantity(3 + (int) id);

        List<OrderLego> orderLegos = new ArrayList<>();
        orderLegos.add(orderLego1);
        orderLegos.add(orderLego2);
        orderLegos.add(orderLego3);

        return orderLegos;
    }

    List<OrderLegoDTO> buildOrderLegoDTO(long legoId1, long legoId2, long legoId3, long id){
        OrderLegoDTO orderLego1 = new OrderLegoDTO(legoId1, 1 + (int) id, 10 + (double) id);
        OrderLegoDTO orderLego2 = new OrderLegoDTO(legoId2, 2 + (int) id, 20 + (double) id);
        OrderLegoDTO orderLego3 = new OrderLegoDTO(legoId3, 3 + (int) id, 30 + (double) id);

        List<OrderLegoDTO> orderLegos = new ArrayList<>();
        orderLegos.add(orderLego1);
        orderLegos.add(orderLego2);
        orderLegos.add(orderLego3);

        return orderLegos;
    }
}