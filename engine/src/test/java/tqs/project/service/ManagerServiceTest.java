package tqs.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.datamodels.RegisterDTO;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Manager;
import tqs.project.model.User;
import tqs.project.repository.ManagerRepository;
import tqs.project.repository.UserRepository;
import tqs.project.service.ManagerService;
import tqs.project.service.UserService;

@ExtendWith(MockitoExtension.class)
class ManagerServiceTest {

    Manager manager;

    @InjectMocks
    private ManagerService service;

    @Mock
    private ManagerRepository rep;

    @BeforeEach
    void setUp() {
        User user = new User("Filipe", "filipeg@ua.pt", "filipe");
        this.manager = new Manager();
        this.manager.setUser(user);
    }


    @Test
    void testGetUser() {
        when(rep.findByUserEmail("filipeg@ua.pt")).thenReturn(this.manager);

        Manager found = service.getUser("filipeg@ua.pt");

        verify(rep, times(1)).findByUserEmail(anyString());
        assertEquals(found, this.manager);
    }

    @Test
    void testRegisterValid() throws UserAlreadyExistsException {
        when(rep.findByUserEmail("filipeg@ua.pt")).thenReturn(null);

        RegisterDTO user = new RegisterDTO("Filipe", "filipeg@ua.pt", "filipe");

        service.register(user);

        verify(rep, times(1)).findByUserEmail(anyString());
        verify(rep, times(1)).save(any());
    }

    @Test
    void testRegisterInvalid() {
        when(rep.findByUserEmail("filipeg@ua.pt")).thenReturn(this.manager);

        RegisterDTO user = new RegisterDTO("Filipe", "filipeg@ua.pt", "filipe");

        assertThrows(UserAlreadyExistsException.class, () -> {service.register(user);});

        verify(rep, times(1)).findByUserEmail(anyString());
        verify(rep, times(0)).save(any());
    }
}