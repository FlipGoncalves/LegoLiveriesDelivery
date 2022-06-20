package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.parsing.Parser;
import tqs.project.ProjectApplication;
import tqs.project.datamodels.AddressDTO;
import tqs.project.datamodels.OrderDTO;
import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.ManagerAlreadyExistsException;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Manager;
import tqs.project.model.Order;
import tqs.project.model.Store;
import tqs.project.model.User;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.ManagerRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.StoreRepository;
import tqs.project.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProjectApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class OrderControllerTestIT {
    
    @Autowired
    MockMvc mvc;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private AddressRepository addressRepository;

    Order order1, order2, order3;
    Store store1, store2; 
    Address address1, address2;
    AddressDTO addressDTO1, addressDTO2;
    OrderDTO orderDTO1, orderDTO2, orderDTO3;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc( mvc );
        
        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);

        address1 = addressRepository.saveAndFlush(address1);
        address2 = addressRepository.saveAndFlush(address2);

        store1 = new Store(1, "Store 1");
        store2 = new Store(2, "Store 2");

        store1 = storeRepository.saveAndFlush(store1);
        store2 = storeRepository.saveAndFlush(store2);

        order1 = new Order();
        order1 = orderRepository.saveAndFlush(order1);
        order1 = buildOrderObject(order1, 1);
        order1.setStatus(0);

        order1.setStore(store1);
        order1.setAddress(address1);
        
        store1.addOrder(order1);
        address1.addOrder(order1);

        order2 = new Order();        
        order2.setStore(store1);
        order2.setAddress(address2);
        order2 = orderRepository.saveAndFlush(order2);
        order2 = buildOrderObject(order2, 2);
        order2.setStatus(2);

        order2.setStore(store1);
        order2.setAddress(address2);

        store1.addOrder(order2);
        address2.addOrder(order2);

        order3 = new Order();
        order3 = orderRepository.saveAndFlush(order3);
        order3 = buildOrderObject(order3, 3);
        order3.setStatus(2);

        order3.setStore(store2);
        order3.setAddress(address1);

        store2.addOrder(order3);
        address1.addOrder(order3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);

        orderDTO1 = new OrderDTO("Client X", order1.getTimeOfDelivery(), order1.getStore().getName(), addressDTO1);

    }

    @Test
    void test_GetAllOrders_ReturnsCorrectOrders(){

        given().get("/api/orders")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("size()", is(3))
               .body("[0].clientName", is(order1.getClientName())).and()
               .body("[1].clientName", is(order2.getClientName())).and()
               .body("[2].clientName", is(order3.getClientName()));
    }

    @Test
    void test_MakeOrder_StoreDoesNotExist_ReturnsBadRequestStatus() throws StoreNotFoundException{

        OrderDTO dto = new OrderDTO("Client", 5, "Not a Store", addressDTO1);

        given().contentType(ContentType.JSON).body(dto)
               .post("/api/orders")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_MakeOrder_StoreExists_ReturnsCorrectOrderId() throws StoreNotFoundException{
        
        RestAssured.registerParser("text/plain", Parser.TEXT);

        given().contentType(ContentType.JSON).body(orderDTO1)
               .post("/api/orders")
               .then().log().body().assertThat()
               .status(HttpStatus.CREATED).and()
               .contentType(ContentType.TEXT).and()
               .body(containsString("\"orderId\" : " + orderRepository.findByClientName("Client X").get(0).getOrderId()));
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

    Order buildOrderObject(Order order, long id){
        
        order.setClientName("Client " + id);
        order.setDate(new Date());
        order.setTimeOfDelivery((int) id);
        order.setReview((int) id);

        return order;
    }
}
