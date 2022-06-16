package restapi.tqs.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.Client;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    Client findByUserEmail(String email);
}