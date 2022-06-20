package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.swagger.v3.oas.annotations.media.Content;
import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.ManagerAlreadyExistsException;
import tqs.project.exceptions.ManagerNotFoundException;
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

    Manager manager1, manager2, manager3;
    User user1, user2, user3;
    RegisterDTO dto1, dto2, dto3;

    @BeforeEach
    void setup() throws ManagerNotFoundException, ManagerAlreadyExistsException{
        RestAssuredMockMvc.mockMvc( mvc );
        
        user1 = createUser(1);
        user2 = createUser(2);
        user3 = createUser(3);

        manager1 = new Manager();
        manager2 = new Manager();
        manager3 = new Manager();

        manager1.setUser(user1);
        manager2.setUser(user2);
        manager3.setUser(user3);

        user1.setManager(manager1);
        user2.setManager(manager2);
        user3.setManager(manager3);

        dto1 = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());
        dto2 = new RegisterDTO(user2.getUsername(), user2.getEmail(), user2.getPassword());
        dto3 = new RegisterDTO(user3.getUsername(), user3.getEmail(), user3.getPassword());

        Mockito.when(managerService.getAllManagers()).thenReturn(Arrays.asList(manager1, manager2));
        Mockito.when(managerService.login(manager1.getUser().getEmail())).thenReturn(manager1);
        Mockito.when(managerService.login(manager2.getUser().getEmail())).thenReturn(manager2);
        Mockito.when(managerService.login(manager3.getUser().getEmail())).thenThrow(ManagerNotFoundException.class);

        Mockito.when(managerService.register(dto1)).thenThrow(ManagerAlreadyExistsException.class);
        Mockito.when(managerService.register(dto2)).thenThrow(ManagerAlreadyExistsException.class);
        Mockito.when(managerService.register(dto3)).thenReturn(manager3);
    }

    @Test
    void test_GetAllManagers_ReturnsCorrectManagers(){
        given().get("/api/managers")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("[0].user.username", is (manager1.getUser().getUsername())).and()
               .body("[0].user.email", is (manager1.getUser().getEmail())).and()
               .body("[1].user.username", is (manager2.getUser().getUsername())).and()
               .body("[1].user.email", is (manager2.getUser().getEmail()));
    }

    @Test
    void test_Login_ManagerExists_ReturnsCorrectManager(){
        given().get("/api/managers/{email}", user1.getEmail())
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("user.username", is(user1.getUsername())).and()
               .body("user.email", is(user1.getEmail()));
    }

    @Test
    void test_Login_UserDoesNotExists_ReturnsBadRequestStatus(){
        given().get("/api/managers/{email}", user3.getEmail())
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_Register_ManagerExists_ReturnsBadRequestStatus() throws ManagerAlreadyExistsException{

        given().contentType(ContentType.JSON).body(dto1)
               .post("/api/managers")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_Register_ManagerDoesNotExist_ReturnsCorrectManager() throws ManagerAlreadyExistsException{

        given().contentType(ContentType.JSON).body(dto3)
               .post("/api/managers")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("user.username", is(dto3.getUsername())).and()
               .body("user.email", is(dto3.getEmail()));
    }

    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }

}
