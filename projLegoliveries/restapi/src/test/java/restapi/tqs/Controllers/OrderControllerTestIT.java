package restapi.tqs.Controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import restapi.tqs.TqsApplication;
import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.DataModels.OrderDTO;
import restapi.tqs.DataModels.OrderLegoDTO;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TqsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
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
    AddressDTO addressDTO1, addressDTO2, addressDTO3;

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

    @PersistenceContext
    private EntityManager em;

    @Autowired
    private OrderService orderService;

    public static MockWebServer mockBackEnd;

    @BeforeAll
    static void allSetUp() throws IOException{
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @BeforeEach
    void setUp(){

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        orderService.setEngineURL(baseUrl);

        user1 = createUser(1);
        user2 = createUser(2);

        user1 = userRepository.saveAndFlush(user1);
        user2 = userRepository.saveAndFlush(user2);

        client1 = new Client();
        client2 = new Client();

        client1.setUser(user1);
        client2.setUser(user2);

        client1 = clientRepository.saveAndFlush(client1);
        client2 = clientRepository.saveAndFlush(client2);

        user1.setClient(client1);
        user2.setClient(client2);

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

        order1 = new Order();
        order2 = new Order();
        order3 = new Order();

        order1 = orderRepository.saveAndFlush(order1);
        order2 = orderRepository.saveAndFlush(order2);
        order3 = orderRepository.saveAndFlush(order3);

        orderLegos1 = buildAndSaveOrderLegoList(order1, lego1, lego2, lego3,1);
        orderLegos2 = buildAndSaveOrderLegoList(order2, lego1, lego2, lego3,2);
        orderLegos3 = buildAndSaveOrderLegoList(order3, lego1, lego2, lego3,3);

        order1 = buildOrderObject(order1, client1, address1, orderLegos1, 1);
        order2 = buildOrderObject(order2, client1, address1, orderLegos2, 2);
        order3 = buildOrderObject(order3, client2, address2, orderLegos3, 3);

        orderLegoDTO1 = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTO2 = buildOrderLegoDTO(1l,2l,3l,2);
        orderLegoDTO3 = buildOrderLegoDTO(1l,2l,3l,3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);

        orderDTO1 = new OrderDTO(1l, addressDTO1, 2100, orderLegoDTO1);
        orderDTO2 = new OrderDTO(1l, addressDTO1, 1500, orderLegoDTO2);
        orderDTO3 = new OrderDTO(2l, addressDTO2, 2000, orderLegoDTO3);

    }

    @AfterEach
    void cleanUp(){
        em.clear();
        userRepository.deleteAll();
        clientRepository.deleteAll();
        legoRepository.deleteAll();
        orderLegoRepository.deleteAll();
        orderRepository.deleteAll();
        addressRepository.deleteAll();
    }

    @AfterAll
    static void allCleanUp() throws IOException{
        mockBackEnd.shutdown();
    }

    @Test
    void test_GetAllOrders_ReturnsCorrectOrders() throws Exception{

        mvc.perform(get("/orders")
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

        mvc.perform(get("/orders/{orderId}",order1.getOrderId())
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
        
        mvc.perform(get("/orders/{orderId}",200)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

    @Test
    void test_GetClientOrders_ValidId_ReturnsCorrectOrders() throws Exception{

        mvc.perform(get("/orders/client/{clientId}", order1.getClient().getClientId())
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
        
        mvc.perform(get("/orders/client/{clientId}",200)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadScheduledTimeOfDelivery_ReturnsBadRequestStatus() throws Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);

        OrderDTO orderDTOTest = new OrderDTO(1l, addressDTO1, 2700, orderLegoDTOTest);

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadClientId_ReturnsBadRequestStatus() throws Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(50l, addressDTO1, 2100, orderLegoDTOTest);

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadAddressId_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, addressDTO3, 2100, orderLegoDTOTest);

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadLegoId_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(50l,2l,3l,1);
        OrderDTO orderDTOTest = new OrderDTO(1l, addressDTO1, 2100, orderLegoDTOTest);

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadOrderLegoDTO_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(1l,2l,3l,1);
        orderLegoDTOTest.get(0).setQuantity(-5);
        OrderDTO orderDTOTest = new OrderDTO(1l, addressDTO1, 2100, orderLegoDTOTest);

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_InvalidOrderDTO_BadOrderLegoList_ReturnsBadRequestStatus() throws JsonProcessingException, Exception{
        OrderDTO orderDTOTest = new OrderDTO(1l, addressDTO1, 2100, new ArrayList<OrderLegoDTO>());

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_MakeOrder_ValidOrderDTO_ReturnsCorrectOrder() throws JsonProcessingException, Exception{
        List<OrderLegoDTO> orderLegoDTOTest = buildOrderLegoDTO(lego1.getLegoId(),lego1.getLegoId(),lego1.getLegoId(),1);
        OrderDTO orderDTOTest = new OrderDTO(order1.getClient().getClientId(), addressDTO1, 2100, orderLegoDTOTest);

        String responseFromEngine = "{\"orderId\" : " + 1 + " }";

        mockBackEnd.enqueue(new MockResponse().setBody(responseFromEngine).setResponseCode(201));

        mvc.perform(post("/orders")
        .content(objectMapper.writeValueAsString(orderDTOTest))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.scheduledTimeOfDelivery", is(orderDTOTest.getScheduledTimeOfDelivery())))
        .andExpect(jsonPath("$.client", is((int) orderDTOTest.getClientId())))
        .andExpect(jsonPath("$.address", is((int)address1.getAddressId())))
        .andExpect(jsonPath("$.orderLego", hasSize(3)));
    }

    @Test
    void test_UpdateOrderStatus_InvalidArg_ReturnsBadRequestStatus() throws Exception{

        mvc.perform(post("/orders/1/4")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_UpdateOrderStatus_ValidArgs_ReturnsOkStatus() throws Exception{

        mvc.perform(post("/orders/" + order1.getExternalOrderId() + "/2")
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk());
    }

    Order buildOrderObject(Order order, Client client, Address address, Set<OrderLego> orderLegos, long id){

        order.setClient(client);
        order.setAddress(address);
        order.setScheduledTimeOfDelivery(2100);
        order.setExternalOrderId(id);

        client.getOrders().add(order);
        address.getOrders().add(order);

        return order;
    }

    Lego buildLegoObject(long id){
        Lego lego =  new Lego();
        lego.setName("Lego " + id);
        lego.setImageUrl("URL " + id);
        lego.setPrice(10 + id);
        return lego;
    }
    
    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }

    Address buildAddressObject(long id){
        Address address = new Address();
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

    Set<OrderLego> buildAndSaveOrderLegoList(Order order, Lego lego1, Lego lego2, Lego lego3, long id){
       
        double totalPrice = 0;

        OrderLego orderLego1 = new OrderLego();
        orderLego1.setId(new OrderLegoId(order.getOrderId(), lego1.getLegoId()));
        orderLego1.setOrder(order);
        orderLego1.setLego(lego1);
        orderLego1.setPrice(lego1.getPrice());
        orderLego1.setQuantity(1 + (int) id);
        orderLego1 = orderLegoRepository.saveAndFlush(orderLego1);
        lego1.getOrderLego().add(orderLego1);
        order.getOrderLego().add(orderLego1);
        totalPrice += orderLego1.getPrice() * orderLego1.getQuantity();


        OrderLego orderLego2 = new OrderLego();
        orderLego2.setId(new OrderLegoId(order.getOrderId(), lego2.getLegoId()));
        orderLego2.setOrder(order);
        orderLego2.setLego(lego2);
        orderLego2.setPrice(lego2.getPrice());
        orderLego2.setQuantity(2 + (int) id);
        orderLego2 = orderLegoRepository.saveAndFlush(orderLego2);
        lego2.getOrderLego().add(orderLego2);
        order.getOrderLego().add(orderLego2);
        totalPrice += orderLego2.getPrice() * orderLego2.getQuantity();

        OrderLego orderLego3 = new OrderLego();
        orderLego3.setId(new OrderLegoId(order.getOrderId(), lego3.getLegoId()));
        orderLego3.setOrder(order);
        orderLego3.setLego(lego3);
        orderLego3.setPrice(lego3.getPrice());
        orderLego3.setQuantity(40 + (int) id);
        orderLego3 = orderLegoRepository.saveAndFlush(orderLego3);
        lego3.getOrderLego().add(orderLego3);
        order.getOrderLego().add(orderLego3);
        totalPrice += orderLego3.getPrice() * orderLego3.getQuantity();

        order.setTotalPrice(totalPrice);

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
