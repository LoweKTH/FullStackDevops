package com.fullstackdevops.userms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.fullstackdevops.userms.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);

}
