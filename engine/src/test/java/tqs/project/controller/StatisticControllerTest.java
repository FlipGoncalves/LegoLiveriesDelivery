package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.controller.StatisticController;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;

@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RiderService riderService;

    @MockBean
    private OrderService orderService;

    List<Order> orders, completedOrders;
    List<Rider> riders;
    Order order1, order2, order3;
    Store store1, store2;
    Rider rider1, rider2;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc( mvc );
        order1 = new Order("Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order2 = new Order("Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order3 = new Order("Client 3", new Date(), 3, 4);
        order3.setStatus(2);

        store1 = new Store(1, "Store 1");
        store2 = new Store(2, "Store 2");

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);

        orders = new ArrayList<>(Arrays.asList(order1, order2, order3));

        completedOrders = new ArrayList<>(Arrays.asList(order2, order3));

        rider1 = new Rider(25, 7);
        rider2 = new Rider(50, 15);

        riders = new ArrayList<>(Arrays.asList(rider1, rider2));
    }

    /*@Test
    void test_getStatistics_NoStoreId_ReturnsCorrectStatistics(){
        when(orderService.getAllOrders()).thenReturn(orders);
        when(orderService.getAllOrdersByStatus(2)).thenReturn(completedOrders);
        when(riderService.getAllRiders()).thenReturn(riders);

        given().get("/api/statistics")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("numOrders", is(orders.size())).and()
               .body("numRiders", is(riders.size())).and()
               .body("completedOrders", is(completedOrders.size()));
    }

    @Test
    void test_getStatistics_WithStoreId_ReturnsCorrectStatistics(){
        when(orderService.getAllOrdersByStoreId(1)).thenReturn(new ArrayList<>(Arrays.asList(order1, order2)));
        when(orderService.getAllOrdersByStoreIdAndStatus(1,2)).thenReturn(new ArrayList<>(Arrays.asList(order2)));

        given().get("/api/statistics/{storeId}", 1)
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("numOrders", is(2)).and()
               .body("numRiders", is(0)).and()
               .body("completedOrders", is(1));
    }*/
}
