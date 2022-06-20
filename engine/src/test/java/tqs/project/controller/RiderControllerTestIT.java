package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import tqs.project.datamodels.RiderDTO;
import tqs.project.exceptions.ManagerAlreadyExistsException;
import tqs.project.exceptions.RiderAlreadyExistsException;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.model.Manager;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.model.User;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.ManagerRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.RiderRepository;
import tqs.project.repository.StoreRepository;
import tqs.project.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProjectApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class RiderControllerTestIT {
    
    @Autowired
    MockMvc mvc;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private UserRepository userRepository;

    Rider rider1, rider2;
    User user1, user2;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc( mvc );       
        user1 = new User("User 1", "user1@gmail.com", "password1");
        userRepository.saveAndFlush(user1);
        rider1 = new Rider(25, 7);
        rider1.setUser(user1);
        riderRepository.saveAndFlush(rider1);
        
        user2 = new User("User 2", "user2@gmail.com", "password2");
        userRepository.saveAndFlush(user2);
        rider2 = new Rider(50, 15);
        rider2.setUser(user2);
        riderRepository.saveAndFlush(rider2);
    }

    @Test
    void test_getAllRiders_ReturnsCorrectRiders(){
        given().get("/api/riders")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("size()", is(2))
               .body("[0].reviewSum", is(rider1.getReviewSum())).and()
               .body("[0].totalReviews", is(rider1.getTotalReviews())).and()
               .body("[0].user.username", is(rider1.getUser().getUsername())).and()
               .body("[0].user.email", is(rider1.getUser().getEmail())).and()
               .body("[1].reviewSum", is(rider2.getReviewSum())).and()
               .body("[1].totalReviews", is(rider2.getTotalReviews())).and()
               .body("[1].user.username", is(rider2.getUser().getUsername())).and()
               .body("[1].user.email", is(rider2.getUser().getEmail()));
    }

    @Test
    void test_addRider_InvalidRiderDTO_ReturnsBadRequestStatus() throws RiderAlreadyExistsException{

        RiderDTO riderDTO = new RiderDTO(rider1.getUser().getUsername(), rider1.getUser().getEmail(), rider1.getUser().getPassword(), rider1.getTotalReviews(), rider1.getReviewSum());
        
        given().contentType(ContentType.JSON).body(riderDTO)
               .post("/api/riders")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    
    @Test
    void test_addRider_ValidRiderDTO_ReturnsCorrectRider() throws UserAlreadyExistsException, RiderAlreadyExistsException{
        String username = "User 3";
        String email = "user3@gmail.com";
        String password = "password3";
        int numRev = 4;
        int sumRev = 17;

        RiderDTO riderDTO = new RiderDTO(username, email, password, numRev, sumRev);
        
        given().contentType(ContentType.JSON).body(riderDTO)
               .post("/api/riders")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("reviewSum", is(sumRev)).and()
               .body("totalReviews", is(numRev)).and()
               .body("user.username", is(username)).and()
               .body("user.email", is(email));
    }
}
