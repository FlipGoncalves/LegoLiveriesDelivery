package tqs.project.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import tqs.project.datamodels.AddressDTO;
import tqs.project.datamodels.OrderDTO;
import tqs.project.exceptions.InvalidStatusException;
import tqs.project.exceptions.OrderNotFoundException;
import tqs.project.exceptions.OrderNotUpdatedException;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.RiderRepository;
import tqs.project.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @InjectMocks
    private OrderService service;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private RiderRepository riderRepository;

    public static MockWebServer mockBackEnd;

    Order order1, order2, order3;
    Store store1, store2; 
    Address address1, address2;
    AddressDTO addressDTO1, addressDTO2;

    @BeforeAll
    static void allSetUp() throws IOException{
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @BeforeEach
    void setUp() {

        String baseUrl = String.format("http://localhost:%s", mockBackEnd.getPort());
        service.setEngineURL(baseUrl);

        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);

        store1 = new Store(1, "Store 1");
        store2 = new Store(2, "Store 2");

        order1 = new Order("Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order1.setStore(store1);
        order1.setAddress(address1);

        store1.getOrders().add(order1);
        address1.getOrders().add(order1);

        order2 = new Order("Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order2.setStore(store1);
        order2.setAddress(address2);

        store1.getOrders().add(order2);
        address2.getOrders().add(order2);

        order3 = new Order("Client 3", new Date(), 3, 4);
        order3.setStatus(2);
        order3.setStore(store2);
        order3.setAddress(address1);
        
        store2.getOrders().add(order3);
        address1.getOrders().add(order3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);
    }

    @AfterAll
    static void cleanUp() throws IOException{
        mockBackEnd.shutdown();
    }

    @Test
    void test_getAllOrders_ReturnsCorrectOrders() {
        when(orderRepository.findAll()).thenReturn(new ArrayList<Order>(Arrays.asList(order1,order2,order3)));

        List<Order> result = service.getAllOrders();

        assertTrue(!result.isEmpty());
        assertEquals(3, result.size());
        assertTrue(result.contains(order1));
        assertTrue(result.contains(order2));
        assertTrue(result.contains(order3));
    }

    @Test
    void test_getAllOrdersByStatus_ExistStatus_ReturnsCorrectOrders(){

        when(orderRepository.findByStatus(2)).thenReturn(new ArrayList<>(Arrays.asList(order2, order3)));

        List<Order> response = service.getAllOrdersByStatus(2);
        
        assertTrue(response != null);
        assertEquals(2, response.size());
        assertTrue(!response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(response.contains(order3));
    }

    @Test
    void test_getAllOrdersByStatus_DoesNotExistStatus_ReturnsCorrectOrders(){

        when(orderRepository.findByStatus(4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStatus(4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreId_StoreExists_ReturnsCorrectOrders(){

        when(orderRepository.findByStoreStoreId(1)).thenReturn(new ArrayList<>(Arrays.asList(order1, order2)));

        List<Order> response = service.getAllOrdersByStoreId(1);
        
        assertTrue(response != null);
        assertEquals(2, response.size());
        assertTrue(response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(!response.contains(order3));
    }

    @Test
    void test_getAllOrdersByStoreId_StoreDoesNotExists_ReturnsEmptyList(){

        when(orderRepository.findByStoreStoreId(4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreId(4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StoreAndStatusExists_ReturnsCorrectOrders(){

        when(orderRepository.findByStoreStoreIdAndStatus(1,2)).thenReturn(new ArrayList<>(Arrays.asList(order2)));

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(1,2);
        
        assertTrue(response != null);
        assertEquals(1, response.size());
        assertTrue(!response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(!response.contains(order3));
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StoreExistsButStatusDoesNotExist_ReturnsEmptyList(){

        when(orderRepository.findByStoreStoreIdAndStatus(1,4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(1,4);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StatusExistsButStoreDoesNotExist_ReturnsEmptyList(){

        when(orderRepository.findByStoreStoreIdAndStatus(5,2)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(5,2);
        
        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_getAllOrdersByStoreIdAndStatus_StoreAndStatusDoNotExist_ReturnsEmptyList(){

        when(orderRepository.findByStoreStoreIdAndStatus(5,4)).thenReturn(new ArrayList<>());

        List<Order> response = service.getAllOrdersByStoreIdAndStatus(5,4);

        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_MakeOrder_StoreDoesNotExist_ThrowsStoreNotFoundException(){

        OrderDTO orderDTO = new OrderDTO("Client 1", 2100, "Not a Store", addressDTO1);

        when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(new Order());
        when(storeRepository.findByName(orderDTO.getStoreName())).thenReturn(Optional.empty());

        assertThrows(StoreNotFoundException.class, () -> {service.makeOrder(orderDTO);});
    }

    @Test
    void test_MakeOrder_ValidOrderDTO_ReturnsCorrectId() throws StoreNotFoundException{
        
        OrderDTO orderDTO = new OrderDTO("Client 1", 2100, "Store 1", addressDTO1);

        Order order = new Order();
        order.setOrderId(1);

        when(orderRepository.saveAndFlush(any(Order.class))).thenReturn(order);
        when(storeRepository.findByName(orderDTO.getStoreName())).thenReturn(Optional.of(store1));
        when(addressRepository.findByLatitudeAndLongitude(orderDTO.getAddress().getLatitude(), orderDTO.getAddress().getLongitude())).thenReturn(Optional.of(address1));

        Order result = service.makeOrder(orderDTO);

        assertNotNull(result);
        assertEquals(1, result.getOrderId());
        assertEquals("Client 1", result.getClientName());
        assertEquals(2100, result.getTimeOfDelivery());
        assertEquals(address1, result.getAddress());
        assertEquals(store1, result.getStore());
        assertEquals(0, result.getStatus());
    }

    @Test
    void test_UpdatedOrderStatus_InvalidStatus_ThrowsInvalidStatusException() throws InvalidStatusException, OrderNotFoundException, OrderNotUpdatedException{

        assertThrows(InvalidStatusException.class, () -> {service.updateOrderStatus(1, 3);});

    }

    @Test
    void test_UpdatedOrderStatus_OrderDoesNotExist_ThrowsOrderNotFoundException() throws InvalidStatusException, OrderNotFoundException, OrderNotUpdatedException{

        when(orderRepository.findById(6L)).thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> {service.updateOrderStatus(6, 2);});

    }

    @Test
    void test_UpdateOrderStatus_ValidArguments_ReturnsCorrectOrder() throws InvalidStatusException, OrderNotFoundException, OrderNotUpdatedException{

        when(orderRepository.findById(1l)).thenReturn(Optional.of(order1));

        mockBackEnd.enqueue(new MockResponse().setResponseCode(200));

        Order order = service.updateOrderStatus(1, 2);

        assertNotNull(order);
        assertEquals(2, order.getStatus());
    }

    @Test
    void test_SetRider_ReturnsCorrectOrder(){

        Rider rider1, rider2, rider3;
        List<Rider> riders = new ArrayList<>();

        rider1 = new Rider();
        rider2 = new Rider();
        rider3 = new Rider();
        
        riders.add(rider1);
        riders.add(rider2);
        riders.add(rider3);

        when(riderRepository.findAll()).thenReturn(riders);
        when(orderRepository.getById(1L)).thenReturn(order1);

        Order result = service.setRider(1L);

        assertTrue(riders.contains(result.getRider()));
    }

    Address buildAddressObject(long id){
        Address address = new Address();
        address.setAddressId(id);
        address.setLongitude(100 + id);
        address.setLatitude(50 + id);
        address.setStreet("Street " + id);
        address.setPostalCode("3810-24" + id);
        address.setCity("city " + id);
        address.setCountry("Country " + id);
        return address;
    }

    AddressDTO buildAddressDTO(long id){
        AddressDTO address = new AddressDTO();
        address.setLongitude(100 + id);
        address.setLatitude(50 + id);
        address.setStreet("Street " + id);
        address.setPostalCode("3810-24" + id);
        address.setCity("city " + id);
        address.setCountry("Country " + id);
        return address;
    }
}
