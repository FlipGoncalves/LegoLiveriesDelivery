package restapi.tqs.Controllers;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import restapi.tqs.Controller.AddressController;
import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.Exceptions.AddressAlreadyExistsException;
import restapi.tqs.Models.Address;
import restapi.tqs.Service.AddressService;

@WebMvcTest(AddressController.class)
class AddressControllerTest {
    
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AddressService service;

    Address address1, address2, address3;
    AddressDTO addressDTO1, addressDTO2, addressDTO3;

    @BeforeEach
    void setUp(){
        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);
        address3 = buildAddressObject(3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);
    }

    @Test
    void test_getAllAdresses() throws Exception{

        Mockito.when(service.getAllAddresses()).thenReturn(new ArrayList<>(Arrays.asList(address1, address2)));

        mvc.perform(get("/address")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$", hasSize(2)))
        .andExpect(jsonPath("$[0].street", is(address1.getStreet())))
        .andExpect(jsonPath("$[0].latitude", is(address1.getLatitude())))
        .andExpect(jsonPath("$[0].longitude", is(address1.getLongitude())))
        .andExpect(jsonPath("$[1].street", is(address2.getStreet())))
        .andExpect(jsonPath("$[1].latitude", is(address2.getLatitude())))
        .andExpect(jsonPath("$[1].longitude", is(address2.getLongitude())));
    }

    @Test
    void test_insertAddress_AddressExists_ReturnsBadRequestStatus() throws Exception{
        Mockito.when(service.insertAddress(addressDTO1)).thenThrow(AddressAlreadyExistsException.class);

        mvc.perform(post("/address")
        .content(objectMapper.writeValueAsString(addressDTO1))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isBadRequest());
    }

    @Test
    void test_insertAddress_AddressDoesNotExist_ReturnsCorrectAddress() throws Exception{
        Mockito.when(service.insertAddress(addressDTO3)).thenReturn(address3);

        mvc.perform(post("/address")
        .content(objectMapper.writeValueAsString(addressDTO3))
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.street", is(address3.getStreet())))
        .andExpect(jsonPath("$.latitude", is(address3.getLatitude())))
        .andExpect(jsonPath("$.longitude", is(address3.getLongitude())));
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
