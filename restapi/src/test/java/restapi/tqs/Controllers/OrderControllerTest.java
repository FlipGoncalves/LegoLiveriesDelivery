package restapi.tqs.Controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import restapi.tqs.Controller.OrderController;
import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.DataModels.OrderLegoDTO;
import restapi.tqs.Exceptions.AddressNotFoundException;
import restapi.tqs.Exceptions.BadOrderLegoDTOException;
import restapi.tqs.Exceptions.BadScheduledTimeOfDeliveryException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Exceptions.LegoNotFoundException;
import restapi.tqs.Exceptions.OrderNotFoundException;
import restapi.tqs.Models.Address;
import restapi.tqs.Models.Client;
import restapi.tqs.Models.Lego;
import restapi.tqs.Models.Order;
import restapi.tqs.Models.OrderLego;
import restapi.tqs.Models.User;
import restapi.tqs.Service.OrderService;


@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private OrderService service;

    Order order1, order2, order3;
    Client client1, client2;
    Address address1, address2;
    User user1, user2;
    OrderDTO orderDTO1, orderDTO2, orderDTO3;
    Set<OrderLego> orderLegos1, orderLegos2, orderLegos3;
    Lego lego1, lego2, lego3;
    List<OrderLegoDTO> orderLegoDTO1, orderLegoDTO2, orderLegoDTO3;
    ArrayList<Order> all_Orders;


    @BeforeEach
    void setUp(){

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

        all_Orders = new ArrayList<>();
        all_Orders.add(order1);
        all_Orders.add(order2);
        all_Orders.add(order3);        

        Set<Order> orders = client1.getOrders();
        orders.add(order1);
        client1.setOrders(orders);

        orders = client1.getOrders();
        orders.add(order2);
        client1.setOrders(orders);

        orders = client2.getOrders();
        orders.add(order3);
        client2.setOrders(orders);

        orderLegoDTO1 = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTO2 = buildOrderLegoDTO(1l,2l,3l,2);
        orderLegoDTO3 = buildOrderLegoDTO(1l,2l,3l,3);

        orderDTO1 = new OrderDTO(1l, 1l, 2100, orderLegoDTO1);
        orderDTO2 = new OrderDTO(1l, 1l, 1500, orderLegoDTO2);
        orderDTO3 = new OrderDTO(2l, 2l, 2000, orderLegoDTO3);
    }

    @Test
    void test_GetAllOrders_ReturnsCorrectOrders() throws Exception{

        Mockito.when(service.getAllOrders()).thenReturn(all_Orders);

        mvc.perform(get("/order")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$[0].scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[0].riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$[0].totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$[0].address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$[0].client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$[0].orderLego", hasSize(3)))
        .andExpect(jsonPath("$[1].orderId", is((int) order2.getOrderId())))
        .andExpect(jsonPath("$[1].scheduledTimeOfDelivery", is(order2.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[1].riderName", is(order2.getRiderName())))
        .andExpect(jsonPath("$[1].totalPrice", is(order2.getTotalPrice())))
        .andExpect(jsonPath("$[1].address", is((int) order2.getAddress().getAddressId())))
        .andExpect(jsonPath("$[1].client", is((int) order2.getClient().getClientId())))
        .andExpect(jsonPath("$[1].orderLego", hasSize(3)))
        .andExpect(jsonPath("$[2].orderId", is((int) order3.getOrderId())))
        .andExpect(jsonPath("$[2].scheduledTimeOfDelivery", is(order3.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[2].riderName", is(order3.getRiderName())))
        .andExpect(jsonPath("$[2].totalPrice", is(order3.getTotalPrice())))
        .andExpect(jsonPath("$[2].address", is((int) order3.getAddress().getAddressId())))
        .andExpect(jsonPath("$[2].client", is((int) order3.getClient().getClientId())))
        .andExpect(jsonPath("$[2].orderLego", hasSize(3)));

    }

    @Test
    void test_GetOrderById_ValidId_ReturnsCorrectOrder() throws Exception{

        Mockito.when(service.getOrderById(1)).thenReturn(order1);

        mvc.perform(get("/order/{orderId}",1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$.scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$.riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$.totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$.address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$.client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$.orderLego", hasSize(3)));
    }

    @Test
    void test_GetOrderById_InvalidId_ReturnsBadRequestStatus() throws Exception{
        
        Mockito.when(service.getOrderById(200)).thenThrow(OrderNotFoundException.class);

        mvc.perform(get("/order/{orderId}",200)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

    @Test
    void test_GetClientOrders_ValidId_ReturnsCorrectOrders() throws Exception{

        Mockito.when(service.getClientOrders(1)).thenReturn(new ArrayList<Order>(Arrays.asList(order1,order2)));

        mvc.perform(get("/order/client/{clientId}", 1)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].orderId", is((int) order1.getOrderId())))
        .andExpect(jsonPath("$[0].scheduledTimeOfDelivery", is(order1.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[0].riderName", is(order1.getRiderName())))
        .andExpect(jsonPath("$[0].totalPrice", is(order1.getTotalPrice())))
        .andExpect(jsonPath("$[0].address", is((int) order1.getAddress().getAddressId())))
        .andExpect(jsonPath("$[0].client", is((int) order1.getClient().getClientId())))
        .andExpect(jsonPath("$[0].orderLego", hasSize(3)))
        .andExpect(jsonPath("$[1].orderId", is((int) order2.getOrderId())))
        .andExpect(jsonPath("$[1].scheduledTimeOfDelivery", is(order2.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$[1].riderName", is(order2.getRiderName())))
        .andExpect(jsonPath("$[1].totalPrice", is(order2.getTotalPrice())))
        .andExpect(jsonPath("$[1].address", is((int) order2.getAddress().getAddressId())))
        .andExpect(jsonPath("$[1].client", is((int) order2.getClient().getClientId())))
        .andExpect(jsonPath("$[1].orderLego", hasSize(3)));
    }

    @Test
    void test_GetClientOrders_InvalidId_ReturnsBadRequestStatus() throws Exception{
        
        Mockito.when(service.getClientOrders(200)).thenThrow(ClientNotFoundException.class);

        mvc.perform(get("/order/client/{clientId}",200)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadScheduledTimeOfDelivery_ReturnsBadRequestStatus() throws Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2700, orderLegoDTOTest);

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenThrow(new BadScheduledTimeOfDeliveryException(""));

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

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenThrow(new ClientNotFoundException(""));

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

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenThrow(new AddressNotFoundException(""));

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

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenThrow(new LegoNotFoundException(""));

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

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenThrow(new BadOrderLegoDTOException(""));

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadOrderLegoList_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        OrderDTO orderDTOTest = new OrderDTO(1l, 1l, 2100, new ArrayList<OrderLegoDTO>());

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenThrow(new ClientNotFoundException(""));

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

        Mockito.when(service.makeOrder(any(OrderDTO.class))).thenReturn(order1);

        mvc.perform(post("/order")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.orderId", is((int) order1.getOrderId())))
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

        order.setOrderId(id);
        order.setClient(client);
        order.setAddress(address);
        order.setDate(date);
        order.setScheduledTimeOfDelivery(2100);
        order.setRiderName("Paulo " + id);
        order.setTotalPrice(totalPrice);

        for (OrderLego orderLego : orderLegos) {
            orderLego.setOrder(order);
        }
        order.setOrderLego(orderLegos);

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
