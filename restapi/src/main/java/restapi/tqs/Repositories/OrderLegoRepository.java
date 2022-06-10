package restapi.tqs.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.OrderLego;

@Repository
public interface OrderLegoRepository extends JpaRepository<OrderLego, Long> {
    
}
