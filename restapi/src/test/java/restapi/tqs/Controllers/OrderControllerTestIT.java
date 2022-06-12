package restapi.tqs.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import restapi.tqs.TqsApplication;
import restapi.tqs.Controller.OrderController;
import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.DataModels.OrderLegoDTO;
import restapi.tqs.Exceptions.AddressNotFoundException;
import restapi.tqs.Exceptions.BadOrderLegoDTOException;
import restapi.tqs.Exceptions.BadOrderLegoListException;
import restapi.tqs.Exceptions.BadScheduledTimeOfDeliveryException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Exceptions.LegoNotFoundException;
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
import restapi.tqs.Repositories.UserRepository;
import restapi.tqs.Service.OrderService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TqsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class OrderControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    Order order1, order2, order3;
    Client client1, client2;
    Address address1, address2;
    User user1, user2;
    OrderDTO orderDTO1, orderDTO2, orderDTO3;
    Set<OrderLego> orderLegos1, orderLegos2, orderLegos3;
    Lego lego1, lego2, lego3;
    List<OrderLegoDTO> orderLegoDTO1, orderLegoDTO2, orderLegoDTO3;
    ArrayList<Order> all_Orders;

    @Autowired
    private LegoRepository legoRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderLegoRepository orderLegoRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    void setUp(){

        List<Object> array1 = buildUserAndClientObject(1);
        user1 = (User) array1.get(0);
        client1 = (Client) array1.get(1);

        List<Object> array2 = buildUserAndClientObject(2);
        user2 = (User) array2.get(0);
        client2 = (Client) array2.get(1);

        client1 = clientRepository.saveAndFlush(client1);
        client2 = clientRepository.saveAndFlush(client2);
        user1 = userRepository.saveAndFlush(user1);
        user2 = userRepository.saveAndFlush(user2);

        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);

        address1 = addressRepository.saveAndFlush(address1);
        address2 = addressRepository.saveAndFlush(address2);

        lego1 = buildLegoObject(1);
        lego2 = buildLegoObject(2);
        lego3 = buildLegoObject(3);

        lego1 = legoRepository.saveAndFlush(lego1);
        lego2 = legoRepository.saveAndFlush(lego2);
        lego3 = legoRepository.saveAndFlush(lego3);

        orderLegos1 = buildOrderLegoList(lego1, lego2, lego3,1);
        orderLegos2 = buildOrderLegoList(lego1, lego2, lego3,2);
        orderLegos3 = buildOrderLegoList(lego1, lego2, lego3,3);

        order1 = buildAndSaveOrderObject(client1, address1, orderLegos1, 1);
        order2 = buildAndSaveOrderObject(client1, address1, orderLegos2, 2);
        order3 = buildAndSaveOrderObject(client2, address2, orderLegos3, 3);

        order1 = orderRepository.saveAndFlush(order1);
        order2 = orderRepository.saveAndFlush(order2);
        order3 = orderRepository.saveAndFlush(order3);

        Set<Order> orders = client1.getOrders();
        orders.add(order1);
        client1.setOrders(orders);
        
        /*System.out.println("TESTE1 " + orderRepository.count());
        System.out.println("TESTE1 " + orderRepository.findById(1l));
        System.out.println("TESTE1 " + orderRepository.findById(2l));
        System.out.println("TESTE1 " + orderRepository.findById(3l));*/

        orders = client1.getOrders();
        orders.add(order2);
        client1.setOrders(orders);
        
        /*System.out.println("TESTE2 " + orderRepository.count());
        System.out.println("TESTE2 " + orderRepository.findById(1l));
        System.out.println("TESTE2 " + orderRepository.findById(2l));
        System.out.println("TESTE2 " + orderRepository.findById(3l));*/

        orders = client2.getOrders();
        orders.add(order3);
        client2.setOrders(orders);
        
        /*System.out.println("TESTE3 " + orderRepository.count());
        System.out.println("TESTE3 " + orderRepository.findById(1l));
        System.out.println("TESTE3 " + orderRepository.findById(2l));
        System.out.println("TESTE3 " + orderRepository.findById(3l));*/

        System.out.println("TESTESTESTE");
        System.out.println(order1.getOrderId());
        System.out.println(order2.getOrderId());
        System.out.println(order3.getOrderId());
        System.out.println("TESTESTESTE");

        for (OrderLego orderLego : orderLegos1) {
            orderLego.setOrder(order1);
            orderLego.setId(new OrderLegoId(order1.getOrderId(), orderLego.getLego().getLegoId()));

            orderLego = orderLegoRepository.saveAndFlush(orderLego);
        }

        for (OrderLego orderLego : orderLegos2) {
            orderLego.setOrder(order2);
            orderLego.setId(new OrderLegoId(order2.getOrderId(), orderLego.getLego().getLegoId()));
            orderLego = orderLegoRepository.saveAndFlush(orderLego);
        }

        for (OrderLego orderLego : orderLegos3) {
            orderLego.setOrder(order3);
            orderLego.setId(new OrderLegoId(order3.getOrderId(), orderLego.getLego().getLegoId()));
            orderLego = orderLegoRepository.saveAndFlush(orderLego);
        }

        orderLegoDTO1 = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTO2 = buildOrderLegoDTO(1l,2l,3l,2);
        orderLegoDTO3 = buildOrderLegoDTO(1l,2l,3l,3);

        orderDTO1 = new OrderDTO(1l, 1l, 2100, orderLegoDTO1);
        orderDTO2 = new OrderDTO(1l, 1l, 1500, orderLegoDTO2);
        orderDTO3 = new OrderDTO(2l, 2l, 2000, orderLegoDTO3);

        System.out.println("TESTE " + orderRepository.count());
    }

    @AfterEach
    void cleanUp(){
        System.out.println("Count user before " + userRepository.count());
        System.out.println("Count client before " + clientRepository.count());
        System.out.println("Count lego before " + legoRepository.count());
        System.out.println("Count orderLego before " + orderLegoRepository.count());
        System.out.println("Count address before " + addressRepository.count());
        System.out.println("Count order before " + orderRepository.count());
        userRepository.deleteAll();
        clientRepository.deleteAll();
        legoRepository.deleteAll();
        orderLegoRepository.deleteAll();
        addressRepository.deleteAll();
        orderRepository.deleteAll();
        System.out.println("Count user after " + userRepository.count());
        System.out.println("Count client after " + clientRepository.count());
        System.out.println("Count lego after " + legoRepository.count());
        System.out.println("Count orderLego after " + orderLegoRepository.count());
        System.out.println("Count address after " + addressRepository.count());
        System.out.println("Count order after " + orderRepository.count());
    }

    @Test
    void test_GetAllOrders_ReturnsCorrectOrders() throws Exception{

        mvc.perform(get("/order")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$[0].timeOfDelivery", is(order1.getTimeOfDelivery())))
        .andExpect(jsonPath("$[0].scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[0].riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$[0].totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$[0].address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$[0].client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$[0].orderLego", hasSize(3)))
        .andExpect(jsonPath("$[1].orderId", is((int) order2.getOrderId())))
        .andExpect(jsonPath("$[1].timeOfDelivery", is(order2.getTimeOfDelivery())))
        .andExpect(jsonPath("$[1].scheduledTimeOfDelivery", is(order2.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[1].riderName", is(order2.getRiderName())))
        .andExpect(jsonPath("$[1].totalPrice", is(order2.getTotalPrice())))
        .andExpect(jsonPath("$[1].address", is((int) order2.getAddress().getAddressId())))
        .andExpect(jsonPath("$[1].client", is((int) order2.getClient().getClientId())))
        .andExpect(jsonPath("$[1].orderLego", hasSize(3)))
        .andExpect(jsonPath("$[2].orderId", is((int) order3.getOrderId())))
        .andExpect(jsonPath("$[2].timeOfDelivery", is(order3.getTimeOfDelivery())))
        .andExpect(jsonPath("$[2].scheduledTimeOfDelivery", is(order3.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[2].riderName", is(order3.getRiderName())))
        .andExpect(jsonPath("$[2].totalPrice", is(order3.getTotalPrice())))
        .andExpect(jsonPath("$[2].address", is((int) order3.getAddress().getAddressId())))
        .andExpect(jsonPath("$[2].client", is((int) order3.getClient().getClientId())))
        .andExpect(jsonPath("$[2].orderLego", hasSize(3)));

    }

    @Test
    void test_GetOrderById_ValidId_ReturnsCorrectOrder() throws Exception{

        //System.out.println("Object: " + orderRepository.findById(1l));
        //System.out.println("Order1: " + order1);

        mvc.perform(get("/order/{orderId}",1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$.timeOfDelivery", is(order1.getTimeOfDelivery())))
        .andExpect(jsonPath("$.scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$.riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$.totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$.address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$.client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$.orderLego", hasSize(3)));
    }

    @Test
    void test_GetOrderById_InvalidId_ReturnsBadRequestStatus() throws Exception{
        
        mvc.perform(get("/order/{orderId}",200)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

    @Test
    void test_GetClientOrders_ValidId_ReturnsCorrectOrders() throws Exception{

        mvc.perform(get("/order/client/{clientId}", 1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$[0].timeOfDelivery", is(order1.getTimeOfDelivery())))
        .andExpect(jsonPath("$[0].scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[0].riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$[0].totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$[0].address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$[0].client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$[0].orderLego", hasSize(3)))
        .andExpect(jsonPath("$[1].orderId", is((int) order2.getOrderId())))
        .andExpect(jsonPath("$[1].timeOfDelivery", is(order2.getTimeOfDelivery())))
        .andExpect(jsonPath("$[1].scheduledTimeOfDelivery", is(order2.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[1].riderName", is(order2.getRiderName())))
        .andExpect(jsonPath("$[1].totalPrice", is(order2.getTotalPrice())))
        .andExpect(jsonPath("$[1].address", is((int) order2.getAddress().getAddressId())))
        .andExpect(jsonPath("$[1].client", is((int) order2.getClient().getClientId())))
        .andExpect(jsonPath("$[1].orderLego", hasSize(3)));
    }

    @Test
    void test_GetClientOrders_InvalidId_ReturnsBadRequestStatus() throws Exception{
        
        mvc.perform(get("/order/client/{clientId}",200)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadScheduledTimeOfDelivery_ReturnsBadRequestStatus() throws Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2700, orderLegoDTOTest);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadClientId_ReturnsBadRequestStatus() throws Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(50l, 1l, 2100, orderLegoDTOTest);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadAddressId_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, 50l, 2100, orderLegoDTOTest);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadLegoId_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(50l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2100, orderLegoDTOTest);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadOrderLegoDTO_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTOTest.get(0).setQuantity(-5);
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2100, orderLegoDTOTest);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadOrderLegoList_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2100, new ArrayList<OrderLegoDTO>());

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_ValidOrderDTO_ReturnsCorrectOrder() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2100, orderLegoDTOTest);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$.timeOfDelivery", is(order1.getTimeOfDelivery())))
        .andExpect(jsonPath("$.scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$.riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$.totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$.address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$.client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$.orderLego", hasSize(3)));
    }

    Order buildAndSaveOrderObject(Client client, Address address, Set<OrderLego> orderLegos, long id){

        Order order = new Order();

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
        order.setTimeOfDelivery(2110);
        order.setRiderName("Paulo " + id);
        order.setTotalPrice(totalPrice);

        return order;
    }

    Lego buildLegoObject(long id){
        Lego lego =  new Lego();
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
        ArrayList<Object> array = new ArrayList<>();
        array.add(user);
        array.add(client);
        return array;
    }

    Address buildAddressObject(long id){
        Address address = new Address();
        address.setCountry("Country " + id);
        return address;
    }

    Set<OrderLego> buildOrderLegoList(Lego lego1, Lego lego2, Lego lego3, long id){

        OrderLego orderLego1 = new OrderLego();
        orderLego1.setLego(lego1);
        orderLego1.setPrice(lego1.getPrice());
        orderLego1.setQuantity(1 + (int) id);
        OrderLego orderLego2 = new OrderLego();
        orderLego2.setLego(lego2);
        orderLego2.setPrice(lego2.getPrice());
        orderLego2.setQuantity(2 + (int) id);
        OrderLego orderLego3 = new OrderLego();
        orderLego3.setLego(lego3);
        orderLego3.setPrice(lego3.getPrice());
        orderLego3.setQuantity(40 + (int) id);

        Set<OrderLego> orderLegos = new HashSet<>();
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
