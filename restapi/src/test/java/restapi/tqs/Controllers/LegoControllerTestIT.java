package restapi.tqs.Controllers;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.hamcrest.Matchers.*;

import restapi.tqs.DataModels.LegoDTO;
import restapi.tqs.Models.Lego;
import restapi.tqs.Repositories.LegoRepository;

import restapi.tqs.TqsApplication;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = TqsApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
public class LegoControllerTestIT {
    
    @Autowired
    MockMvc mvc;

    @Autowired
    LegoRepository legoRepository;

    @Autowired
    ObjectMapper objectMapper;

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
    List<Lego> all_legos;

    @BeforeEach
    void setUp(){
        lego1 = buildLegoObject(LEGO_NAME1, LEGO_PRICE1, LEGO_IMAGEURL1);
        lego2 = buildLegoObject(LEGO_NAME2, LEGO_PRICE2, LEGO_IMAGEURL2);
        lego3 = buildLegoObject(LEGO_NAME3, LEGO_PRICE3, LEGO_IMAGEURL3);

        lego1 = legoRepository.save(lego1);
        lego2 = legoRepository.save(lego2);
        lego3 = legoRepository.save(lego3);
        legoRepository.flush();

        all_legos = new ArrayList<>();
        all_legos.add(lego1);
        all_legos.add(lego2);
        all_legos.add(lego3);

    }

    @AfterEach
    void cleanUp(){
        legoRepository.deleteAll();
    }

    @Test
    void test_GetAllData_ReturnsCorrectLegos() throws Exception{

        mvc.perform(get("/lego/all_legos")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(3)))
        .andExpect(jsonPath("$[0].name", is(LEGO_NAME1)))
        .andExpect(jsonPath("$[0].price", is(LEGO_PRICE1)))
        .andExpect(jsonPath("$[0].imageUrl", is(LEGO_IMAGEURL1)))
        .andExpect(jsonPath("$[1].name", is(LEGO_NAME2)))
        .andExpect(jsonPath("$[1].price", is(LEGO_PRICE2)))
        .andExpect(jsonPath("$[1].imageUrl", is(LEGO_IMAGEURL2)))
        .andExpect(jsonPath("$[2].name", is(LEGO_NAME3)))
        .andExpect(jsonPath("$[2].price", is(LEGO_PRICE3)))
        .andExpect(jsonPath("$[2].imageUrl", is(LEGO_IMAGEURL3)));
    }

    @Test
    void test_GetLegoByName_FullName_ReturnsCorrectLego() throws Exception{

        mvc.perform(get("/lego/get_lego/name")
        .param("name","Monster Jam Megalodon - 42134")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is(LEGO_NAME1)))
        .andExpect(jsonPath("$[0].price", is(LEGO_PRICE1)))
        .andExpect(jsonPath("$[0].imageUrl", is(LEGO_IMAGEURL1)));
    }

    @Test
    void test_GetLegoByName_ParcialName_ReturnsCorrectLegos() throws Exception{
        
        mvc.perform(get("/lego/get_lego/name")
        .param("name","Policial")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", is(LEGO_NAME2)))
        .andExpect(jsonPath("$[0].price", is(LEGO_PRICE2)))
        .andExpect(jsonPath("$[0].imageUrl", is(LEGO_IMAGEURL2)))
        .andExpect(jsonPath("$[1].name", is(LEGO_NAME3)))
        .andExpect(jsonPath("$[1].price", is(LEGO_PRICE3)))
        .andExpect(jsonPath("$[1].imageUrl", is(LEGO_IMAGEURL3)));
    }

    @Test
    void test_GetLegoByName_InvalidName_ReturnsBadRequestStatus() throws Exception{
        
        mvc.perform(get("/lego/get_lego/name")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_GetLegoByPrice_ValidPrice_ReturnsCorrectLegos() throws Exception{
        
        mvc.perform(get("/lego/get_lego/price")
        .param("price","17.99")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].name", is(LEGO_NAME1)))
        .andExpect(jsonPath("$[0].price", is(LEGO_PRICE1)))
        .andExpect(jsonPath("$[0].imageUrl", is(LEGO_IMAGEURL1)))
        .andExpect(jsonPath("$[1].name", is(LEGO_NAME3)))
        .andExpect(jsonPath("$[1].price", is(LEGO_PRICE3)))
        .andExpect(jsonPath("$[1].imageUrl", is(LEGO_IMAGEURL3)));
    }

    @Test
    void test_GetLegoByPrice_InvalidPrice_ReturnsBadRequestStatus() throws Exception{
        
        mvc.perform(get("/lego/get_lego/price")
        .param("price","30")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());

    }

    @Test
    void test_InsertLego_ValidLegoDTO_ReturnsCorrectLego() throws Exception{

        LegoDTO legoDTO = new LegoDTO(LEGO_NAME1, LEGO_PRICE1, LEGO_IMAGEURL1);
        
        mvc.perform(post("/lego/insert_lego")
        .content(objectMapper.writeValueAsString(legoDTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.name", is(LEGO_NAME1)))
        .andExpect(jsonPath("$.price", is(LEGO_PRICE1)))
        .andExpect(jsonPath("$.imageUrl", is(LEGO_IMAGEURL1)));
    }

    @Test
    void test_InsertLego_InvalidLegoDTO_ReturnsBadRequestStatus() throws Exception{

        LegoDTO legoDTO = new LegoDTO(null, LEGO_PRICE1, LEGO_IMAGEURL1); 
        
        mvc.perform(post("/lego/insert_lego")
        .content(objectMapper.writeValueAsString(legoDTO))
        .contentType(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isBadRequest());
    }

    Lego buildLegoObject(String name, double price, String imageUrl){
        Lego lego =  new Lego();
        lego.setName(name);
        lego.setImageUrl(imageUrl);
        lego.setPrice(price);
        return lego;
    }
}
