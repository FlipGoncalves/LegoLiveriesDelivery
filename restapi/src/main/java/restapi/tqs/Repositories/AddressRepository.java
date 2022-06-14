package restapi.tqs.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.Address;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    
}
