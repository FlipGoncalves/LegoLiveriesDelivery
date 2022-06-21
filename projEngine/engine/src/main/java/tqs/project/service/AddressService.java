package tqs.project.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.AddressDTO;
import tqs.project.exceptions.AddressAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.repository.AddressRepository;

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

        Optional<Address> address = addressRep.findByLatitudeAndLongitude(addressDTO.getLatitude(), addressDTO.getLongitude());

        if (address.isPresent()){
            throw new AddressAlreadyExistsException("Address already exists: " + address);
        }

        Address address2 = new Address();
        address2.convertDTOtoObject(addressDTO);

        return addressRep.saveAndFlush(address2);
    }
}
