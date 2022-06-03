package tqs.project.repositories;

import org.springframework.stereotype.Repository;

import tqs.project.model.Order;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Order findByClientName(String name);
}
