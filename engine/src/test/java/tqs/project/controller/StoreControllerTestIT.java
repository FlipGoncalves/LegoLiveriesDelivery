package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import io.restassured.parsing.Parser;
import tqs.project.ProjectApplication;
import tqs.project.datamodels.AddressDTO;
import tqs.project.datamodels.OrderDTO;
import tqs.project.datamodels.RegisterDTO;
import tqs.project.datamodels.RiderDTO;
import tqs.project.datamodels.StoreDTO;
import tqs.project.exceptions.ManagerAlreadyExistsException;
import tqs.project.exceptions.RiderAlreadyExistsException;
import tqs.project.exceptions.StoreAlreadyExistsException;
import tqs.project.exceptions.StoreNotFoundException;
import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.Address;
import tqs.project.model.Manager;
import tqs.project.model.Order;
import tqs.project.model.Rider;
import tqs.project.model.Store;
import tqs.project.model.User;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.ManagerRepository;
import tqs.project.repository.OrderRepository;
import tqs.project.repository.RiderRepository;
import tqs.project.repository.StoreRepository;
import tqs.project.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = ProjectApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase
@Transactional
public class StoreControllerTestIT {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private StoreRepository storeRepository;

    Store store1, store2, store3, store4;
    StoreDTO storeDTO1, storeDTO2, storeDTO3, storeDTO4;
    Address address1, address2, address3, address4;
    AddressDTO addressDTO1, addressDTO2, addressDTO3, addressDTO4;

    
    @BeforeEach
    void setUp(){

        RestAssuredMockMvc.mockMvc( mvc );
        
        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);
        address3 = buildAddressObject(3);
        address4 = buildAddressObject(4);

        addressRepository.saveAndFlush(address1);
        addressRepository.saveAndFlush(address2);
        addressRepository.saveAndFlush(address3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);
        addressDTO4 = buildAddressDTO(4);

        store1 = new Store();
        store1.setName("Store 1");
        store1.setAddress(address1);

        storeRepository.saveAndFlush(store1);

        store2 = new Store();
        store2.setName("Store 2");
        store2.setAddress(address2);

        storeRepository.saveAndFlush(store2);

        store3 = new Store();
        store3.setName("Store 3");
        store3.setAddress(address3);

        store4 = new Store();
        store4.setName("Store 4");
        store4.setAddress(address4);

        storeDTO1 = new StoreDTO("Store 1", addressDTO1);
        storeDTO2 = new StoreDTO("Store 2", addressDTO2);
        storeDTO3 = new StoreDTO("Store 3", addressDTO3);
        storeDTO4 = new StoreDTO("Store 4", addressDTO4);
    }

    @Test
    void test_GetAllStores_ReturnsCorrectStores(){


        given().get("/api/stores")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("size", is(2)).and()
               .body("[0].name", is(store1.getName())).and()
               .body("[1].name", is(store2.getName()));

    }

    @Test
    void test_InsertStore_StoreAlreadyExists_ThrowsStoreAlreadyExistException() throws StoreAlreadyExistsException{
        

        given().contentType(ContentType.JSON).body(storeDTO1)
               .post("/api/stores")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_InsertStore_AddressExists_ReturnsCorrectStore() throws StoreAlreadyExistsException{

        given().contentType(ContentType.JSON).body(storeDTO3)
               .post("/api/stores")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("name", is(store3.getName())).and()
               .body("address", is((int) address3.getAddressId()));
    }
    
    @Test
    void test_InsertStore_AddressDoesNotExist_ReturnsCorrectStore() throws StoreAlreadyExistsException{

        
        given().contentType(ContentType.JSON).body(storeDTO4)
               .post("/api/stores")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("name", is(store4.getName())).and()
               .body("address", is((int) addressRepository.findByLatitudeAndLongitude(address4.getLatitude(), address4.getLongitude()).get().getAddressId()));
    }

    Address buildAddressObject(long id){
        Address address = new Address();
        address.setLongitude(100 + id);
        address.setLatitude(50 + id);
        address.setStreet("Street " + id);
        address.setPostalCode("3810-24" + id);
        address.setCity("city " + id);
        address.setCountry("Country " + id);
        return address;
    }

    AddressDTO buildAddressDTO(long id){
        AddressDTO address = new AddressDTO();
        address.setLongitude(100 + id);
        address.setLatitude(50 + id);
        address.setStreet("Street " + id);
        address.setPostalCode("3810-24" + id);
        address.setCity("city " + id);
        address.setCountry("Country " + id);
        return address;
    }
}
