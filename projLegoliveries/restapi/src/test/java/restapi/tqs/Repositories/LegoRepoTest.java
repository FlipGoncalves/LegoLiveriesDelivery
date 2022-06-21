package restapi.tqs.Repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import restapi.tqs.Models.Lego;

@DataJpaTest
class LegoRepoTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private LegoRepository legoRepository;

    private static final String LEGO_NAME1 = "Monster Jam Megalodon - 42134";
    private static final double LEGO_PRICE1 = 17.99;
    private static final String LEGO_IMAGEURL1 = "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dwff799435/images/col/751/7514616-frente.jpg?sw\u003d280\u0026sh\u003d280";
    private static final String LEGO_NAME2 = "Perseguição Policial no Banco - 60317";
    private static final double LEGO_PRICE2 = 99.99;
    private static final String LEGO_IMAGEURL2 = "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dwc401c218/images/col/751/7514662-frente.jpg?sw\u003d280\u0026sh\u003d280";
    private static final String LEGO_NAME3 = "Perseguição Policial de Carro dos Gelados - 60314";
    private static final double LEGO_PRICE3 = 17.99;
    private static final String LEGO_IMAGEURL3 = "https://www.continente.pt/dw/image/v2/BDVS_PRD/on/demandware.static/-/Sites-col-master-catalog/default/dw131aa979/images/col/751/7515529-frente.jpg?sw\u003d280\u0026sh\u003d280";

    Lego lego1, lego2, lego3;

    @BeforeEach
    void setUp(){

        lego1 = buildLegoObject(LEGO_NAME1, LEGO_PRICE1, LEGO_IMAGEURL1);
        lego2 = buildLegoObject(LEGO_NAME2, LEGO_PRICE2, LEGO_IMAGEURL2);
        lego3 = buildLegoObject(LEGO_NAME3, LEGO_PRICE3, LEGO_IMAGEURL3);

        entityManager.persistAndFlush(lego1);
        entityManager.persistAndFlush(lego2);
        entityManager.persistAndFlush(lego3);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_FindAll_ReturnsAllObjects(){
        List<Lego> result = legoRepository.findAll();
        assertEquals(3, result.size());
    }
    
    @Test
    void test_FindAllByName_WithFullName_ReturnCorrectObject(){
        List<Lego> result = legoRepository.findAllByNameContainingIgnoreCase(LEGO_NAME1);
        assertEquals(1, result.size());
        assertTrue(result.contains(lego1));
    }

    @Test
    void test_FindAllByPrice_ReturnsCorrectObject(){
        List<Lego> result = legoRepository.findAllByPrice(LEGO_PRICE2);
        assertEquals(1, result.size());
        assertTrue(result.contains(lego2));
    }

    @Test
    void test_FindAllByNameWithInvalidObject_ReturnsEmpty(){
        List<Lego> result = legoRepository.findAllByNameContainingIgnoreCase("Not a lego");
        assertTrue(result.isEmpty());
    }

    @Test
    void test_FindAllByPriceWithInvalidObject_ReturnsEmpty(){
        List<Lego> result = legoRepository.findAllByPrice(5.99);
        assertTrue(result.isEmpty());
    }

    @Test
    void test_FindAllByName_WithPartOfName_ReturnsCorrectObjects(){
        List<Lego> result = legoRepository.findAllByNameContainingIgnoreCase("Policial");

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(lego2));
        assertTrue(result.contains(lego3));
    }

    Lego buildLegoObject(String name, double price, String imageUrl){
        Lego lego =  new Lego();
        lego.setName(name);
        lego.setImageUrl(imageUrl);
        lego.setPrice(price);
        return lego;
    }
}

