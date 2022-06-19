package tqs.project.service;

import java.security.SecureRandom;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.RiderDTO;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.repositories.RiderRepository;
import tqs.project.repositories.UserRepository;

@Service
public class RiderService {    
    private static final Logger log = LoggerFactory.getLogger(RiderService.class);

    private SecureRandom rand = new SecureRandom();

    @Autowired
    private RiderRepository rep;

    @Autowired
    private UserRepository userrep;

    public List<Rider> getAllData() {
        log.info("Getting All Rider Data");

        List<Rider> riders = rep.findAll();

        return riders;
    }

    public Rider insertRider(RiderDTO rider) throws UserNotFoundException{
        log.info("Getting All Rider Data");

        User usr = userrep.findByEmail((String) rider.getEmail());

        if (usr == null) {
            throw new UserNotFoundException("User with email " + rider.getEmail() + " already exists");
        } 

        Rider rider2;

        if (rider.getNumRev() < 0) {
            rider2 = new Rider(rider.getSumRev(), rider.getNumRev());
        } else {
            int numRev = rand.nextInt(16);
            int sum = 0;
            for (int i = 0; i < numRev; i++) {
                sum += rand.nextInt(5) + 1;
            }
            
            rider2 = new Rider(sum, numRev);
        }

        rider2.setUser(usr);

        return rep.save(rider2);
    }
}
