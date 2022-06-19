package tqs.project.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tqs.project.model.User;
import tqs.project.repository.UserRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository rep;

    @Test
    public void findByNameTest() {
        User data = new User("Filipe", "filipeg@ua.pt", "filipefilipe");

        entityManager.persistAndFlush(data);

        User data_get = rep.findByEmail("filipeg@ua.pt");

        assertThat(data_get).isNotNull();
        assertEquals(data, data_get);
    }

    @Test
    public void findByIDTest() {
        User data = new User("Filipe", "filipeg@ua.pt", "filipefilipe");

        entityManager.persistAndFlush(data);

        Optional<User> data_get = rep.findById(data.getUserId());

        assertThat(data_get).isNotNull();
        assertEquals(data, data_get.get());
    }
}