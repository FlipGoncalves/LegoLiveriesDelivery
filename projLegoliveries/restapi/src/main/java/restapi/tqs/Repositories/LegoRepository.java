package restapi.tqs.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.Lego;

@Repository
public interface LegoRepository extends JpaRepository<Lego, Long> {
    List<Lego> findAllByNameContainingIgnoreCase(String name);
    List<Lego> findAllByPrice(double price);
}

