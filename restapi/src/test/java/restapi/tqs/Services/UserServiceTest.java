package restapi.tqs.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
import restapi.tqs.Exceptions.UserAlreadyExistsException;
import restapi.tqs.Exceptions.UserNotFoundException;
import restapi.tqs.Models.User;
import restapi.tqs.Repositories.UserRepository;
import restapi.tqs.Service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock(lenient = true)
    private UserRepository userRepository;

    @InjectMocks
    private UserService service;

    User user1, user2, user3;

    @BeforeEach
    void setUp(){
        user1 = createUser(1);
        user2 = createUser(2);
        user3 = createUser(3);

        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(user1, user2)));
        Mockito.when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(user2.getEmail())).thenReturn(Optional.of(user2));
        Mockito.when(userRepository.findByEmail(user3.getEmail())).thenReturn(Optional.empty());
    }

    @Test
    void test_GetAllUsers_ReturnsCorrectUsers(){

        List<User> result = service.getAllUsers();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(user1));
        assertTrue(result.contains(user2));
    }

    @Test
    void test_Login_UserExists_ReturnsCorrectUser() throws UserNotFoundException{
        User result = service.login(user1.getEmail());

        assertEquals(result, user1);
    }

    @Test
    void test_Login_UserDoesNotExist_ThrowsUserNotFoundException(){
        
        assertThrows(UserNotFoundException.class, () -> {service.login(user3.getEmail());});
    }

    @Test
    void test_Register_UserExists_ThrowsUserAlreadyExistsException(){
        RegisterDTO dto = new RegisterDTO(user1.getUsername(), user1.getEmail(), user1.getPassword());

        assertThrows(UserAlreadyExistsException.class, () -> {service.register(dto);});
    }

    @Test
    void test_Register_UserDoesNotExist_ReturnsCorrectUser() throws UserAlreadyExistsException{
        RegisterDTO dto = new RegisterDTO(user3.getUsername(), user3.getEmail(), user3.getPassword());

        User result = service.register(dto);

        assertNotNull(result);
        assertEquals(user3.getUsername(), result.getUsername());
        assertEquals(user3.getPassword(), result.getPassword());
        assertEquals(user3.getEmail(), result.getEmail());

    }

    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }
}
