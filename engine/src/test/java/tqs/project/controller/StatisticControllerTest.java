package tqs.project.controller;

import static org.mockito.Mockito.when;
import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;

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
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.model.User;
import tqs.project.service.OrderService;
import tqs.project.service.RiderService;
import tqs.project.service.StoreService;

@WebMvcTest(StatisticController.class)
public class StatisticControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RiderService riderService;

    @MockBean
    private OrderService orderService;

    @MockBean 
    private StoreService storeService;

    List<Order> orders, completedOrders;
    List<Rider> riders;
    List<Store> stores;
    Order order1, order2, order3;
    Store store1, store2;
    Rider rider1, rider2;
    User user1, user2;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc( mvc );
        order1 = new Order("Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order2 = new Order("Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order3 = new Order("Client 3", new Date(), 3, 4);
        order3.setStatus(2);

        store1 = new Store(1, "Store1");
        store2 = new Store(2, "Store2");

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);

        orders = new ArrayList<>(Arrays.asList(order1, order2, order3));

        completedOrders = new ArrayList<>(Arrays.asList(order2, order3));

        stores = new ArrayList<>(Arrays.asList(store1, store2));

        user1 = new User("Rider1", "email1", "pass1");
        user2 = new User("Rider2", "email2", "pass2");

        rider1 = new Rider(25, 7);
        rider1.setUser(user1);
        rider2 = new Rider(50, 15);
        rider2.setUser(user2);

        riders = new ArrayList<>(Arrays.asList(rider1, rider2));
    }

    @Test
    void test_getStatistics_NoStoreId_ReturnsCorrectStatistics(){
        when(orderService.getAllOrders()).thenReturn(orders);
        when(orderService.getAllOrdersByStatus(2)).thenReturn(completedOrders);
        when(riderService.getAllRiders()).thenReturn(riders);
        when(storeService.getAllStores()).thenReturn(stores);
        when(orderService.getAllOrdersByStoreId(store1.getStoreId())).thenReturn(new ArrayList<>(Arrays.asList(order1, order2)));
        when(orderService.getAllOrdersByStoreId(store2.getStoreId())).thenReturn(new ArrayList<>(Arrays.asList(order3)));
        when(orderService.getAllOrdersByStoreIdAndStatus(store1.getStoreId(), 2)).thenReturn(new ArrayList<>(Arrays.asList(order1)));
        when(orderService.getAllOrdersByStoreIdAndStatus(store2.getStoreId(), 2)).thenReturn(new ArrayList<>(Arrays.asList(order3)));


        given().get("/api/statistics")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("numOrders", is(orders.size())).and()
               .body("numRiders", is(riders.size())).and()
               .body("completedOrders", is(completedOrders.size())).and()
               .body("orderByStore.Store1", is(2)).and()
               .body("orderByStore.Store2", is(1)).and()
               .body("compOrderByStore.Store1", is(1)).and()
               .body("compOrderByStore.Store2", is(1)).and()
               .body("reviewPerRider.Rider1", is((float) ((double) rider1.getReviewSum()/rider1.getTotalReviews()))).and()
               .body("reviewPerRider.Rider2", is((float) ((double) rider2.getReviewSum()/rider2.getTotalReviews())));


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
               .body("completedOrders", is(1));
    }
}
