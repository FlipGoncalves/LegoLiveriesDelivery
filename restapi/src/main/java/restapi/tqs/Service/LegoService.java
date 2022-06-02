package restapi.tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.Models.Lego;
import restapi.tqs.Repositories.LegoRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LegoService {    
    private static final Logger log = LoggerFactory.getLogger(LegoService.class);

    @Autowired
    private LegoRepository legorep;


    public List<Lego> getData() {
        log.info("Getting All Lego");

        List<Lego> legos = legorep.findAll();
        return legos;
    }

    public List<Lego> getData(String name) {
        log.info("Getting Lego By Name {}", name);

        List<Lego> legos = legorep.findAllByNameContainingIgnoreCase(name);
        return legos;
    }

    public List<Lego> getData(double price) {
        log.info("Getting Lego By Price {}", price);
        
        List<Lego> legos = legorep.findAllByPrice(price);
        return legos;

    }

    public Lego insertData(Lego lego) {
        log.info("Inserting Lego");
         
        return legorep.save(lego);
    }
}