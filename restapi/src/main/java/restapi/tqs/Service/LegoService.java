package restapi.tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.Models.Lego;
import restapi.tqs.Repositories.LegoRepository;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class LegoService {    
    private static final Logger log = LoggerFactory.getLogger(LegoService.class);

    @Autowired
    private LegoRepository legorep;


    public List<Lego> getData() {
        log.info("Getting Cached Data");

        List<Lego> legos = legorep.findAll();
        return legos;
        //return Arrays.asList(new Lego("Lego Service by Name", 2.0), new Lego("Lego Serice By Name", 4.0));
    }

    public List<Lego> getData(String name) {
        log.info("Getting Cached Data");

        List<Lego> legos = legorep.findAllByName(name);
        return legos;
        //return Arrays.asList(new Lego("Lego Service by Name", 2.0), new Lego("Lego Serice By Name", 4.0));
    }

    public List<Lego> getData(double price) {
        log.info("Getting Cached Data");
        
        List<Lego> legos = legorep.findAllByPrice(price);
        return legos;

        //return Arrays.asList(new Lego("Lego Service by Price", 2.0));
    }

    public Lego insertData(String name, double price, String imageUrl) {
        log.info("Getting Cached Data");
        
        Lego lego = new Lego();
        lego.setName(name);
        lego.setPrice(price);
        lego.setImageUrl(imageUrl);

        return legorep.save(lego);

        //return new Lego("Lego Service by Insert", 2.0);
    }
}