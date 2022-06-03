package tqs.project.servicesTests;

import java.text.ParseException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

import tqs.project.model.User;
import tqs.project.model.RegisterDTO;
import tqs.project.repositories.UserRepository;
import tqs.project.service.UserService;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    User user;

    @InjectMocks
    private UserService service;

    @Mock
    private UserRepository rep;

    @BeforeEach
    void setUp() {
        this.user = new User("Filipe", "filipeg@ua.pt", "filipe");
    }


    @Test
    void testGetUser() {
        when(rep.findByEmail("filipeg@ua.pt")).thenReturn(this.user);

        User found = service.getUser("filipeg@ua.pt");

        verify(rep, times(1)).findByEmail(anyString());
        assertEquals(found, this.user);
    }

    @Test
    void testRegisterValid() {
        when(rep.findByEmail("filipeg@ua.pt")).thenReturn(null);

        RegisterDTO user = new RegisterDTO("Filipe", "filipeg@ua.pt", "filipe");

        service.register(user);

        verify(rep, times(1)).findByEmail(anyString());
        verify(rep, times(1)).save(any());
    }

    @Test
    void testRegisterInvalid() {
        when(rep.findByEmail("filipeg@ua.pt")).thenReturn(this.user);

        RegisterDTO user = new RegisterDTO("Filipe", "filipeg@ua.pt", "filipe");

        service.register(user);    

        verify(rep, times(1)).findByEmail(anyString());
        verify(rep, times(0)).save(any());
    }
}