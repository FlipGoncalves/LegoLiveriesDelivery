package tqs.project.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.model.Rider;
import tqs.project.repositories.RiderRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RiderService {    
    private static final Logger log = LoggerFactory.getLogger(RiderService.class);

    @Autowired
    private RiderRepository rep;

    public List<Rider> getAllData() {
        log.info("Getting All Rider Data");

        List<Rider> riders = rep.findAll();

        return riders;
    }
}
