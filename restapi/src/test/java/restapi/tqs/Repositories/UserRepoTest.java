package restapi.tqs.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.Models.Address;
import restapi.tqs.Models.Lego;
import restapi.tqs.Models.User;

@DataJpaTest
public class UserRepoTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    User user1;

    @BeforeEach
    void setUp(){
        user1 = createUser(1);

        entityManager.persistAndFlush(user1);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByEmail_ValidEmail_ReturnCorrectUser(){
        Optional<User> result = userRepository.findByEmail(user1.getEmail());

        assertTrue(result.isPresent());
        assertEquals(user1, result.get());
    }

    @Test
    void test_FindByEmail_InvalidEmail_ReturnEmptyOptional(){
        Optional<User> result = userRepository.findByEmail("Not an email");

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
