package restapi.tqs.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import restapi.tqs.DataModels.LegoDTO;
import restapi.tqs.Exceptions.BadLegoDTOException;
import restapi.tqs.Models.Lego;
import restapi.tqs.Repositories.LegoRepository;
import restapi.tqs.Service.LegoService;

@ExtendWith(MockitoExtension.class)
class LegoServiceTest {
    
    @Mock(lenient = true)
    private LegoRepository legoRepository;

    @InjectMocks
    private LegoService service;

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

        Mockito.when(legoRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(lego1, lego2, lego3)));
        Mockito.when(legoRepository.findAllByNameContainingIgnoreCase(LEGO_NAME1)).thenReturn(new ArrayList<>(Arrays.asList(lego1)));
        Mockito.when(legoRepository.findAllByNameContainingIgnoreCase("Policial")).thenReturn(new ArrayList<>(Arrays.asList(lego2, lego3)));
        Mockito.when(legoRepository.findAllByPrice(LEGO_PRICE2)).thenReturn(new ArrayList<>(Arrays.asList(lego2)));
    }

    @Test
    void test_GetAllLegos(){
        List<Lego> result = service.getLegos();

        assertTrue(!result.isEmpty());
        assertEquals(3, result.size());
        assertTrue(result.contains(lego1));
        assertTrue(result.contains(lego2));
        assertTrue(result.contains(lego3));

        verify(legoRepository, times(1)).findAll();
    }

    @Test
    void test_GetLegosByName_WithFullName(){
        List<Lego> result = service.getLegosByName(LEGO_NAME1);

        assertTrue(!result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(result.contains(lego1));
        assertTrue(!result.contains(lego2));
        assertTrue(!result.contains(lego3));

        verify(legoRepository, times(1)).findAllByNameContainingIgnoreCase(LEGO_NAME1);
    }

    @Test
    void test_GetLegosByName_WithParcialName(){
        List<Lego> result = service.getLegosByName("Policial");

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(!result.contains(lego1));
        assertTrue(result.contains(lego2));
        assertTrue(result.contains(lego3));
    }

    @Test
    void test_GetLegosByPrice(){
        List<Lego> result = service.getLegosByPrice(LEGO_PRICE2);

        assertTrue(!result.isEmpty());
        assertEquals(1, result.size());
        assertTrue(!result.contains(lego1));
        assertTrue(result.contains(lego2));
        assertTrue(!result.contains(lego3));

        verify(legoRepository, times(1)).findAllByPrice(LEGO_PRICE2);
    }

    @Test
    void test_InsertLego_ValidLegoDTO() throws BadLegoDTOException{

        LegoDTO legoDTO = new LegoDTO("A Lego Name", 30, "A Lego imgUrl");
        service.insertLego(legoDTO);

        verify(legoRepository, times(1)).save(any(Lego.class));
    }

    @Test
    void test_InsertLego_BlankLegoName_ThrowsBadLegoDTOException() throws BadLegoDTOException{

        LegoDTO legoDTO = new LegoDTO(" ", 30, "A Lego imgUrl");
        
        assertThrows(BadLegoDTOException.class, () -> {service.insertLego(legoDTO);});
    }

    @Test
    void test_InsertLego_LegoPriceLessorEqualToZero_ThrowsBadLegoDTOException() throws BadLegoDTOException{

        LegoDTO legoDTO = new LegoDTO("A Lego Name", 0, "A Lego imgUrl");
        
        assertThrows(BadLegoDTOException.class, () -> {service.insertLego(legoDTO);});

    }

    @Test
    void test_InsertLego_BlankLegoImgUrl_ThrowsBadLegoDTOException() throws BadLegoDTOException{

        LegoDTO legoDTO = new LegoDTO("A Lego Name", 30, "  ");
        
        assertThrows(BadLegoDTOException.class, () -> {service.insertLego(legoDTO);});

    }

    Lego buildLegoObject(String name, double price, String imageUrl){
        Lego lego =  new Lego();
        lego.setName(name);
        lego.setImageUrl(imageUrl);
        lego.setPrice(price);
        return lego;
    }
}
