package tqs.project.repository;

import org.springframework.stereotype.Repository;

import tqs.project.model.Rider;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
    Optional<Rider> findByUserEmail(String email);
}