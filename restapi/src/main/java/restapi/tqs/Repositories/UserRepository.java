package restapi.tqs.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
