package tqs.project.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tqs.project.datamodels.AddressDTO;
import tqs.project.exceptions.AddressAlreadyExistsException;
import tqs.project.model.Address;
import tqs.project.service.AddressService;

@RestController
@RequestMapping("/api/address")
@Validated
@CrossOrigin
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<List<Address>> getAllStores() {
        log.info("GET Request -> All Stores Data");

        List<Address> stores = addressService.getAllAddresses();

        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Address> insertAddress(@RequestBody AddressDTO addressDTO){

        Address store = null;
        try {
            store = addressService.insertAddress(addressDTO);
        } catch (AddressAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(store, HttpStatus.CREATED);
    }
}