package restapi.tqs.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import restapi.tqs.Models.Favorites;

@Repository
public interface FavoritesRepository extends JpaRepository<Favorites, Long> {
    
}
