package restapi.tqs.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import restapi.tqs.DataModels.LegoDTO;
import restapi.tqs.Exceptions.BadLegoDTOException;
import restapi.tqs.Models.Lego;
import restapi.tqs.Service.LegoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;

@RestController
@CrossOrigin
@Validated
@RequestMapping("/legos")
public class LegoController {
    private static final Logger log = LoggerFactory.getLogger(LegoController.class);

    @Autowired
    private LegoService legoService;

    @GetMapping()
    public ResponseEntity<List<Lego>> getAllLegos() {
        log.info("GET Request -> All Lego Data");
        List<Lego> legos = legoService.getLegos();
        return new ResponseEntity<>(legos, HttpStatus.OK);
    }

    @GetMapping("/name")
    public ResponseEntity<List<Lego>> getLegoByName(@RequestParam(value = "name", required = true) String name){
        name = name.replaceAll("[\n\r\t]", "_");
        log.info("GET Request -> Lego Data by name: {}", name);

        List<Lego> data = legoService.getLegosByName(name);

        if (data.isEmpty()) {
            log.info("ERROR: Lego not Found");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("SUCCESS: Lego Found");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/price")
    public ResponseEntity<List<Lego>> getLegoByPrice(@RequestParam(value = "price", required = true) String price){
        price = price.replaceAll("[\n\r\t]", "_");
        log.info("GET Request -> Lego Data by price: {}", price);

        List<Lego> data = legoService.getLegosByPrice(Double.parseDouble(price));

        if (data.isEmpty()) {
            log.info("SUCCESS: Lego not Found");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("SUCCESS: Lego Found");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Lego> insertLego(@RequestBody LegoDTO lego) {
        log.info("POST Request -> Insert Lego: {}", lego);

        Lego lego2;
        try {
            lego2 = legoService.insertLego(lego);
        } catch (BadLegoDTOException e) {
            log.info("ERROR: Bad Lego insertion");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        log.info("SUCCESS: Lego created");
        return new ResponseEntity<>(lego2, HttpStatus.CREATED);
    }
}