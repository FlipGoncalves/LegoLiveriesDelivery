package tqs.project.controllerTests;

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
import tqs.project.controller.UserController;
import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.User;
import tqs.project.service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    User user1, user2;

    @BeforeEach
    void setup(){
        RestAssuredMockMvc.mockMvc( mvc );
        user1 = new User("User 1", "user1@gmail.com", "password1");
        user2 = new User("User 2", "user2@gmail.com", "password2");

        when(userService.getUser("user1@gmail.com")).thenReturn(user1);
        when(userService.getUser(user2.getEmail())).thenReturn(null);
    }

    @Test
    void test_loginUser_UserExists_ReturnsCorrectUser(){
        given().get("/api/user/login/{email}", user1.getEmail())
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("username", is(user1.getUsername())).and()
               .body("email", is(user1.getEmail()));
    }

    @Test
    void test_loginUser_UserDoesNotExists_ReturnsBadRequestStatus(){
        given().get("/api/user/login/{email}", user2.getEmail())
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST).and();
    }

    @Test
    void test_registerUser_UserExists_ReturnsBadRequestStatus() throws UserAlreadyExistsException{
        RegisterDTO reg = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());
        when(userService.register(reg)).thenThrow(UserAlreadyExistsException.class);
        given().contentType(ContentType.JSON).body(reg)
               .post("/api/user/register")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_registerUser_UserDoesNotExists_ReturnsCorrectUser() throws UserAlreadyExistsException{
        RegisterDTO reg = new RegisterDTO(user2.getUsername(), user2.getEmail(), user2.getPassword());
        when(userService.register(reg)).thenReturn(user2);
        given().contentType(ContentType.JSON).body(reg)
               .post("/api/user/register")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("username", is(reg.getUsername())).and()
               .body("email", is(reg.getEmail()));
    }

}
