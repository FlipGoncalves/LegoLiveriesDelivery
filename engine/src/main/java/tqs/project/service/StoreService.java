package tqs.project.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.StoreDTO;
import tqs.project.exceptions.StoreAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.model.Store;
import tqs.project.repositories.AddressRepository;
import tqs.project.repositories.StoreRepository;

@Service
public class StoreService {
    
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private AddressRepository addressRep;

    public List<Store> getAllStores(){
        log.info("Getting all stores");

        return storeRep.findAll();
    }

    public Store insertStore(StoreDTO storeDTO) throws StoreAlreadyExistsException{
        log.info("Inserting Store");

        Store store = new Store();

        if(storeRep.findByName(storeDTO.getName()).isPresent()){
            throw new StoreAlreadyExistsException("Store with name " + storeDTO.getName() + " already exists");
        }

        store.setName(storeDTO.getName());

        ExampleMatcher modelMatcher = ExampleMatcher.matching()
            .withIgnorePaths("addressId")
            .withIgnoreCase("street")
            .withIgnoreCase("postalCode")
            .withIgnoreCase("city")
            .withIgnoreCase("country");

        Address address = new Address();
        address.convertDTOtoObject(storeDTO.getAddress());

        Example<Address> example = Example.of(address, modelMatcher);

        if (addressRep.exists(example)){
            List<Address> list = addressRep.findAll(example);
            address = list.get(0);
        } else{
            address = addressRep.saveAndFlush(address);
        }

        store.setAddress(address);

        return storeRep.saveAndFlush(store);
    }
}
