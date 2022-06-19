package restapi.tqs.Controller;

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

import restapi.tqs.DataModels.AddressDTO;
import restapi.tqs.Exceptions.AddressAlreadyExistsException;
import restapi.tqs.Models.Address;
import restapi.tqs.Service.AddressService;

@RestController
@RequestMapping("/address")
@Validated
@CrossOrigin
public class AddressController {
    private static final Logger log = LoggerFactory.getLogger(AddressController.class);

    @Autowired
    private AddressService addressService;

    @GetMapping()
    public ResponseEntity<List<Address>> getAllAddresses() {
        log.info("GET Request -> All Stores Data");

        List<Address> addresses = addressService.getAllAddresses();

        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Address> insertAddress(@RequestBody AddressDTO addressDTO){

        Address address = null;
        try {
            address = addressService.insertAddress(addressDTO);
        } catch (AddressAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(address, HttpStatus.CREATED);
    }
}