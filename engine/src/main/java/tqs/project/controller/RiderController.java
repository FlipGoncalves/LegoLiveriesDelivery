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

import tqs.project.datamodels.RiderDTO;
import tqs.project.exceptions.RiderAlreadyExistsException;
import tqs.project.exceptions.RiderNotFoundException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.Rider;
import tqs.project.service.RiderService;

@RestController
@RequestMapping("/api/rider")
@Validated
@CrossOrigin
public class RiderController {
    private static final Logger log = LoggerFactory.getLogger(RiderController.class);

    @Autowired
    private RiderService riderservice;

    @GetMapping()
    public ResponseEntity<List<Rider>> getAllRiders() {
        log.info("GET Request -> All Riders Data");

        List<Rider> riders = riderservice.getAllRiders();

        return new ResponseEntity<>(riders, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Object> addRider(@RequestBody RiderDTO rider) {
        log.info("POST Request -> Rider data");

        Rider riderSaved;
        
        try {
            riderSaved = riderservice.insertRider(rider);
        } catch (RiderAlreadyExistsException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(riderSaved, HttpStatus.CREATED);
    }
}