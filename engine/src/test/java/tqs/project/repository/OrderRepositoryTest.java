package tqs.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.model.Order;
import tqs.project.model.Store;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.StoreRepository;

@DataJpaTest
class OrderRepositoryTest {

    @Autowired
    TestEntityManager entityManager;

    @Autowired
    OrderRepository orderRep;

    @Autowired
    StoreRepository storeRep;

    Order order1, order2, order3;
    Store store1, store2;
    long store1Id, store2Id, notStoreId;

    @BeforeEach
    void setUp(){
        order1 = new Order("Client 1", new Date(), 1, 5);
        order1.setStatus(0);
        order2 = new Order("Client 2", new Date(), 2, 3);
        order2.setStatus(2);
        order3 = new Order("Client 3", new Date(), 3, 4);
        order3.setStatus(2);

        store1 = new Store();
        store1.setName("Store 1");
        store2 = new Store();
        store2.setName("Store 2");

        store1 = storeRep.saveAndFlush(store1);
        store2 = storeRep.saveAndFlush(store2);

        store1Id = store1.getStoreId();
        store2Id = store2.getStoreId();

        notStoreId = 1;

        while (notStoreId == store1Id || notStoreId == store2Id){
            notStoreId++;
        }

        order1.setStore(store1);
        order2.setStore(store1);
        order3.setStore(store2);

        order1 = orderRep.saveAndFlush(order1);
        order2 = orderRep.saveAndFlush(order2);
        order3 = orderRep.saveAndFlush(order3);

    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByClientName_ClientExists_ReturnsCorrectOrder() {

        Order response = orderRep.findByClientName("Client 1");

        assertTrue(response != null);
        assertEquals(order1, response);
    }

    @Test
    void test_FindByClientName_ClientDoesNotExists_ReturnsNull(){
        
        Order response = orderRep.findByClientName("Not a Client");

        assertTrue(response == null);
    }

    @Test
    void test_FindByStatus_OrdersWithStatusExist_ReturnsCorrectOrders(){

        List<Order> response = orderRep.findByStatus(2);

        assertTrue(response != null);
        assertEquals(2, response.size());
        assertTrue(!response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(response.contains(order3));
    }

    @Test
    void test_FindByStatus_OrdersWithStatusDoesNotExist_ReturnsEmptyList(){

        List<Order> response = orderRep.findByStatus(4);

        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_FindByStoreStoreId_OrderWithStoreStoreIdExist_ReturnsCorrectOrders(){

        List<Order> response = orderRep.findByStoreStoreId(store1Id);

        assertTrue(response != null);
        assertEquals(2, response.size());
        assertTrue(response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(!response.contains(order3));
    }

    @Test
    void test_FindByStoreStoreId_OrderWithStoreStoreIdDoesNotExist_ReturnsEmptyList(){

        List<Order> response = orderRep.findByStoreStoreId(notStoreId);

        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_FindByStoreStoreIdAndStatus_OrderWithStoreStoreIdAndStatusExist_ReturnsCorrectOrders(){

        List<Order> response = orderRep.findByStoreStoreIdAndStatus(store1Id,2);

        assertTrue(response != null);
        assertEquals(1, response.size());
        assertTrue(!response.contains(order1));
        assertTrue(response.contains(order2));
        assertTrue(!response.contains(order3));
    }

    @Test
    void test_FindByStoreStoreIdAndStatus_ValidStoreIDInvalidStatus_ReturnsEmptyList(){

        List<Order> response = orderRep.findByStoreStoreIdAndStatus(store1Id,4);

        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_FindByStoreStoreIdAndStatus_InvalidStoreIDValidStatus_ReturnsEmptyList(){

        List<Order> response = orderRep.findByStoreStoreIdAndStatus(notStoreId,2);

        assertTrue(response != null);
        assertEquals(0, response.size());
    }

    @Test
    void test_FindByStoreStoreIdAndStatus_InvalidStoreIDAndStatus_ReturnsEmptyList(){

        List<Order> response = orderRep.findByStoreStoreIdAndStatus(notStoreId,4);

        assertTrue(response != null);
        assertEquals(0, response.size());
    }
}