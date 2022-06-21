package restapi.tqs.Repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.Client;
import restapi.tqs.Models.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByClient(Client client, Pageable pageable);
    Optional<Order> findByExternalOrderId(long externalOrderId);
}