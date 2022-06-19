package tqs.project.service;

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
import tqs.project.model.Store;
import tqs.project.repository.OrderRepository;
import tqs.project.service.OrderService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository rep;

    List<Order> orders;
    Order order1, order2, order3;
    Store store1, store2;

    @BeforeEach
    void setUp() {
        order1 = new Order("Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order2 = new Order("Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order3 = new Order("Client 3", new Date(), 3, 4);
        order3.setStatus(2);

        store1 = new Store(1, "Store 1");
        store2 = new Store(2, "Store 2");

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);

        orders = new ArrayList<>();
        orders.add(order1);
        orders.add(order2);
        orders.add(order3);
    }

    @Test
    void test_getAllOrders_ReturnsCorrectOrders() {
        when(rep.findAll()).thenReturn(orders);

        List<Order> response = service.getAllOrders();

        verify(rep, times(1)).findAll();
        assertEquals(response, orders);
    }

    @Test
    void test_getAllOrdersByStatus_ExistStatus_ReturnsCorrectOrders(){

        when(rep.findByStatus(2)).thenReturn(new ArrayList<>(Arrays.asList(order2, order3)));

        List<Order> response = service.getAllOrdersByStatus(2);

        verify(rep, times(1)).findByStatus(2);
        
        assertTrue(response != null);
        assertEquals(2, response.size());
        assertTrue(!response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(response.contains(order3));
    }

    @Test
    void test_getAllOrdersByStatus_DoesNotExistStatus_ReturnsCorrectOrders(){

        when(rep.findByStatus(4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStatus(4);

        verify(rep, times(1)).findByStatus(4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreId_StoreExists_ReturnsCorrectOrders(){

        when(rep.findByStoreStoreId(1)).thenReturn(new ArrayList<>(Arrays.asList(order1, order2)));

        List<Order> response = service.getAllOrdersByStoreId(1);

        verify(rep, times(1)).findByStoreStoreId(1);
        
        assertTrue(response != null);
        assertEquals(2, response.size());
        assertTrue(response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(!response.contains(order3));
    }

    @Test
    void test_getAllOrdersByStoreId_StoreDoesNotExists_ReturnsEmptyList(){

        when(rep.findByStoreStoreId(4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreId(4);

        verify(rep, times(1)).findByStoreStoreId(4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StoreAndStatusExists_ReturnsCorrectOrders(){

        when(rep.findByStoreStoreIdAndStatus(1,2)).thenReturn(new ArrayList<>(Arrays.asList(order2)));

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(1,2);

        verify(rep, times(1)).findByStoreStoreIdAndStatus(1,2);
        
        assertTrue(response != null);
        assertEquals(1, response.size());
        assertTrue(!response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(!response.contains(order3));
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StoreExistsButStatusDoesNotExist_ReturnsEmptyList(){

        when(rep.findByStoreStoreIdAndStatus(1,4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(1,4);

        verify(rep, times(1)).findByStoreStoreIdAndStatus(1,4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StatusExistsButStoreDoesNotExist_ReturnsEmptyList(){

        when(rep.findByStoreStoreIdAndStatus(5,2)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(5,2);

        verify(rep, times(1)).findByStoreStoreIdAndStatus(5,2);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StoreAndStatusDoNotExist_ReturnsEmptyList(){

        when(rep.findByStoreStoreIdAndStatus(5,4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(5,4);

        verify(rep, times(1)).findByStoreStoreIdAndStatus(5,4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

}
