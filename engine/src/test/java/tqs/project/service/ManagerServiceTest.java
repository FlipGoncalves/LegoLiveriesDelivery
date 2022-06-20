package tqs.project.service;

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

import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.ManagerAlreadyExistsException;
import tqs.project.exceptions.ManagerNotFoundException;
import tqs.project.model.Manager;
import tqs.project.model.User;
import tqs.project.repository.ManagerRepository;
import tqs.project.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    @Mock(lenient = true)
    private ManagerRepository managerRepository;

    @Mock(lenient = true)
    private UserRepository userRepository;

    @InjectMocks
    private ManagerService service;

    Manager manager1, manager2, manager3;
    User user1, user2, user3;
    RegisterDTO dto1, dto2, dto3;

    @BeforeEach
    void setUp(){

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

        Mockito.when(managerRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(manager1, manager2)));
        Mockito.when(managerRepository.findByUserEmail(manager1.getUser().getEmail())).thenReturn(Optional.of(manager1));
        Mockito.when(managerRepository.findByUserEmail(manager2.getUser().getEmail())).thenReturn(Optional.of(manager2));
        Mockito.when(managerRepository.findByUserEmail(manager3.getUser().getEmail())).thenReturn(Optional.empty());
        Mockito.when(userRepository.findByEmail(user1.getEmail())).thenReturn(Optional.of(user1));
        Mockito.when(userRepository.findByEmail(user2.getEmail())).thenReturn(Optional.of(user2));
    }

    @Test
    void test_GetAllManagers_ReturnsCorrectManagers(){

        List<Manager> result = service.getAllManagers();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(manager1));
        assertTrue(result.contains(manager2));
    }

    @Test
    void test_Login_ManagerExists_ReturnsCorrectManager() throws ManagerNotFoundException{

        Manager result = service.login(manager1.getUser().getEmail());

        assertNotNull(result);
        assertEquals(manager1, result);
    }

    @Test
    void test_Login_ManagerDoesNotExist_ThrowsManagerDoesNotExistException(){
        
        assertThrows(ManagerNotFoundException.class, () -> {service.login(manager3.getUser().getEmail());});

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
    void test_Register_ManagerExists_ThrowsManagerAlreadyExistsException(){
        
        assertThrows(ManagerAlreadyExistsException.class, () -> {service.register(dto1);});

    }

    @Test
    void test_InsertManager_ManagerDoesNotExist_ReturnsCorrectManager() throws ManagerAlreadyExistsException{
        
        Mockito.when(userRepository.saveAndFlush(any(User.class))).thenReturn(user3);
        Mockito.when(managerRepository.saveAndFlush(any(Manager.class))).thenReturn(manager3);

        Manager result = service.register(dto3);

        assertEquals(manager3, result);
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