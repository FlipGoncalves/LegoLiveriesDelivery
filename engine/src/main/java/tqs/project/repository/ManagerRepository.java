package tqs.project.repository;

import org.springframework.stereotype.Repository;

import tqs.project.model.Manager;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Optional<Manager> findByUserEmail(String email);
}
