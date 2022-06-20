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
import tqs.project.exceptions.UserNotFoundException;
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
public class UserControllerTestIT {
    
    @Autowired
    MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    User user1, user2;

    @BeforeEach
    void setup(){
        
        RestAssuredMockMvc.mockMvc( mvc );
        
        user1 = new User("User 1", "user1@gmail.com", "password1");
        user2 = new User("User 2", "user2@gmail.com", "password2");

        userRepository.saveAndFlush(user1);
        userRepository.saveAndFlush(user2);

    }

    @Test
    void test_GetAllUsers_ReturnsCorrectUsers(){

        given().get("/api/users")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("size", is(2)).and()
               .body("[0].username", is(user1.getUsername())).and()
               .body("[1].username", is(user2.getUsername()));

    }

    @Test
    void test_loginUser_UserExists_ReturnsCorrectUser() throws UserNotFoundException{
        
        given().get("/api/users/login/{email}", user1.getEmail())
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("username", is(user1.getUsername())).and()
               .body("email", is(user1.getEmail()));
    }

    @Test
    void test_loginUser_UserDoesNotExists_ReturnsBadRequestStatus() throws UserNotFoundException{
        
        given().get("/api/users/login/{email}", "Not a user email")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST).and();
    }

    @Test
    void test_registerUser_UserExists_ReturnsBadRequestStatus() throws UserAlreadyExistsException{
        
        RegisterDTO reg = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());
                
        given().contentType(ContentType.JSON).body(reg)
               .post("/api/users/register")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_registerUser_UserDoesNotExists_ReturnsCorrectUser() throws UserAlreadyExistsException{
        
        RegisterDTO reg = new RegisterDTO("User 3", "email3", "pass3");
                
        given().contentType(ContentType.JSON).body(reg)
               .post("/api/users/register")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("username", is("User 3")).and()
               .body("email", is("email3"));
    }
}
