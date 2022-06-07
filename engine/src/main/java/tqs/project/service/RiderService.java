package tqs.project.service;

import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.repositories.RiderRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import tqs.project.repositories.UserRepository;

@Service
public class RiderService {    
    private static final Logger log = LoggerFactory.getLogger(RiderService.class);

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
        Rider rider2 = new Rider(0, 0);

        if (usr != null) {
            rider2.setUser(usr);
        } else {
            usr = new User((String) rider.get("name"), (String) rider.get("email"), (String) rider.get("password"));
            rider2.setUser(usr);
        }

        return rep.save(rider2);
    }
}
