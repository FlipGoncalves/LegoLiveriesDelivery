package tqs.project.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.model.Manager;
import tqs.project.model.User;

@DataJpaTest
class ManagerRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired 
    UserRepository userRepository;

    User user1;
    Manager manager1;

    @BeforeEach
    void setUp(){
        user1 = createUser(1);

        user1 = userRepository.saveAndFlush(user1);

        manager1 = new Manager();

        manager1.setUser(user1);

        manager1 = managerRepository.saveAndFlush(manager1);

        user1.setManager(manager1);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByUserEmail_ValidEmail_ReturnsCorrectUser(){
        Optional<Manager> result = managerRepository.findByUserEmail(user1.getEmail());

        assertTrue(result.isPresent());
        assertEquals(manager1, result.get());  
    }

    @Test
    void test_FindByUserEmail_InvalidEmail_ReturnsEmptyOptional(){
        Optional<Manager> result = managerRepository.findByUserEmail("Not a user email");

        assertTrue(result.isEmpty());
    }

    User createUser(long id){
        User user = new User();
        user.setEmail("user" + id + "@gmail.com");
        user.setUsername("User " + id);
        user.setPassword("password" + id);
        return user;
    }
}