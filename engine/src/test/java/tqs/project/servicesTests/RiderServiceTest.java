package tqs.project.servicesTests;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.datamodels.RiderDTO;
import tqs.project.exceptions.RiderAlreadyExistsException;
import tqs.project.exceptions.RiderNotFoundException;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.repositories.RiderRepository;
import tqs.project.repositories.UserRepository;
import tqs.project.service.RiderService;

@ExtendWith(MockitoExtension.class)
class RiderServiceTest {

    @InjectMocks
    private RiderService service;

    @Mock
    private RiderRepository riderRep;

    @Mock
    private UserRepository userRep;

    @Test
    void test_GetAllRiders_ReturnsCorrectRiders() {
        List<Rider> riders = Arrays.asList(new Rider(), new Rider());

        when(riderRep.findAll()).thenReturn(riders);

        List<Rider> found = service.getAllRiders();

        verify(riderRep, times(1)).findAll();
        assertEquals(found, riders);
    }

    @Test
    void test_CreateOrGetUser_UserExists_GetsUser(){

        User user = new User("Rider 1", "rider1@gmail.com", "rider1pass");

        RiderDTO dto = new RiderDTO("Rider 1", "rider1@gmail.com", "rider1pass", 5, 5);

        when(userRep.findByEmail(dto.getEmail())).thenReturn(Optional.of(user));

        User result = service.createOrGetUser(dto);

        assertEquals(user, result);

    }

    @Test
    void test_CreateOrGetUser_UserDoesNotExist_CreatesAndSavesUser(){

        User user = new User("Rider 1", "rider1@gmail.com", "rider1pass");

        RiderDTO dto = new RiderDTO("Rider 1", "rider1@gmail.com", "rider1pass", 5, 5);

        when(userRep.findByEmail(dto.getEmail())).thenReturn(Optional.empty());

        when(userRep.saveAndFlush(any(User.class))).thenReturn(user);

        User result = service.createOrGetUser(dto);

        assertEquals(user, result);

    }

    @Test
    void test_InsertRider_ValidRider_WithNumRev_ReturnsCorrectRider() throws UserAlreadyExistsException, RiderAlreadyExistsException{

        RiderDTO request = new RiderDTO("New User", "user@gmail.com", "password", 10, 30);
        
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("New User");


        Rider rider = new Rider(30,10);
        rider.setUser(user);

        
        when(userRep.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        when(riderRep.saveAndFlush(any(Rider.class))).thenReturn(rider);

        Rider response = service.insertRider(request);

        assertEquals(user.getUsername(), response.getUser().getUsername());
        assertEquals(user.getEmail(), response.getUser().getEmail());
        assertEquals(request.getNumRev(), response.getTotalReviews());
        assertEquals(request.getSumRev(), response.getReviewSum());

    }

    @Test
    void test_InsertRider_ValidRider_WithoutNumRev_ReturnsCorrectRider() throws UserAlreadyExistsException, RiderAlreadyExistsException{
        int numRev = 25;
        int totalRev = 5;

        RiderDTO request = new RiderDTO();
        request.setEmail("user@gmail.com");
        request.setUsername("New User");
        request.setPassword("password");
        
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("New User");

        Rider rider = new Rider(numRev,totalRev); //The reviewSum and totalReviews here are random numbers that would be created in the service
        rider.setUser(user);
        
        when(userRep.findByEmail("user@gmail.com")).thenReturn(Optional.of(user));
        when(riderRep.saveAndFlush(any(Rider.class))).thenReturn(rider);

        Rider response = service.insertRider(request);

        assertEquals(user.getUsername(), response.getUser().getUsername());
        assertEquals(user.getEmail(), response.getUser().getEmail());
        assertEquals(totalRev, response.getTotalReviews());
        assertEquals(numRev, response.getReviewSum());

    }

    @Test
    void test_InsertRider_ClientAlreadyExists_ThrowsClientAlreadyExistsException(){

        int numRev = 25;
        int totalRev = 5;

        RiderDTO request = new RiderDTO();
        request.setEmail("user@gmail.com");
        request.setUsername("Existing User");
        request.setPassword("password");
        
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("Existing User");

        Rider rider = new Rider(numRev,totalRev); //The reviewSum and totalReviews here are random numbers that would be created in the service
        rider.setUser(user);
        user.setRider(rider);
        
        when(riderRep.findByUserEmail(request.getEmail())).thenReturn(Optional.of(rider));
        
        assertThrows(RiderAlreadyExistsException.class, () -> {service.insertRider(request);});
    }
}