package restapi.tqs.Controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import restapi.tqs.Exceptions.BadRequestException;
import restapi.tqs.Models.Lego;
import restapi.tqs.Repositories.LegoRepository;
import restapi.tqs.Service.LegoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RestController
@CrossOrigin
@RequestMapping("/lego")
public class RESTController {
    private static final Logger log = LoggerFactory.getLogger(RESTController.class);

    @Autowired
    private LegoService service;

    @GetMapping("/all_legos")
    public ResponseEntity<List<Lego>> getData() {
        log.info("GET Request -> All Lego Data");
        List<Lego> legos = service.getData();
        //List<Lego> legos = Arrays.asList(new Lego("Lego 1", 22.99), new Lego("Lego 2", 44.99));
        System.out.println(legos);

        // repositoryData.saveAll(covid);
        return new ResponseEntity<>(legos, HttpStatus.OK);
    }

    @PostMapping("/insert_lego")
    public ResponseEntity<Lego> insertLego(@RequestBody Lego lego) {
        Lego lego2 = service.insertData(lego);

        return new ResponseEntity<>(lego, HttpStatus.OK);
    }

    @GetMapping("/get_lego/name")
    public ResponseEntity<List<Lego>> getLegoByName(@RequestParam(value = "name", required = true) String name) throws BadRequestException {
        log.info("GET Request -> Lego Data by name: {}", name);

        List<Lego> data = service.getData(name);

        if (data.isEmpty()) {
            throw new BadRequestException("ERROR getting Lego data by name");
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    @GetMapping("/get_lego/price")
    public ResponseEntity<List<Lego>> getLegoByPrice(@RequestParam(value = "price", required = true) String price) throws BadRequestException {
        log.info("GET Request -> Lego Data by price: {}", price);

        List<Lego> data = service.getData(Double.parseDouble(price));

        if (data.isEmpty()) {
            throw new BadRequestException("ERROR getting Lego data by price");
        }

        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}