package tqs.project.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.repositories.RiderRepository;
import tqs.project.repositories.UserRepository;
import tqs.project.service.RiderService;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RiderServiceTest {

    List<Rider> riders;

    @InjectMocks
    private RiderService service;

    @Mock
    private RiderRepository riderRep;

    @Mock
    private UserRepository userRep;

    @BeforeEach
    void setUp() {
        this.riders = Arrays.asList(new Rider(), new Rider());
    }

    @Test
    void getAllData_ReturnsCorrectRiders() {
        when(riderRep.findAll()).thenReturn(this.riders);

        List<Rider> found = service.getAllData();

        verify(riderRep, times(1)).findAll();
        assertEquals(found, riders);
    }

    @Test
    void insertRider_ValidRider_WithNumRev_ReturnsCorrectRider() throws UserAlreadyExistsException{
        Map<String, Object> request = new HashMap<>();

        request.put("name", "New User");
        request.put("email", "user@gmail.com");
        request.put("password", "password");
        request.put("numRev", "10");
        request.put("sumRev", "30");
        
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("New User");

        Rider rider = new Rider(30,10);
        rider.setUser(user);

        
        when(userRep.findByEmail("user@gmail.com")).thenReturn(null);
        when(riderRep.save(any(Rider.class))).thenReturn(rider);

        Rider response = service.insertRider(request);

        verify(userRep, times(1)).findByEmail("user@gmail.com");
        verify(riderRep, times(1)).save(any(Rider.class));

        assertEquals(user.getUsername(), response.getUser().getUsername());
        assertEquals(user.getEmail(), response.getUser().getEmail());
        assertEquals(Integer.parseInt((String)request.get("numRev")), response.getTotalReviews());
        assertEquals(Integer.parseInt((String)request.get("sumRev")), response.getReviewSum());

    }

    @Test
    void insertRider_ValidRider_WithoutNumRev_ReturnsCorrectRider() throws UserAlreadyExistsException{
        Map<String, Object> request = new HashMap<>();

        int numRev = 25;
        int totalRev = 5;

        request.put("name", "New User");
        request.put("email", "user@gmail.com");
        request.put("password", "password");
        
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("New User");

        Rider rider = new Rider(numRev,totalRev); //The reviewSum and totalReviews here are random numbers that would be created in the service
        rider.setUser(user);
        
        when(userRep.findByEmail("user@gmail.com")).thenReturn(null);
        when(riderRep.save(any(Rider.class))).thenReturn(rider);

        Rider response = service.insertRider(request);

        verify(userRep, times(1)).findByEmail("user@gmail.com");
        verify(riderRep, times(1)).save(any(Rider.class));

        assertEquals(user.getUsername(), response.getUser().getUsername());
        assertEquals(user.getEmail(), response.getUser().getEmail());
        assertEquals(totalRev, response.getTotalReviews());
        assertEquals(numRev, response.getReviewSum());

    }

    @Test
    void insertRider_UserAlreadyExists_ThrowsUserAlreadyExistsException(){
        Map<String, Object> request = new HashMap<>();

        int numRev = 25;
        int totalRev = 5;

        request.put("name", "New User");
        request.put("email", "user@gmail.com");
        request.put("password", "password");
        
        User user = new User();
        user.setEmail("user@gmail.com");
        user.setUsername("New User");

        Rider rider = new Rider(numRev,totalRev); //The reviewSum and totalReviews here are random numbers that would be created in the service
        rider.setUser(user);
        
        when(userRep.findByEmail("user@gmail.com")).thenReturn(user);
        
        assertThrows(UserAlreadyExistsException.class, () -> {service.insertRider(request);});
        verify(userRep, times(1)).findByEmail("user@gmail.com");
        verify(riderRep, times(0)).save(any(Rider.class));
    }
}
