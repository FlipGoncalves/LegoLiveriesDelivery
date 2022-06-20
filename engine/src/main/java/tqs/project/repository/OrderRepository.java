package tqs.project.repository;

import org.springframework.stereotype.Repository;

import tqs.project.model.Order;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByExternalOrderId(long externalOrderId);
    List<Order> findByClientName(String name);
    List<Order> findByStatus(int status);
    List<Order> findByStoreStoreId(long storeId);
    List<Order> findByStoreStoreIdAndStatus(long storeId, int status);
}
