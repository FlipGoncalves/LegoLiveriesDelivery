package tqs.project.repositories;

import org.springframework.stereotype.Repository;

import tqs.project.model.Manager;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    Manager findByUserEmail(String email);
}
