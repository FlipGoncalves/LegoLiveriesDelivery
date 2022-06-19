package restapi.tqs.Controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import restapi.tqs.TqsApplication;
import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TqsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class UserControllerTestIT {
    
    @Autowired
    MockMvc mvc;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    User user1, user2, user3;

    @BeforeEach
    void setUp(){
        user1 = createUser(1);
        user2 = createUser(2);
        user3 = createUser(3);

        user1 = userRepository.saveAndFlush(user1);
        user2 = userRepository.saveAndFlush(user2);

    }

    @Test
    void test_GetAllUsers_ReturnsCorrectUsers() throws Exception{

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

        mvc.perform(get("/user/login/{email}", user3.getEmail())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_LoginUser_UserExists_ReturnsCorrectUser() throws Exception{

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

        mvc.perform(post("/user/register")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_RegisterUser_UserDoesNotExist_ReturnsCorrectUser() throws Exception{

        RegisterDTO dto = new RegisterDTO(user3.getUsername(), user3.getEmail(), user3.getPassword());

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
