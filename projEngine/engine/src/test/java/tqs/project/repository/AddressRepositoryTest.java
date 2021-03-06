package tqs.project.repository;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import tqs.project.datamodels.AddressDTO;
import tqs.project.model.Address;

@DataJpaTest
public class AddressRepositoryTest {
    
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private AddressRepository addressRepository;

    Address address1, address2, address3;
    AddressDTO addressDTO1, addressDTO2, addressDTO3;

    @BeforeEach
    void setUp(){

        address1 = buildAddressObject(1);
        address2 = buildAddressObject(2);
        address3 = buildAddressObject(3);

        address1 = entityManager.persistAndFlush(address1);
        address2 = entityManager.persistAndFlush(address2);
        address3 = entityManager.persistAndFlush(address3);

        addressDTO1 = buildAddressDTO(1);
        addressDTO2 = buildAddressDTO(2);
        addressDTO3 = buildAddressDTO(3);
    }

    @AfterEach
    void cleanUp(){
        entityManager.clear();
    }

    @Test
    void test_findByLatitudeAndLongitude_AddressExist_ReturnsAddress(){

        Optional<Address> result = addressRepository.findByLatitudeAndLongitude(addressDTO1.getLatitude(), addressDTO1.getLongitude());
        assertTrue(result.isPresent());
    }

    @Test
    void test_findByLatitudeAndLongitude_AddressDoesNotExist_ReturnsEmptyOptional(){

        Optional<Address> result = addressRepository.findByLatitudeAndLongitude(90, 180);
        assertTrue(result.isEmpty());
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
