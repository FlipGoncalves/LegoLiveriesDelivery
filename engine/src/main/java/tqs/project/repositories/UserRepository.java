package tqs.project.repositories;

import org.springframework.stereotype.Repository;

import tqs.project.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findByUserId(String email);
}
