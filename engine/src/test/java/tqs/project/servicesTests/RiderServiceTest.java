package tqs.project.servicesTests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;
import org.mockito.junit.jupiter.MockitoExtension;

import tqs.project.model.Rider;
import tqs.project.repositories.RiderRepository;
import tqs.project.service.RiderService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class RiderServiceTest {

    List<Rider> riders;

    @InjectMocks
    private RiderService service;

    @Mock
    private RiderRepository rep;

    @BeforeEach
    void setUp() {
        this.riders = Arrays.asList(new Rider(), new Rider());
    }

    @Test
    void getAllData() {
        when(rep.findAll()).thenReturn(this.riders);

        List<Rider> found = service.getAllData();

        verify(rep, times(1)).findAll();
        assertEquals(found, this.riders);
    }
}
