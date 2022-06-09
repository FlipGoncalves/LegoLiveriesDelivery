package restapi.tqs.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import restapi.tqs.DataModels.LegoDTO;
import restapi.tqs.Exceptions.BadLegoDTOException;
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
        
        name = name.replaceAll("[\n\r\t]", "_");
        log.info("Getting Lego By Name {}", name);

        List<Lego> legos = legorep.findAllByNameContainingIgnoreCase(name);
        return legos;
    }

    public List<Lego> getData(double price) {
        log.info("Getting Lego By Price {}", price);
        
        List<Lego> legos = legorep.findAllByPrice(price);
        return legos;

    }

    public Lego insertData(LegoDTO legoDTO) throws BadLegoDTOException {
        log.info("Inserting Lego");
        
        if (legoDTO.hasNullFields()){
            throw new BadLegoDTOException("The LegoDTO has a null attribute: " + legoDTO.toString());
        }

        if (legoDTO.getImgUrl().isBlank()|| legoDTO.getName().isBlank()|| legoDTO.getPrice() <= 0){
            throw new BadLegoDTOException("The LegoDTO is invalid: " + legoDTO.toString());
        }

        Lego lego = new Lego();
        lego.setName(legoDTO.getName());
        lego.setImageUrl(legoDTO.getImgUrl());
        lego.setPrice(legoDTO.getPrice());

        return legorep.save(lego);
    }
}