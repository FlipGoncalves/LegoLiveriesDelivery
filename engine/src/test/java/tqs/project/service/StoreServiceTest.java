package tqs.project.service;

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

import tqs.project.datamodels.AddressDTO;
import tqs.project.datamodels.StoreDTO;
import tqs.project.exceptions.AddressAlreadyExistsException;
import tqs.project.exceptions.StoreAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.model.Store;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.StoreRepository;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {
    
    @Mock(lenient = true)
    private StoreRepository storeRepository;

    @Mock(lenient = true)
    private AddressRepository addressRepository;

    @InjectMocks
    private StoreService service;

    Store store1, store2, store3, store4;
    StoreDTO storeDTO1, storeDTO2, storeDTO3, storeDTO4;
    Address address1, address2, address3, address4;
    AddressDTO addressDTO1, addressDTO2, addressDTO3, addressDTO4;

    @BeforeEach
    void setUp(){
        
        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);
        address3 = buildAddressObject(3);
        address4 = buildAddressObject(4);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);
        addressDTO4 = buildAddressDTO(4);

        store1 = new Store();
        store1.setName("Store 1");
        store1.setAddress(address1);

        store2 = new Store();
        store2.setName("Store 2");
        store2.setAddress(address2);

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


        Mockito.when(storeRepository.findAll()).thenReturn(new ArrayList<>(Arrays.asList(store1, store2)));
        Mockito.when(storeRepository.findByName(storeDTO1.getName())).thenReturn(Optional.of(store1));
        Mockito.when(storeRepository.findByName(storeDTO2.getName())).thenReturn(Optional.of(store2));
        Mockito.when(storeRepository.findByName(storeDTO3.getName())).thenReturn(Optional.empty());
        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO1.getLatitude(), addressDTO1.getLongitude())).thenReturn(Optional.of(address1));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO2.getLatitude(), addressDTO2.getLongitude())).thenReturn(Optional.of(address2));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO3.getLatitude(), addressDTO3.getLongitude())).thenReturn(Optional.of(address3));
        Mockito.when(addressRepository.findByLatitudeAndLongitude(addressDTO4.getLatitude(), addressDTO4.getLongitude())).thenReturn(Optional.empty());
    }

    @Test
    void test_GetAllStores_ReturnsCorrectStores(){
        
        List<Store> result = service.getAllStores();

        assertTrue(!result.isEmpty());
        assertEquals(2, result.size());
        assertTrue(result.contains(store1));
        assertTrue(result.contains(store2));
    }

    @Test
    void test_InsertStore_StoreAlreadyExists_ThrowsStoreAlreadyExistException(){
        
        assertThrows(StoreAlreadyExistsException.class, () -> {service.insertStore(storeDTO1);});

    }

    @Test
    void test_InsertStore_AddressExists_ReturnsCorrectStore() throws StoreAlreadyExistsException{
        
        Store result = service.insertStore(storeDTO3);

        assertEquals(store3.getName(), result.getName());
        assertEquals(store3.getAddress(), result.getAddress());
    }
    
    @Test
    void test_InsertStore_AddressDoesNotExist_ReturnsCorrectStore() throws StoreAlreadyExistsException{

        Mockito.when(addressRepository.saveAndFlush(any(Address.class))).thenReturn(address4);

        Store result = service.insertStore(storeDTO4);

        assertEquals(store4.getName(), result.getName());
        assertEquals(store4.getAddress(), result.getAddress());
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
