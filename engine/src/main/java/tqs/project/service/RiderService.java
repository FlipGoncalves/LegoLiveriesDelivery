package tqs.project.service;

import java.security.SecureRandom;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tqs.project.datamodels.RiderDTO;
import tqs.project.exceptions.RiderAlreadyExistsException;
import tqs.project.exceptions.RiderNotFoundException;
import tqs.project.exceptions.UserNotFoundException;
import tqs.project.model.Rider;
import tqs.project.model.User;
import tqs.project.repository.RiderRepository;
import tqs.project.repository.UserRepository;

@Service
public class RiderService {    
    private static final Logger log = LoggerFactory.getLogger(RiderService.class);

    private SecureRandom rand = new SecureRandom();

    @Autowired
    private RiderRepository riderRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Rider> getAllRiders() {
        log.info("Getting All Rider Data");

        return riderRepository.findAll();
    }

    public Rider insertRider(RiderDTO dto) throws RiderAlreadyExistsException{
        log.info("Getting All Rider Data");

        Rider rider = new Rider();

        if (riderRepository.findByUserEmail(dto.getEmail()).isPresent()) {
            throw new RiderAlreadyExistsException("Rider with email " + dto.getEmail() + " already exists");
        } 

        User user = createOrGetUser(dto);

        int numRev = rand.nextInt(16);
        int sumRev = 0;
        if (dto.getNumRev() < 0) {
            numRev = dto.getNumRev();
            sumRev = dto.getSumRev();
        } else {
            for (int i = 0; i < numRev; i++) {
                sumRev += rand.nextInt(5) + 1;
            }
        }

        rider.setReviewSum(sumRev);
        rider.setTotalReviews(numRev);

        rider.setUser(user);

        rider = riderRepository.saveAndFlush(rider);

        user.setRider(rider);

        return rider;
    }

    public User createOrGetUser(RiderDTO dto){
        Optional<User> userOptional = userRepository.findByEmail(dto.getEmail());
        
        User user = new User();
        
        if (userOptional.isEmpty()) {
            user.setEmail(dto.getEmail());
            user.setUsername(dto.getUsername());
            user.setPassword(dto.getPassword());
            user = userRepository.saveAndFlush(user);
        } else{
            user = userOptional.get();
        }

        return user;
    }
}