package restapi.tqs.Services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.Exceptions.AddressAlreadyExistsException;
import restapi.tqs.Models.Address;
import restapi.tqs.Repositories.AddressRepository;
import restapi.tqs.Service.AddressService;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {
    
    @Mock(lenient = true)
    private AddressRepository addressRepository;

    @InjectMocks
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

        Mockito.when(addressRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(address1, address2)));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(address1.getLatitude(), address1.getLongitude())).thenReturn(Optional.of(address1));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(address2.getLatitude(), address2.getLongitude())).thenReturn(Optional.of(address2));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(address3.getLatitude(), address3.getLongitude())).thenReturn(Optional.empty());
    }

    @Test
    void test_GetAllAddress_ReturnsCorrectAddresses(){

        List<Address> result = service.getAllAddresses();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(address1));
        assertTrue(result.contains(address2));
    }

    @Test
    void test_InsertAddress_AddressExists_ThrowsAddressAlreadyExistsException() throws AddressAlreadyExistsException{
    
        Mockito.when(addressRepository.saveAndFlush(any(Address.class))).thenReturn(address3);
        
        assertThrows(AddressAlreadyExistsException.class, () -> {service.insertAddress(addressDTO1);});
    }

    @Test
    void test_InsertAddress_AddressDoesNotExist_ReturnsCorrectAddress() throws AddressAlreadyExistsException{
    
        Mockito.when(addressRepository.saveAndFlush(any(Address.class))).thenReturn(address3);

        Address result = service.insertAddress(addressDTO3);

        assertEquals(address3, result);
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
