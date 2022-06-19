package tqs.project.repository;

import org.springframework.stereotype.Repository;

import tqs.project.model.Address;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    Optional<Address> findByLatitudeAndLongitude(double latitude, double longitude);
}
