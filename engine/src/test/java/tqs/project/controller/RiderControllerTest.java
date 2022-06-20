package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
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
import tqs.project.controller.RiderController;
import tqs.project.datamodels.RiderDTO;
import tqs.project.exceptions.RiderAlreadyExistsException;
import tqs.project.exceptions.RiderNotFoundException;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.service.RiderService;

@WebMvcTest(RiderController.class)
public class RiderControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private RiderService riderService;

    Rider rider1, rider2;
    User user1, user2;
    List<Rider> riders;

    @BeforeEach
    void setUp(){
        RestAssuredMockMvc.mockMvc( mvc );
        rider1 = new Rider(25, 7);
        user1 = new User("User 1", "user1@gmail.com", "password1");
        rider1.setUser(user1);

        rider2 = new Rider(50, 15);
        user2 = new User("User 2", "user2@gmail.com", "password2");
        rider2.setUser(user2);

        riders = new ArrayList<Rider>(Arrays.asList(rider1, rider2));

        when(riderService.getAllRiders()).thenReturn(riders);
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
        String username = "User 3";
        String email = "user3@gmail.com";
        String password = "password3";
        int numRev = 7;
        int sumRev = 25;

        RiderDTO riderDTO = new RiderDTO(username, email, password, numRev, sumRev);

        when(riderService.insertRider(riderDTO)).thenThrow(RiderAlreadyExistsException.class);
        
        given().contentType(ContentType.JSON).body(riderDTO)
               .post("/api/riders")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    
    @Test
    void test_addRider_ValidRiderDTO_ReturnsCorrectRider() throws UserAlreadyExistsException, RiderAlreadyExistsException{
        String username = "User 1";
        String email = "user1@gmail.com";
        String password = "password1";
        int numRev = 4;
        int sumRev = 17;

        RiderDTO riderDTO = new RiderDTO(username, email, password, numRev, sumRev);

        User user = new User(username, email, password);
        Rider rider = new Rider(sumRev, numRev);
        rider.setUser(user);

        when(riderService.insertRider(riderDTO)).thenReturn(rider);
        
        given().contentType(ContentType.JSON).body(riderDTO)
               .post("/api/riders")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("reviewSum", is(rider.getReviewSum())).and()
               .body("totalReviews", is(rider.getTotalReviews())).and()
               .body("user.username", is(rider.getUser().getUsername())).and()
               .body("user.email", is(rider.getUser().getEmail()));
    }
}
