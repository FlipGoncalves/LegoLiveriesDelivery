package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.parsing.Parser;
import tqs.project.controller.OrderController;
import tqs.project.datamodels.AddressDTO;
import tqs.project.datamodels.OrderDTO;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Store;
import tqs.project.service.OrderService;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private OrderService orderService;

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

        store1 = new Store(1, "Store 1");
        store2 = new Store(2, "Store 2");

        order1 = new Order("Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order1.setStore(store1);
        order1.setAddress(address1);

        store1.getOrders().add(order1);
        address1.getOrders().add(order1);

        order2 = new Order("Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order2.setStore(store1);
        order2.setAddress(address2);

        store1.getOrders().add(order2);
        address2.getOrders().add(order2);

        order3 = new Order("Client 3", new Date(), 3, 4);
        order3.setStatus(2);
        order3.setStore(store2);
        order3.setAddress(address1);
        
        store2.getOrders().add(order3);
        address1.getOrders().add(order3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);

        orderDTO1 = new OrderDTO(order1.getClientName(), order1.getTimeOfDelivery(), order1.getStore().getName(), addressDTO1);
        orderDTO2 = new OrderDTO(order1.getClientName(), order1.getTimeOfDelivery(), order1.getStore().getName(), addressDTO1);
        orderDTO3 = new OrderDTO(order1.getClientName(), order1.getTimeOfDelivery(), order1.getStore().getName(), addressDTO1);

    }

    @Test
    void test_GetAllOrders_ReturnsCorrectOrders(){

        Mockito.when(orderService.getAllOrders()).thenReturn(new ArrayList<>(Arrays.asList(order1, order2, order3)));

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

        Mockito.when(orderService.makeOrder(dto)).thenThrow(StoreNotFoundException.class);

        given().contentType(ContentType.JSON).body(dto)
               .post("/api/orders")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_MakeOrder_StoreExists_ReturnsCorrectOrderId() throws StoreNotFoundException{
        
        RestAssured.registerParser("text/plain", Parser.TEXT);

        Mockito.when(orderService.makeOrder(orderDTO1)).thenReturn(order1);

        order1.setOrderId(1);

        given().contentType(ContentType.JSON).body(orderDTO1)
               .post("/api/orders")
               .then().log().body().assertThat()
               .status(HttpStatus.CREATED).and()
               .contentType(ContentType.TEXT).and()
               .body(containsString("\"orderId\" : 1"));
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
}
