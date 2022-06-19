package tqs.project.repository;

import org.springframework.stereotype.Repository;

import tqs.project.model.Rider;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface RiderRepository extends JpaRepository<Rider, Long> {
}
