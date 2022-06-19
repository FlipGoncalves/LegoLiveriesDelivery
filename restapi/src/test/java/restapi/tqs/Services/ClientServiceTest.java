package restapi.tqs.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import restapi.tqs.DataModels.RegisterDTO;
import restapi.tqs.Exceptions.ClientAlreadyExistsException;
import restapi.tqs.Exceptions.ClientNotFoundException;
import restapi.tqs.Models.Client;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.ClientRepository;
import restapi.tqs.Repositories.UserRepository;
import restapi.tqs.Service.ClientService;

@ExtendWith(MockitoExtension.class)
class ClientServiceTest {
    
    @Mock(lenient = true)
    private ClientRepository clientRepository;

    @Mock(lenient = true)
    private UserRepository userRepository;

    @InjectMocks
    private ClientService service;

    Client client1, client2, client3;
    User user1, user2, user3;
    RegisterDTO dto1, dto2, dto3;

    @BeforeEach
    void setUp(){

        user1 = createUser(1);
        user2 = createUser(2);
        user3 = createUser(3);

        client1 = new Client();
        client2 = new Client();
        client3 = new Client();

        client1.setUser(user1);
        client2.setUser(user2);
        client3.setUser(user3);

        user1.setClient(client1);
        user2.setClient(client2);
        user3.setClient(client3);

        dto1 = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());
        dto2 = new RegisterDTO(user2.getUsername(), user2.getEmail(), user2.getPassword());
        dto3 = new RegisterDTO(user3.getUsername(), user3.getEmail(), user3.getPassword());

        Mockito.when(clientRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(client1, client2)));
        Mockito.when(clientRepository.findByUserEmail(client1.getUser().getEmail())).thenReturn(Optional.of(client1));
        Mockito.when(clientRepository.findByUserEmail(client2.getUser().getEmail())).thenReturn(Optional.of(client2));
        Mockito.when(clientRepository.findByUserEmail(client3.getUser().getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(user2.getEmail())).thenReturn(Optional.of(user2));
    }

    @Test
    void test_GetAllClients_ReturnsCorrectClients(){

        List<Client> result = service.getAllClients();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(client1));
        assertTrue(result.contains(client2));
    }

    @Test
    void test_Login_ClientExists_ReturnsCorrect() throws ClientNotFoundException{

        Client result = service.login(client1.getUser().getEmail());

        assertNotNull(result);
        assertEquals(client1, result);
    }

    @Test
    void test_Login_ClientDoesNotExist_ThrowsClientDoesNotExistException(){
        
        assertThrows(ClientNotFoundException.class, () -> {service.login(client3.getUser().getEmail());});

    }

    @Test
    void test_CreateOrGetUser_UserExists_GetsUser(){

        User result = service.createOrGetUser(dto1);

        assertEquals(user1, result);

    }

    @Test
    void test_CreateOrGetUser_UserDoesNotExist_CreatesAndSavesUser(){

        Mockito.when(userRepository.saveAndFlush(any(User.class))).thenReturn(user3);

        User result = service.createOrGetUser(dto3);

        assertEquals(user3, result);

    }

    @Test
    void test_InsertClient_ClientExists_ThrowsClientAlreadyExistsException() throws ClientAlreadyExistsException{
        
        assertThrows(ClientAlreadyExistsException.class, () -> {service.insertClient(dto1);});

    }

    @Test
    void test_InsertClient_ClientDoesNotExist_ReturnsCorrectClient() throws ClientAlreadyExistsException{
        
        Mockito.when(userRepository.saveAndFlush(any(User.class))).thenReturn(user3);
        Mockito.when(clientRepository.saveAndFlush(any(Client.class))).thenReturn(client3);


        Client result = service.insertClient(dto3);

        assertEquals(client3, result);
        assertEquals(user3, result.getUser());


    }

    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }
}
