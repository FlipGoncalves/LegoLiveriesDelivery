package tqs.project.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.StoreDTO;
import tqs.project.exceptions.StoreAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.model.Store;
import tqs.project.repository.AddressRepository;
import tqs.project.repository.StoreRepository;

@Service
public class StoreService {
    
    private static final Logger log = LoggerFactory.getLogger(StoreService.class);

    @Autowired
    private StoreRepository storeRep;

    @Autowired
    private AddressRepository addressRep;

    public List<Store> getAllStores(){
        log.info("Getting All Stores");

        return storeRep.findAll();
    }

    public Store insertStore(StoreDTO storeDTO) throws StoreAlreadyExistsException{
        log.info("Inserting Store");

        Store store = new Store();

        if(storeRep.findByName(storeDTO.getName()).isPresent()){
            log.info("Inserting Store ERROR");
            throw new StoreAlreadyExistsException("Store with name " + storeDTO.getName() + " already exists");
        }

        log.info("Inserting Store SAVE");
        store = storeRep.saveAndFlush(store);

        log.info("Inserting Store NAME");
        store.setName(storeDTO.getName());

        Address address = new Address();

        log.info("Inserting Store GET ADDRESS");
        Optional<Address> addressOptional = addressRep.findByLatitudeAndLongitude(storeDTO.getAddress().getLatitude(), storeDTO.getAddress().getLongitude());

        log.info("Inserting Store ADDRESS");
        if (addressOptional.isPresent()){
            address = addressOptional.get();
        } else{
            address.convertDTOtoObject(storeDTO.getAddress());
            address = addressRep.saveAndFlush(address);
        }

        log.info("Inserting Store SET ADDRESS");
        store.setAddress(address);
        address.setStore(store);

        log.info("Inserting Store SAVE AGAIN");
        store = storeRep.saveAndFlush(store);

        return store;
    }
}
