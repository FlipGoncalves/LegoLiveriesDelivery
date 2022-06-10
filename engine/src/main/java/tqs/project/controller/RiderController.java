package tqs.project.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import tqs.project.exceptions.UserAlreadyExistsException;
import tqs.project.model.Rider;
import tqs.project.service.RiderService;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin
public class RiderController {
    private static final Logger log = LoggerFactory.getLogger(StatisticController.class);

    @Autowired
    private RiderService riderservice;

        
    @GetMapping("/riders")
    public ResponseEntity<Map<String, Object>> getData() {
        log.info("GET Request -> All Riders Data");

        List<Rider> riders = riderservice.getAllData();

        Map<String, Object> mapper = new HashMap<>();
        mapper.put("riders", riders);

        return new ResponseEntity<>(mapper, HttpStatus.OK);
    }

    @PostMapping("/addrider")
    public ResponseEntity<Object> addRider(@RequestBody Map<String, Object> rider) {
        log.info("POST Request -> Rider data");

        Rider riderSaved;
        try {
            riderSaved = riderservice.insertRider(rider);
        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }

        return new ResponseEntity<>(riderSaved, HttpStatus.OK);
    }
}