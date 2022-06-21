package restapi.tqs.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import restapi.tqs.Models.Client;
import restapi.tqs.Models.User;

@DataJpaTest
class ClientRepoTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private UserRepository userRepository;

    Client client1;
    User user1;

    @BeforeEach
    void setUp(){

        user1 = createUser(1);

        user1 = entityManager.persistAndFlush(user1);

        client1 = new Client();

        client1.setUser(user1);

        client1 = entityManager.persistAndFlush(client1);

        user1.setClient(client1);

    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByUserEmail_ValidEmail_ReturnsCorrectUser(){
        Optional<Client> result = clientRepository.findByUserEmail(user1.getEmail());

        assertTrue(result.isPresent());
        assertEquals(client1, result.get());  
    }

    @Test
    void test_FindByUserEmail_InvalidEmail_ReturnsEmptyOptional(){
        Optional<Client> result = clientRepository.findByUserEmail("Not a user email");

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
