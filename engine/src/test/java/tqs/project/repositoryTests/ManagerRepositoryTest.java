package tqs.project.repositoryTests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import tqs.project.repositories.ManagerRepository;
import tqs.project.model.Manager;
import tqs.project.model.User;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ManagerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ManagerRepository rep;

    @Test
    public void findByNameTest() {
        User data = new User("Filipe", "filipeg@ua.pt", "filipefilipe");
        Manager manager = new Manager();
        manager.setUser(data);

        entityManager.persistAndFlush(manager);

        Manager data_get = rep.findByUserEmail("filipeg@ua.pt");

        assertThat(data_get).isNotNull();
        assertEquals(manager, data_get);
    }
}