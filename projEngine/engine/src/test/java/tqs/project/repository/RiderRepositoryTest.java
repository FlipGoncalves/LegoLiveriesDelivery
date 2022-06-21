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

import tqs.project.model.Rider;
import tqs.project.model.User;

@DataJpaTest
class RiderRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private RiderRepository riderRepository;

    @Autowired 
    UserRepository userRepository;

    User user1;
    Rider rider1;

    @BeforeEach
    void setUp(){
        user1 = createUser(1);

        user1 = userRepository.saveAndFlush(user1);

        rider1 = new Rider();

        rider1.setUser(user1);

        rider1 = riderRepository.saveAndFlush(rider1);

        user1.setRider(rider1);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByUserEmail_ValidEmail_ReturnsCorrectManager(){
        Optional<Rider> result = riderRepository.findByUserEmail(user1.getEmail());

        assertTrue(result.isPresent());
        assertEquals(rider1, result.get());  
    }

    @Test
    void test_FindByUserEmail_InvalidEmail_ReturnsEmptyOptional(){
        Optional<Rider> result = riderRepository.findByUserEmail("Not a user email");

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