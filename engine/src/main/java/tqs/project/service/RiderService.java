package tqs.project.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.repositories.RiderRepository;

import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tqs.project.repositories.UserRepository;

@Service
public class RiderService {    
    private static final Logger log = LoggerFactory.getLogger(RiderService.class);

    private Random rand = new Random();

    @Autowired
    private RiderRepository rep;

    @Autowired
    private UserRepository userrep;

    public List<Rider> getAllData() {
        log.info("Getting All Rider Data");

        List<Rider> riders = rep.findAll();

        return riders;
    }

    public Rider insertRider(Map<String, Object> rider) {
        log.info("Getting All Rider Data");

        User usr = userrep.findByEmail((String) rider.get("email"));

        if (usr != null) {
            return null;
        } 

        Rider rider2;

        if (rider.keySet().contains("numRev")) {
            rider2 = new Rider((int) rider.get("sumRev"), (int) rider.get("numRev"));
        } else {
            int numRev = rand.nextInt(16);
            int sum = 0;
            for (int i = 0; i < numRev; i++) {
                sum += rand.nextInt(5) + 1;
            }
            
            rider2 = new Rider(sum, numRev);
        }

        usr = new User((String) rider.get("name"), (String) rider.get("email"), (String) rider.get("password"));
            rider2.setUser(usr);

        return rep.save(rider2);
    }
}
