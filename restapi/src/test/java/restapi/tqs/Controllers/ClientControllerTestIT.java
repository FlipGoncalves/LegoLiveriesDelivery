package restapi.tqs.Controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.AfterEach;
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
import restapi.tqs.Models.Client;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.ClientRepository;
import restapi.tqs.Repositories.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TqsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class ClientControllerTestIT {
        
    @Autowired
    MockMvc mvc;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ObjectMapper objectMapper;

    Client client1, client2, client3;
    User user1, user2, user3;

    @BeforeEach
    void setUp(){
        user1 = createUser(1);
        user2 = createUser(2);
        user3 = createUser(3);

        user1 = userRepository.saveAndFlush(user1);
        user2 = userRepository.saveAndFlush(user2);

        client1 = new Client();
        client2 = new Client();
        client3 = new Client();

        client1.setUser(user1);
        client2.setUser(user2);
        client3.setUser(user3);

        client1 = clientRepository.saveAndFlush(client1);
        client2 = clientRepository.saveAndFlush(client2);

        user1.setClient(client1);
        user2.setClient(client2);
        user3.setClient(client3);

    }

    @AfterEach
    void cleanUp(){
        userRepository.deleteAll();
        clientRepository.deleteAll();
    }

    @Test
    void test_GetAllClients_ReturnsCorrectClients() throws Exception{

        mvc.perform(get("/client")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].user.username", is(client1.getUser().getUsername())))
        .andExpect(jsonPath("$[0].user.password", is(client1.getUser().getPassword())))
        .andExpect(jsonPath("$[0].user.email", is(client1.getUser().getEmail())))
        .andExpect(jsonPath("$[1].user.username", is(client2.getUser().getUsername())))
        .andExpect(jsonPath("$[1].user.password", is(client2.getUser().getPassword())))
        .andExpect(jsonPath("$[1].user.email", is(client2.getUser().getEmail())));

    }

    @Test
    void test_GetClientByEmail_ClientDoesNotExist_ReturnsBadRequestStatus() throws Exception{

        mvc.perform(get("/client/login/{clientEmail}", client3.getUser().getEmail())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_GetClientByEmail_ClientExists_ReturnsCorrectClient() throws Exception{

        mvc.perform(get("/client/login/{clientEmail}", client1.getUser().getEmail())
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(print())
        .andExpect(jsonPath("$.user.username", is(client1.getUser().getUsername())))
        .andExpect(jsonPath("$.user.password", is(client1.getUser().getPassword())))
        .andExpect(jsonPath("$.user.email", is(client1.getUser().getEmail())));
    }

    @Test
    void test_InsertClient_ClientAlreadyExists_ReturnsBadRequestStatus() throws Exception{

        RegisterDTO dto = new RegisterDTO(client1.getUser().getUsername(), client1.getUser().getEmail(), client1.getUser().getPassword());

        mvc.perform(post("/client/register")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_InsertClient_ClientDoesNotExist_ReturnsCorrectClient() throws Exception{

        RegisterDTO dto = new RegisterDTO(client3.getUser().getUsername(), client3.getUser().getEmail(), client3.getUser().getPassword());

        mvc.perform(post("/client/register")
        .content(objectMapper.writeValueAsString(dto))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andDo(print())
        .andExpect(jsonPath("$.user.username", is(client3.getUser().getUsername())))
        .andExpect(jsonPath("$.user.password", is(client3.getUser().getPassword())))
        .andExpect(jsonPath("$.user.email", is(client3.getUser().getEmail())));
    }

    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }
}
