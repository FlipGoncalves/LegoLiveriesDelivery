package tqs.project.repository;

import org.springframework.stereotype.Repository;

import tqs.project.model.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByClientName(String name);
    List<Order> findByStatus(int status);
    List<Order> findByStoreStoreId(long storeId);
    List<Order> findByStoreStoreIdAndStatus(long storeId, int status);
}
