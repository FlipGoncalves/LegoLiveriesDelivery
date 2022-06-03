package tqs.project.repositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
    
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tqs.project.repositories.OrderRepository;
import tqs.project.model.Order;

@RunWith(SpringRunner.class)
@DataJpaTest
public class OrderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private OrderRepository rep;

    @Test
    public void findByClientNameTest() {
        Order data = new Order(1, "filipe", new Date(), 1, 5);

        entityManager.persistAndFlush(data);

        Order data_get = rep.findByClientName("filipe");

        assertThat(data_get).isNotNull();
        assertEquals(data, data_get);
    }
}