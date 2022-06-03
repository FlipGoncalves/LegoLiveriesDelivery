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

import tqs.project.model.Order;
import tqs.project.repositories.OrderRepository;
import tqs.project.service.OrderService;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    List<Order> orders;

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository rep;

    @BeforeEach
    void setUp() {
        this.orders = Arrays.asList(new Order(), new Order());
    }

    @Test
    void getAllData() {
        when(rep.findAll()).thenReturn(this.orders);

        List<Order> found = service.getAllOrders();

        verify(rep, times(1)).findAll();
        assertEquals(found, this.orders);
    }
}
