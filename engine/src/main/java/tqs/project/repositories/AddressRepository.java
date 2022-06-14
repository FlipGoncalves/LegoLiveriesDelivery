package tqs.project.repositories;

import org.springframework.stereotype.Repository;

import tqs.project.model.Address;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
