package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;

import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.ProjectApplication;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.model.User;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.RiderRepository;
import tqs.project.repository.StoreRepository;
import tqs.project.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProjectApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class StatisticControllerTestIT {
   
    @Autowired
    private MockMvc mvc;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired 
    private StoreRepository storeRepository;

    @Autowired 
    private UserRepository userRepository;

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

        order1 = orderRepository.saveAndFlush(order1);
        order2 = orderRepository.saveAndFlush(order2);
        order3 = orderRepository.saveAndFlush(order3);

        store1 = new Store(1, "Store1");
        store2 = new Store(2, "Store2");

        store1 = storeRepository.saveAndFlush(store1);
        store2 = storeRepository.saveAndFlush(store2);

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);

        store1.getOrders().add(order1);
        store1.getOrders().add(order2);
        store2.getOrders().add(order3);

        user1 = new User("Rider1", "email1", "pass1");
        user2 = new User("Rider2", "email2", "pass2");

        user1 = userRepository.saveAndFlush(user1);
        user2 = userRepository.saveAndFlush(user2);

        rider1 = new Rider(25, 7);
        rider1.setUser(user1);
        rider2 = new Rider(50, 15);
        rider2.setUser(user2);

        rider1 = riderRepository.saveAndFlush(rider1);
        rider2 = riderRepository.saveAndFlush(rider2);
    }

    @Test
    void test_getStatistics_NoStoreId_ReturnsCorrectStatistics(){

        given().get("/api/statistics")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("numOrders", is(orderRepository.findAll().size())).and()
               .body("numRiders", is(riderRepository.findAll().size())).and()
               .body("completedOrders", is(orderRepository.findByStatus(2).size())).and()
               .body("orderByStore.Store1", is(2)).and()
               .body("orderByStore.Store2", is(1)).and()
               .body("compOrderByStore.Store1", is(1)).and()
               .body("compOrderByStore.Store2", is(1)).and()
               .body("reviewPerRider.Rider1", is((float) ((double) rider1.getReviewSum()/rider1.getTotalReviews()))).and()
               .body("reviewPerRider.Rider2", is((float) ((double) rider2.getReviewSum()/rider2.getTotalReviews())));


    }

    @Test
    void test_getStatistics_WithStoreId_ReturnsCorrectStatistics(){
        
        given().get("/api/statistics/{storeId}", store1.getStoreId())
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("numOrders", is(2)).and()
               .body("completedOrders", is(1));
    }
}
