package tqs.project.controller;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.Matchers.is;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;

import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import tqs.project.datamodels.AddressDTO;
import tqs.project.exceptions.AddressAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.service.AddressService;

@WebMvcTest(AddressController.class)
class AddressControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AddressService service;

    Address address1, address2, address3;
    AddressDTO addressDTO1, addressDTO2, addressDTO3;

    @BeforeEach
    void setUp() throws AddressAlreadyExistsException{

        RestAssuredMockMvc.mockMvc( mvc );

        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);
        address3 = buildAddressObject(3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);

        Mockito.when(service.getAllAddresses()).thenReturn(new ArrayList<>(Arrays.asList(address1, address2)));
        Mockito.when(service.insertAddress(addressDTO1)).thenThrow(AddressAlreadyExistsException.class);
        Mockito.when(service.insertAddress(addressDTO3)).thenReturn(address3);

    }

    @Test
    void test_getAllAdresses() throws Exception{

        given().get("/api/addresses")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.OK).and()
               .body("size", is(2)).and()
               .body("[0].street", is(address1.getStreet())).and()
               .body("[0].latitude", is((float) address1.getLatitude())).and()
               .body("[0].longitude", is((float) address1.getLongitude())).and()
               .body("[1].street", is(address2.getStreet())).and()
               .body("[1].latitude", is((float) address2.getLatitude())).and()
               .body("[1].longitude", is((float) address2.getLongitude()));
    }

    @Test
    void test_insertAddress_AddressExists_ReturnsBadRequestStatus() throws Exception{

        given().contentType(ContentType.JSON).body(addressDTO1)
               .post("/api/addresses")
               .then().log().body().assertThat()
               .status(HttpStatus.BAD_REQUEST);
    }

    @Test
    void test_insertAddress_AddressDoesNotExist_ReturnsCorrectAddress() throws Exception{
        
        given().contentType(ContentType.JSON).body(addressDTO3)
               .post("/api/addresses")
               .then().log().body().assertThat()
               .contentType(ContentType.JSON).and()
               .status(HttpStatus.CREATED).and()
               .body("street", is(address3.getStreet())).and()
               .body("latitude", is((float) address3.getLatitude())).and()
               .body("longitude", is((float) address3.getLongitude()));
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
