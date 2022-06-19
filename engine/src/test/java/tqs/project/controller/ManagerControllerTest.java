package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Manager;
import tqs.project.model.User;
import tqs.project.service.ManagerService;

@WebMvcTest(ManagerController.class)
public class ManagerControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private ManagerService managerService;

    User user1, user2;
    Manager manager1, manager2;

    @BeforeEach
    void setup(){
        RestAssuredMockMvc.mockMvc( mvc );
        user1 = new User("User 1", "user1@gmail.com", "password1");
        user2 = new User("User 2", "user2@gmail.com", "password2");
        manager1 = new Manager();
        manager2 = new Manager();
        
        manager1.setUser(user1);
        manager2.setUser(user2);

        when(managerService.getUser("user1@gmail.com")).thenReturn(manager1);
        when(managerService.getUser(user2.getEmail())).thenReturn(null);
    }

    @Test
    void test_loginUser_UserExists_ReturnsCorrectUser(){
        given().get("/api/manager/{email}", user1.getEmail())
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("user.username", is(user1.getUsername())).and()
               .body("user.email", is(user1.getEmail()));
    }

    @Test
    void test_loginUser_UserDoesNotExists_ReturnsBadRequestStatus(){
        given().get("/api/manager/{email}", user2.getEmail())
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST).and();
    }

    @Test
    void test_registerUser_UserExists_ReturnsBadRequestStatus() throws UserAlreadyExistsException{
        RegisterDTO reg = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());
        when(managerService.register(reg)).thenThrow(UserAlreadyExistsException.class);
        given().contentType(ContentType.JSON).body(reg)
               .post("/api/manager")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_registerUser_UserDoesNotExists_ReturnsCorrectUser() throws UserAlreadyExistsException{
        RegisterDTO reg = new RegisterDTO(user2.getUsername(), user2.getEmail(), user2.getPassword());
        when(managerService.register(reg)).thenReturn(manager2);
        given().contentType(ContentType.JSON).body(reg)
               .post("/api/manager")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("user.username", is(reg.getUsername())).and()
               .body("user.email", is(reg.getEmail()));
    }

}
