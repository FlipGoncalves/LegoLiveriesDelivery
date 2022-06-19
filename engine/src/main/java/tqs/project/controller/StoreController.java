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

import tqs.project.datamodels.StoreDTO;
import tqs.project.model.Store;
import tqs.project.service.StoreService;

@RestController
@RequestMapping("/api/store")
@Validated
@CrossOrigin
public class StoreController {
    private static final Logger log = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private StoreService storeService;

    @GetMapping()
    public ResponseEntity<List<Store>> getAllStores() {
        log.info("GET Request -> All Stores Data");

        List<Store> stores = storeService.getAllStores();

        return new ResponseEntity<>(stores, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Store> insertStore(@RequestBody StoreDTO storeDTO){

        Store store = null;
        try {
            store = storeService.insertStore(storeDTO);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(store, HttpStatus.CREATED);
    }
}