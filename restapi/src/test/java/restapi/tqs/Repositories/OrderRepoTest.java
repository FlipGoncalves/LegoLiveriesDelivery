package restapi.tqs.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Pageable;

import restapi.tqs.Models.Address;
import restapi.tqs.Models.Client;
import restapi.tqs.Models.Lego;
import restapi.tqs.Models.Order;
import restapi.tqs.Models.OrderLego;
import restapi.tqs.Models.User;

@DataJpaTest
class OrderRepoTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository orderRepository;

    Order order1, order2, order3;
    Client client1, client2;
    User user1, user2;

    @BeforeEach
    void setUp(){
        List<Object> array1 = buildUserAndClientObject(1);
        user1 = (User) array1.get(0);
        client1 = (Client) array1.get(1);

        List<Object> array2 = buildUserAndClientObject(2);
        user2 = (User) array2.get(0);
        client2 = (Client) array2.get(1);

        order1 = buildAndSaveOrderObject(client1, 1);
        order2 = buildAndSaveOrderObject(client1, 1);
        order3 = buildAndSaveOrderObject(client2, 2);

        Set<Order> orders = client1.getOrders();
        orders.add(order1);
        client1.setOrders(orders);

        orders = client1.getOrders();
        orders.add(order2);
        client1.setOrders(orders);

        orders = client2.getOrders();
        orders.add(order3);
        client2.setOrders(orders);
        entityManager.persist(user1);
        entityManager.persist(client1);
        entityManager.persist(user2);
        entityManager.persist(client2);
        entityManager.flush();
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByValidId_ReturnsCorrectOrder() {
        Optional<Order> order = orderRepository.findById( order1.getOrderId() );

        assertTrue(order.isPresent());
        assertEquals(order1.getAddress(), order.get().getAddress());
        assertEquals(order1.getClient(), order.get().getClient());
        assertEquals(order1.getTotalPrice(), order.get().getTotalPrice());
        
    }

    @Test
    void test_FindByInvalidId_ReturnsEmptyOptionalOrder(){
        Optional<Order> order = orderRepository.findById( 300l );
        assertTrue(order.isEmpty());
    }


    @Test
    void test_FindAllByClientWithValidId_ReturnsCorrectOrders(){
        List<Order> orders = orderRepository.findAllByClient(client1, Pageable.unpaged());
        assertTrue(!orders.isEmpty());
        assertEquals(2, orders.size());
        assertTrue(orders.contains(order1));
        assertTrue(orders.contains(order2));
        assertTrue(!orders.contains(order3));
    }

    @Test
    void test_FindAllByClientWithInvalidId_ReturnsEmpty(){
        List<Object> array = buildUserAndClientObject(1);
        User user = (User) array.get(0);
        Client client = (Client) array.get(1);

        entityManager.persist(user);
        entityManager.persist(client);
        entityManager.flush();
        List<Order> orders = orderRepository.findAllByClient(client, Pageable.unpaged());
        assertTrue(orders.isEmpty());
    }

    Order buildAndSaveOrderObject(Client client, long id){

        Order order = new Order();
        
        Address address = buildAddressObject(id);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 6, (int) id);
        Date date = calendar.getTime();

        double totalPrice = 0;

        Set<OrderLego> orderLegos = buildOrderLegoList(id);

        for (OrderLego orderLego : orderLegos) {
            totalPrice += orderLego.getPrice() * orderLego.getQuantity();
        }


        order.setClient(client);
        order.setAddress(address);
        order.setDate(date);
        order.setScheduledTimeOfDelivery(2100);
        order.setRiderName("Paulo " + id);
        order.setOrderLego(orderLegos);
        order.setTotalPrice(totalPrice);

        entityManager.persist(client);
        entityManager.persist(address);
        
        entityManager.persist(order);

        return order;
    }

    Lego buildLegoObject(String name, double price, String imageUrl){
        Lego lego =  new Lego();
        lego.setName(name);
        lego.setImageUrl(imageUrl);
        lego.setPrice(price);
        return lego;
    }
    
    ArrayList<Object> buildUserAndClientObject(long id){
        User user = new User();
        Client client = new Client();
        user.setUsername("Person " + id);
        user.setClient(client);
        client.setUser(user);
        ArrayList<Object> array = new ArrayList<>();
        array.add(user);
        array.add(client);
        return array;
    }

    Address buildAddressObject(long id){
        Address address = new Address();
        address.setCountry("Country " + id);
        return address;
    }

    Set<OrderLego> buildOrderLegoList(long id){
        Lego lego1 = buildLegoObject("Lego1 " + id, 10 + id, "URL1 " + id);
        Lego lego2 = buildLegoObject("Lego2 " + id, 20 + id, "URL2 " + id);
        Lego lego3 = buildLegoObject("Lego3 " + id, 30 + id, "URL3 " + id);

        OrderLego orderLego1 = new OrderLego();
        orderLego1.setLego(lego1);
        orderLego1.setPrice(lego1.getPrice());
        orderLego1.setQuantity(1 + (int) id);
        OrderLego orderLego2 = new OrderLego();
        orderLego1.setLego(lego2);
        orderLego1.setPrice(lego2.getPrice());
        orderLego1.setQuantity(2 + (int) id);
        OrderLego orderLego3 = new OrderLego();
        orderLego1.setLego(lego3);
        orderLego1.setPrice(lego3.getPrice());
        orderLego1.setQuantity(3 + (int) id);

        Set<OrderLego> orderLegos = new HashSet<>();
        orderLegos.add(orderLego1);
        orderLegos.add(orderLego2);
        orderLegos.add(orderLego3);

        entityManager.persist(lego1);
        entityManager.persist(lego2);
        entityManager.persist(lego3);

        return orderLegos;
    }
}
