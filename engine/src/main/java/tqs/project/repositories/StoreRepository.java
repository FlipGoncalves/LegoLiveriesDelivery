package tqs.project.repositories;

import org.springframework.stereotype.Repository;

import tqs.project.model.Store;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface StoreRepository extends JpaRepository<Store, Long> {

}