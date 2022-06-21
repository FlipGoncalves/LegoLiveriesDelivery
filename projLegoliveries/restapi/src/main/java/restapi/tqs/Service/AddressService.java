package restapi.tqs.Service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.Exceptions.AddressAlreadyExistsException;
import restapi.tqs.Models.Address;
import restapi.tqs.Repositories.AddressRepository;

@Service
public class AddressService {
    private static final Logger log = LoggerFactory.getLogger(AddressService.class);

    @Autowired
    private AddressRepository addressRep;

    public List<Address> getAllAddresses(){
        log.info("Getting all addresses");

        return addressRep.findAll();
    }

    public Address insertAddress(AddressDTO addressDTO) throws AddressAlreadyExistsException{
        log.info("Inserting Address");

        if (addressRep.findByLatitudeAndLongitude(addressDTO.getLatitude(), addressDTO.getLongitude()).isPresent()){
            log.info("Address already exists: {}",addressDTO);
            throw new AddressAlreadyExistsException("Address already exists: " + addressDTO);
        }

        Address address = new Address();
        address.convertDTOtoObject(addressDTO);
        log.info("Saving Address");
        return addressRep.saveAndFlush(address);
    }
}
