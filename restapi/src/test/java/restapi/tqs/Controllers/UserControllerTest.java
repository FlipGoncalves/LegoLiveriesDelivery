package restapi.tqs.Controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import restapi.tqs.Controller.ClientController;
import restapi.tqs.Controller.LegoController;
import restapi.tqs.Controller.UserController;
import restapi.tqs.DataModels.LegoDTO;
import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.BadLegoDTOException;
import restapi.tqs.Exceptions.ClientAlreadyExistsException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Exceptions.UserAlreadyExistsException;
import restapi.tqs.Exceptions.UserNotFoundException;
import restapi.tqs.Models.Client;
import restapi.tqs.Models.Lego;
import restapi.tqs.Models.User;
import restapi.tqs.Service.ClientService;
import restapi.tqs.Service.LegoService;
import restapi.tqs.Service.UserService;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService service;

    User user1, user2, user3;

    @BeforeEach
    void setUp(){
        
        user1 = createUser(1);
        user2 = createUser(2);
        user3 = createUser(3);

    }

    @Test
    void test_GetAllUsers_ReturnsCorrectUsers() throws Exception{

        Mockito.when(service.getAllUsers()).thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));

        mvc.perform(get("/user")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].username", is(user1.getUsername())))
        .andExpect(jsonPath("$[0].password", is(user1.getPassword())))
        .andExpect(jsonPath("$[0].email", is(user1.getEmail())))
        .andExpect(jsonPath("$[1].username", is(user2.getUsername())))
        .andExpect(jsonPath("$[1].password", is(user2.getPassword())))
        .andExpect(jsonPath("$[1].email", is(user2.getEmail())));

    }

    @Test
    void test_LoginUser_UserDoesNotExist_ReturnsBadRequestStatus() throws Exception{

        Mockito.when(service.login(user3.getEmail())).thenThrow(UserNotFoundException.class);

        mvc.perform(get("/user/login/{email}", user3.getEmail())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_LoginUser_UserExists_ReturnsCorrectUser() throws Exception{

        Mockito.when(service.login(user1.getEmail())).thenReturn(user1);

        mvc.perform(get("/user/login/{email}", user1.getEmail())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.username", is(user1.getUsername())))
        .andExpect(jsonPath("$.password", is(user1.getPassword())))
        .andExpect(jsonPath("$.email", is(user1.getEmail())));
    }

    @Test
    void test_RegisterUser_UserAlreadyExists_ReturnsBadRequestStatus() throws Exception{

        RegisterDTO dto = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());

        Mockito.when(service.register(dto)).thenThrow(UserAlreadyExistsException.class);

        mvc.perform(post("/user/register")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_RegisterUser_UserDoesNotExist_ReturnsCorrectUser() throws Exception{

        RegisterDTO dto = new RegisterDTO(user3.getUsername(), user3.getEmail(), user3.getPassword());

        Mockito.when(service.register(dto)).thenReturn(user3);

        mvc.perform(post("/user/register")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print())
        .andExpect(jsonPath("$.username", is(user3.getUsername())))
        .andExpect(jsonPath("$.password", is(user3.getPassword())))
        .andExpect(jsonPath("$.email", is(user3.getEmail())));
    }

    
    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }
}
