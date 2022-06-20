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

import tqs.project.model.Store;
import tqs.project.repository.StoreRepository;

@DataJpaTest
public class StoreRepositoryTest {
    
    @Autowired
    TestEntityManager entityManager;

    @Autowired
    StoreRepository storeRep;

    Store store1, store2;

    @BeforeEach
    void setUp(){
        store1 = new Store();
        store1.setName("Store 1");
        store2 = new Store();
        store2.setName("Store 2");

        store1 = storeRep.saveAndFlush(store1);
        store2 = storeRep.saveAndFlush(store2);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindByName_ValidName_ReturnsCorrectStore(){
        
        Optional<Store> response = storeRep.findByName("Store 1");

        assertTrue(response.isPresent());
        assertEquals(store1, response.get());
    }

    @Test
    void test_FindByName_InvalidName_ReturnsEmptyOptional(){
        
        Optional<Store> response = storeRep.findByName("Not a Store");

        assertTrue(response.isEmpty());
    }
}
